package br.com.davi.PPWebBack.usuario.exception;

public class InvalidRegistrationInformationException extends RuntimeException{
    public InvalidRegistrationInformationException(String message){
        super(message);
    }
}
