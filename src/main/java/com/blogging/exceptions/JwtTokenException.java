package com.blogging.exceptions;

public class JwtTokenException extends RuntimeException{
    String message;

    public JwtTokenException(String message )
    {
        super(String.format("JWT Token Exception : %s" , message));
        this.message = message;
    }
}
