package com.hermes.core.common.error;

import com.hermes.core.common.error.api.ApiError;
import com.hermes.core.common.error.exc.BadRequestException;
import com.hermes.core.common.error.exc.NotFoundException;
import com.hermes.core.common.error.exc.UnauthorizedException;
import com.hermes.core.common.session.exc.InvalidJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class RestExceptionHandler {
  @ExceptionHandler({NotFoundException.class})
  public Mono<ResponseEntity<ApiError> > notFoundException(NotFoundException exc) {
    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(exc)));
  }

  @ExceptionHandler({BadRequestException.class})
  public Mono<ResponseEntity<ApiError>> badRequestException(BadRequestException exc) {
    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(exc)));
  }

  @ExceptionHandler({UnauthorizedException.class})
  public Mono<ResponseEntity<ApiError>> unauthorizedException(UnauthorizedException exc) {
    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError(exc)));
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public Mono<ResponseEntity<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException exc) {
    var errors = new HashMap<String, String>();
    exc.getBindingResult().getAllErrors().forEach(error -> {
      var fieldName = ((FieldError) error).getField();
      var errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors));
  }

  @ExceptionHandler({InvalidJwtException.class})
  public Mono<ResponseEntity<Object>> invalidJwtException(InvalidJwtException exc) {
    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError(exc)));
  }

  @ExceptionHandler({RuntimeException.class})
  public Mono<ResponseEntity<Object>> handleRuntimeException(RuntimeException exc) {
    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiError(exc)));
  }
}
