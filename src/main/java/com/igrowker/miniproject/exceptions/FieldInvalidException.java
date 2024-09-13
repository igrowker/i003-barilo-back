package com.igrowker.miniproject.exceptions;

public class FieldInvalidException extends BadRequestException{
    public FieldInvalidException(String message){
        super(message);
    }
}
