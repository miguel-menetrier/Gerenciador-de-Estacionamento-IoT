package com.senai.SA.exceptions;

public class ExistEmailException extends RuntimeException {
    public ExistEmailException(String mensagem) {
        super(mensagem);
    }
}
