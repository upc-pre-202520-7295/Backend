package com.betalyze.shared.infrastructure.exception;

public class NotFoundException extends DomainException {
    public NotFoundException(String message) {
        super(message);
    }
}
