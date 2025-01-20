package org.team6.coffeebeanery.common.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(org.team6.coffeebeanery.common.exception.ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            org.team6.coffeebeanery.common.exception.ResourceNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(org.team6.coffeebeanery.common.exception.InvalidInputException.class)
    public ResponseEntity<ErrorDetails> handleInvalidInputException(
            org.team6.coffeebeanery.common.exception.InvalidInputException ex) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), "예기치 못한 에러",
                                                     System.currentTimeMillis());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 주문 정보 없는 이메일
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handle(IllegalArgumentException ex){
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // 장바구니 null, 재고를 초과하는 주문
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorDetails> handle(IllegalStateException ex){
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // @Valid 검증 실패 시 발생
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handle(MethodArgumentNotValidException ex){
        String message = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> (FieldError) error)
                .sorted(Comparator.comparing(FieldError::getField))
                .map(error -> error.getField() + "-" + error.getDefaultMessage())
                .collect(Collectors.joining("\n"));

        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), message, System.currentTimeMillis());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // @Url 검증 실패 시 발생
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetails> handle(ConstraintViolationException ex){
        String message = ex.getMessage();

        // 에러 메시지만 추출
        Pattern pattern = Pattern.compile("interpolatedMessage='([^']*)'");
        Matcher matcher = pattern.matcher(message);

        if(matcher.find()) {
            message = matcher.group(1);
        }

        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), message, System.currentTimeMillis());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}