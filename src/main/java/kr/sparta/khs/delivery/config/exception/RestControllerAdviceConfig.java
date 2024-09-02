package kr.sparta.khs.delivery.config.exception;

import kr.sparta.khs.delivery.config.holder.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@Component
@RestControllerAdvice
public class RestControllerAdviceConfig {




    @ResponseBody
   	@ExceptionHandler(UsernameNotFoundException.class)
   	protected ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException exception) {

   		log.error("runtime exception! :: {}", exception.toString());

   		return new ResponseEntity<>(Result.failure(exception.getMessage()), HttpStatus.FORBIDDEN); // httpStatus
   	}

    @ResponseBody
   	@ExceptionHandler(RuntimeException.class)
   	protected ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {

   		log.error("runtime exception! :: {}", exception.toString());

   		return new ResponseEntity<>(Result.failure(exception.getClass().getName()), HttpStatus.BAD_REQUEST); // httpStatus
   	}

}
