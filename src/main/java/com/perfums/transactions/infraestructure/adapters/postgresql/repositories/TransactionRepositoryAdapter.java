package com.perfums.transactions.infraestructure.adapters.postgresql.repositories;

import com.perfums.transactions.application.service.EmailTemplateBuilderService;
import com.perfums.transactions.domain.dto.EmailRequestDTO;
import com.perfums.transactions.domain.dto.OrderProductDTO;
import com.perfums.transactions.domain.dto.OrderRequestDTO;
import com.perfums.transactions.domain.models.StateType;
import com.perfums.transactions.domain.repository.EmailRepository;
import com.perfums.transactions.domain.repository.TransactionRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Client;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.FragranceSizeId;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.PaymentMethod;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Transaction;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.TransactionFragrance;
import com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache.FragranceSizePanacheRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache.TransactionFragancePanacheRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache.TransactionPanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class TransactionRepositoryAdapter implements TransactionRepository {

    @Inject
    TransactionPanacheRepository transactionPanacheRepository;

    @Inject
    FragranceSizePanacheRepository fragranceSizePanacheRepository;

    @Inject
    TransactionFragancePanacheRepository transactionFragancePanacheRepository;

    @Inject
    EmailRepository emailRepository;

    @Override
    public Uni<Transaction> createTransaction(
            Client client,
            List<OrderProductDTO> products,
            OrderRequestDTO requestDTO,
            PaymentMethod paymentMethod,
            String code,
            BigDecimal totalPayment
    ) {
        return transactionPanacheRepository.createTransaction(client, products, requestDTO, paymentMethod, code, totalPayment);
    }

    @Override
    public Uni<Void> confirmPayment(Transaction transaction) {
        List<TransactionFragrance> fragrances = new ArrayList<>();
        List<EmailTemplateBuilderService.ProductItem> items = new ArrayList<>();
        return transactionPanacheRepository.update("state = ?1 where id = ?2", StateType.PAID, transaction.getId())
                .onItem().transformToMulti(response -> Multi.createFrom().iterable(transaction.getFragrances()))
                .onItem().transformToUniAndConcatenate(transactionFragrance -> {
                    FragranceSizeId fragranceSizeId = new FragranceSizeId(
                            transactionFragrance.getSize().getId(),
                            transactionFragrance.getFragrance().getId()
                    );
                    fragrances.add(transactionFragrance);
                    return fragranceSizePanacheRepository.findById(fragranceSizeId);
                }).invoke(fragranceSize -> items.add(new EmailTemplateBuilderService.ProductItem(fragranceSize.getImageId(),
                        fragranceSize.getFragrance().getName(), fragrances
                        .stream()
                        .filter(fr -> Objects.equals(fr.getId().getFragranceId(), fragranceSize.getFragrance().getId()))
                        .findFirst()
                        .get().getQuantity().toString()
                ))).collect().asList()
                .flatMap(response -> {
                    EmailTemplateBuilderService.CustomerInfo customerInfo = new EmailTemplateBuilderService
                            .CustomerInfo(transaction.getFirstName(),
                            transaction.getLastName(),
                            transaction.getAddress(),
                            transaction.getAdditionalAddressInfo(),
                            transaction.getCountry(),
                            transaction.getDepartment(),
                            transaction.getPostalCode());
                    String html = EmailTemplateBuilderService.buildPaidOrderHtml(transaction.getCode(),
                            transaction.getFirstName(),
                            items,
                            String.valueOf(transaction.getTotalPayment()), customerInfo);
                    return emailRepository.send(new EmailRequestDTO(transaction.getEmail(), "Pedido Confirmado", html));
                }).flatMap(res -> Uni.createFrom().voidItem());
    }

    @Override
    @WithTransaction
    public Uni<Void> cancelTransactionAndRestoreStock(Long transactionId) {
        List<TransactionFragrance> fragrances = new ArrayList<>();
        List<EmailTemplateBuilderService.ProductItem> items = new ArrayList<>();
        return transactionFragancePanacheRepository
                .find("id.transactionId", transactionId)
                .list()
                .flatMap(results ->
                        Multi.createFrom().iterable(results)
                                .onItem()
                                .transformToUniAndConcatenate(transactionFragrance -> {
                                    FragranceSizeId fragranceSizeId = new FragranceSizeId(
                                            transactionFragrance.getSize().getId(),
                                            transactionFragrance.getFragrance().getId()
                                    );
                                    fragrances.add(transactionFragrance);
                                    return fragranceSizePanacheRepository
                                            .findById(fragranceSizeId)
                                            .invoke(fragranceSize -> fragranceSize
                                                    .setStock(fragranceSize.getStock() + transactionFragrance.getQuantity()));
                                }).onItem().transformToUniAndConcatenate(fragranceSize -> {
                                            items.add(new EmailTemplateBuilderService.ProductItem(fragranceSize.getImageId(),
                                                    fragranceSize.getFragrance().getName(), fragrances
                                                    .stream()
                                                    .filter(fr -> Objects.equals(fr.getId().getFragranceId(), fragranceSize.getFragrance().getId()))
                                                    .findFirst()
                                                    .get().getQuantity().toString()
                                            ));
                                            return fragranceSizePanacheRepository
                                                    .update("stock = ?1 where id.fragranceId = ?2 and id.sizeId = ?3", fragranceSize.getStock(),
                                                            fragranceSize.getFragrance().getId(), fragranceSize.getSize().getId());
                                        }
                                ).collect().asList()
                ).flatMap(updates -> Multi.createFrom().iterable(fragrances).onItem()
                        .transformToUniAndConcatenate(fragrance -> transactionFragancePanacheRepository
                                .delete(fragrance))
                        .toUni()
                ).flatMap(result -> transactionFragancePanacheRepository.flush())
                .flatMap(result -> transactionPanacheRepository.findById(transactionId))
                .flatMap(transaction -> transactionPanacheRepository.delete(transaction)
                        .flatMap(response -> {
                            String html = EmailTemplateBuilderService.buildCanceledOrderHtml(transaction.getCode(),
                                    transaction.getFirstName(),
                                    items,
                                    String.valueOf(transaction.getTotalPayment()));
                            return emailRepository.send(new EmailRequestDTO(transaction.getEmail(), "Pedido Cancelado", html));
                        })
                        .flatMap(response -> Uni.createFrom().voidItem())
                );
    }

    @Override
    @WithSession
    public Uni<Transaction> findById(Long idTransaction) {
        return transactionPanacheRepository.findById(idTransaction);
    }


}
