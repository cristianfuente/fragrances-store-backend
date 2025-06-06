package com.perfums.transactions.application.usecase.impl;

import com.perfums.transactions.application.service.TransactionSchedulerService;
import com.perfums.transactions.application.usecase.OrderUseCase;
import com.perfums.transactions.domain.dto.OrderDTO;
import com.perfums.transactions.domain.dto.OrderProductDTO;
import com.perfums.transactions.domain.dto.OrderRequestDTO;
import com.perfums.transactions.domain.dto.OrderCodeDTO;
import com.perfums.transactions.domain.models.PaymentCode;
import com.perfums.transactions.domain.models.StateType;
import com.perfums.transactions.domain.repository.ClientRepository;
import com.perfums.transactions.domain.repository.FragranceSizeRepository;
import com.perfums.transactions.domain.repository.OtpRepository;
import com.perfums.transactions.domain.repository.PaymentMethodRepository;
import com.perfums.transactions.domain.repository.PaymentStatusRepository;
import com.perfums.transactions.domain.repository.TransactionRepository;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.FragranceSizeId;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Otp;
import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.PaymentParameter;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.UriBuilder;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@ApplicationScoped
public class OrderUseCaseImpl implements OrderUseCase {

    @Inject
    ClientRepository clientRepository;

    @Inject
    PaymentMethodRepository paymentMethodRepository;

    @Inject
    TransactionRepository transactionRepository;

    @Inject
    FragranceSizeRepository fragranceSizeRepository;

    @Inject
    TransactionSchedulerService schedulerService;

    @Inject
    OtpRepository otpRepository;

    @Inject
    PaymentStatusRepository paymentStatusRepository;

    @ConfigProperty(name = "url.backend.domain")
    String backendDomain;

    @Override
    public Uni<List<OrderDTO>> getOrderByStatus(String status) {
        return transactionRepository.findAllByStatusAndCode(status)
                .map(transactions -> {
                    List<OrderDTO> orderDTOList = new ArrayList<>();
                    transactions.forEach(transaction -> {
                        OrderDTO dto = new OrderDTO();
                        dto.setClientName(transaction.getClient().getName());
                        dto.setFirstName(transaction.getFirstName());
                        dto.setLastName(transaction.getLastName());
                        dto.setCodeTransaction(transaction.getCode());
                        dto.setTotalPayment(transaction.getTotalPayment());
                        dto.setState(transaction.getState().name());
                        dto.setEmail(transaction.getEmail());
                        dto.setAddress(transaction.getAddress());
                        dto.setAdditionalAddressInfo(transaction.getAdditionalAddressInfo());
                        dto.setPhone(transaction.getPhone());
                        dto.setCountry(transaction.getCountry());
                        dto.setDepartment(transaction.getDepartment());
                        dto.setPostalCode(transaction.getPostalCode());
                        dto.setCreateAt(transaction.getCreateAt());

                        List<OrderDTO.FragranceItemDTO> fragList = new ArrayList<>();
                        transaction.getFragrances().forEach(transactionFragrance -> {
                            OrderDTO.FragranceItemDTO fragranceItemDTO = new OrderDTO.FragranceItemDTO();
                            fragranceItemDTO.setName(transactionFragrance.getFragrance().getName());
                            fragranceItemDTO.setQuantity(transactionFragrance.getQuantity());
                            fragranceItemDTO.setSizeLabel(transactionFragrance.getSize().getSize() +
                                    transactionFragrance.getSize().getUnit());
                            fragList.add(fragranceItemDTO);
                        });

                        dto.setFragrances(fragList);
                        orderDTOList.add(dto);
                    });
                    return orderDTOList;
                });
    }

    @Override
    public Uni<String> generateRedirectUrl(String tokenValue) {
        return otpRepository.findByOtpValue(tokenValue)
                .flatMap(otp -> {
                    String urlPayment = otp.getTransaction().getPaymentMethod().getUrl();
                    List<PaymentParameter> parameters = otp.getTransaction().getPaymentMethod().getPaymentParameters();
                    String tokenOtp = UUID.randomUUID().toString();
                    if (!otp.getTransaction().getState().equals(StateType.WAITING_PAYMENTS)) {
                        throw new IllegalStateException("Estado incorrecto");
                    }
                    return otpRepository.updateOtp(otp, tokenOtp)
                            .map(item -> {
                                String urlRedirect = backendDomain + "/order/checkout/" + tokenOtp;
                                UriBuilder uriBuilder = UriBuilder.fromUri(urlPayment);
                                for (PaymentParameter parameter : parameters) {
                                    String key = parameter.getKey();
                                    String value = getValue(otp, parameter, urlRedirect);
                                    uriBuilder.queryParam(key, value);
                                }
                                uriBuilder.queryParam("reference_id", otp.getTransaction().getCode());
                                return uriBuilder.build().toString();
                            });
                });
    }

