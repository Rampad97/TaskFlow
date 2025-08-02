package com.meta.TaskFlow.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionsHandler {
    // Validaciones
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handlerValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    // Maneja la excepción personalizada de recurso no encontrado
    @ExceptionHandler(RecursoNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRecursoNoEncontrado(RecursoNotFoundException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
    }

    // Argumentos inválidos
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    // Excepciones no controladas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", "Error interno del servidor: " +  ex.toString());
        return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
