package com.example.blog_app.exception;


import com.example.blog_app.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

/*    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
String message = ex.getMessage();
ApiResponse apiResponse = new ApiResponse(message,false);
return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }*/

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleApiException(ApiException ex){
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message,false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>  handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
Map<String,String> res = new HashMap<>();
ex.getBindingResult().getAllErrors().forEach((error)->{
    String fieldName = ((FieldError)error).getField();
    String message = error.getDefaultMessage();
    res.put(fieldName,message);
});
return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
    }



}
