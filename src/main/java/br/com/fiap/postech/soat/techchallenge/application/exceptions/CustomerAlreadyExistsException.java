package br.com.fiap.postech.soat.techchallenge.application.exceptions;

public class CustomerAlreadyExistsException extends RuntimeException {
    public CustomerAlreadyExistsException(String message) {
        super(message);
    }

    public CustomerAlreadyExistsException(String message, Throwable cause) {
      super(message, cause);
    }

    public CustomerAlreadyExistsException(Throwable cause) {
      super(cause);
    }
}
