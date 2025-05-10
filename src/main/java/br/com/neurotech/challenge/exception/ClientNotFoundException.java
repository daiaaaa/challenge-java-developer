package br.com.neurotech.challenge.exception;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(String id) {
        super("Client with ID " + id + " not found");
    }
}
