package com.perfums.transactions.domain.repository;

import com.perfums.transactions.domain.dto.EmailRequestDTO;
import io.smallrye.mutiny.Uni;

public interface EmailRepository {

    Uni<String> send(EmailRequestDTO requestDTO);

}