    @Override
    public Uni<String> resolverClientRedirection(String tokenValue) {
        return otpRepository.findByOtpValue(tokenValue)
                .onItem().call(otp -> otpRepository.deleteOtp(otp))
                .flatMap(otp -> paymentStatusRepository.verifyTransaction(otp.getTransaction().getCode())
                        .onFailure().call(throwable -> {
                            log.error("error querying transaction {} {}", otp.getTransaction().getCode(), throwable.getMessage());
                            return transactionRepository.cancelTransactionAndRestoreStock(otp.getTransaction().getId());
                        }).onItem().transformToUni(response -> {
                            String code = otp.getTransaction().getCode();
                            UriBuilder uriBuilder = UriBuilder.fromUri(otp.getTransaction().getClient().getUrl());
                            uriBuilder.queryParam("transaction_code", code);
                            return transactionRepository.confirmPayment(otp.getTransaction())
                                    .map(update -> uriBuilder.build().toString());
                        }).onFailure().recoverWithItem(throwable -> {
                            UriBuilder uriBuilder = UriBuilder.fromUri(otp.getTransaction().getClient().getUrl());
                            uriBuilder.queryParam("transaction_code", "0");
                            return uriBuilder.build().toString();
                        }));
    }

    @Override
    public Uni<Void> sentOrder(String code){
        return transactionRepository.findByCode(code)
                .flatMap(transaction -> transactionRepository.markTransactionAsSent(transaction));
    }

    private static String getValue(Otp otp, PaymentParameter parameter, String urlRedirect) {
        String value = parameter.getValue();
        if (PaymentCode.REDIRECT_URI.name().equals(parameter.getCode())) {
            value = urlRedirect;
        }
        if (PaymentCode.PAYMENT.name().equals(parameter.getCode())) {
            value = String.valueOf(otp.getTransaction().getTotalPayment().intValue());
        }
        if (PaymentCode.TRANSACTION.name().equals(parameter.getCode())) {
            value = String.valueOf(otp.getTransaction().getCode());
        }
        return value;
    }

    @Override
    public Uni<OrderCodeDTO> processOrder(OrderRequestDTO request) {
        return clientRepository.findByApiKey(request.getApiKey())
                .flatMap(client -> {
                    log.info("Cliente encontrado para transaccion");
                    double margin = client.getMargin() != null ? client.getMargin() : 0.0;

                    return calculateTotalSequentially(request.getProducts(), margin)
                            .flatMap(total ->
                                    fragranceSizeRepository.verifyAndReserveStock(request.getProducts())
                                            .flatMap(isAvailable -> {
                                                log.info("verificacion de stock de fragancias");
                                                if (!isAvailable) {
                                                    log.error("Stock insuficiente");
                                                    return Uni.createFrom().failure(new IllegalStateException("Insufficient stock for one or more products."));
                                                }

                                                log.info("Stock disponible para transaccion");
                                                return paymentMethodRepository.findById(request.getPaymentMethodId())
                                                        .flatMap(paymentMethod -> {
                                                            log.info("Verificacion metodo de pago completa");
                                                            String code = generateTransactionCode();
                                                            return transactionRepository.createTransaction(
                                                                            client,
                                                                            request.getProducts(),
                                                                            request,
                                                                            paymentMethod,
                                                                            code,
                                                                            total
                                                                    ).invoke(transaction -> schedulerService
                                                                            .scheduleCancellation(transaction.getId()))
                                                                    .flatMap(transaction -> otpRepository.saveOtp(transaction))
                                                                    .map(otp -> {
                                                                        log.info("Se ha creado la transaccion");
                                                                        OrderCodeDTO response = new OrderCodeDTO();
                                                                        response.setCode(otp);
                                                                        return response;
                                                                    });
                                                        });
                                            })
                            );
                });
    }

    private String generateTransactionCode() {
        return new Random().ints(9, 0, 10)
                .mapToObj(String::valueOf)
                .reduce("", String::concat);
    }

    private Uni<BigDecimal> calculateTotalSequentially(List<OrderProductDTO> products, double margin) {
        return calculateTotalRecursive(products, margin, 0, BigDecimal.ZERO);
    }

    private Uni<BigDecimal> calculateTotalRecursive(List<OrderProductDTO> products, double margin, int index, BigDecimal accumulator) {
        log.info("Calculando total precio para transaccion");
        if (index >= products.size()) {
            return Uni.createFrom().item(accumulator);
        }

        OrderProductDTO current = products.get(index);
        FragranceSizeId id = new FragranceSizeId(current.getSizeId(), current.getFragranceId());

        return fragranceSizeRepository.findById(id)
                .flatMap(fragranceSize -> {
                    log.info("Obteniendo informacion de fragancia y tamano");
                    BigDecimal basePrice = fragranceSize.getPrice();
                    BigDecimal priceWithMargin = basePrice.add(basePrice.multiply(BigDecimal.valueOf(margin)));
                    BigDecimal total = priceWithMargin.multiply(BigDecimal.valueOf(current.getQuantity()));
                    return calculateTotalRecursive(products, margin, index + 1, accumulator.add(total));
                });
    }


}
