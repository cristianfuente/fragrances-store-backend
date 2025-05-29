package com.perfums.transactions.domain.models;

public enum StateType {
    WAITING_PAYMENTS,
    PAYMENT_IN_PROCESS,
    EXPIRED,
    PAID,
    ERROR,
    SENT,
    COMPLETE
}
