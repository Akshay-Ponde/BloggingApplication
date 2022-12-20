package com.blogging.exceptions;

import com.blogging.payloads.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseApi> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
    {
        String msg = ex.getMessage();
        return new ResponseEntity<>(new ResponseApi(msg , false) , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> argumentNotValidException(MethodArgumentNotValidException ex)
    {
        Map<String ,String> err = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error)-> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            err.put(fieldName,message);
        });

        return new ResponseEntity<>(err , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtTokenException.class)
    public ResponseEntity<ResponseApi> jwtTokenInvalidExceptionHandler(JwtTokenException ex)
    {
        String msg = ex.getMessage();
        return new ResponseEntity<>(new ResponseApi(msg , false) , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<ResponseApi> messageNotWritableExceptionHandler(HttpMessageNotWritableException ex)
    {
        String msg = ex.getMessage();
        return new ResponseEntity<>(new ResponseApi(msg , false) , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ResponseApi> multipartExceptionHandler(MultipartException ex)
    {
        String msg = ex.getMessage();
        return new ResponseEntity<>(new ResponseApi(msg , false) , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(StringIndexOutOfBoundsException.class)
    public ResponseEntity<ResponseApi> fileNotSelectedExceptionHandler(StringIndexOutOfBoundsException ex)
    {
        String msg = ex.getMessage();
        return new ResponseEntity<>(new ResponseApi(msg , false) , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ResponseApi> fileNotSFoundExceptionHandler(FileNotFoundException ex)
    {
        String msg = ex.getMessage();
        return new ResponseEntity<>(new ResponseApi(msg , false) , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseApi> iOExceptionHandler(FileNotFoundException ex)
    {
        String msg = ex.getMessage();
        return new ResponseEntity<>(new ResponseApi(msg , false) , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidLoginCredentialsException.class)
    public ResponseEntity<ResponseApi> InvalidLoginCredentialExceptionHandler(InvalidLoginCredentialsException ex)
    {
        String msg = ex.getMessage();
        return new ResponseEntity<>(new ResponseApi(msg , false) , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(userNameAlreadyExistsException.class)
    public ResponseEntity<ResponseApi> InvalidLoginCredentialExceptionHandler(userNameAlreadyExistsException ex)
    {
        String msg = ex.getMessage();
        return new ResponseEntity<>(new ResponseApi(msg , false) , HttpStatus.BAD_REQUEST);
    }


}
