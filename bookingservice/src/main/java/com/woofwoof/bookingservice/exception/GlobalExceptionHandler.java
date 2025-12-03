package com.woofwoof.bookingservice.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.woofwoof.bookingservice.service.KafkaLogService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

        private KafkaLogService logger;

        public GlobalExceptionHandler(KafkaLogService logger) {
                this.logger = logger;
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ApiError> handleNotFound(EntityNotFoundException e, HttpServletRequest req) {
                logger.sendLog("WARN", "Erreur l'entité n'existe pas");

                ApiError error = new ApiError(
                                LocalDateTime.now(),
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase(),
                                "L'entité n'existe pas",
                                req.getRequestURI());

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(error);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiError> handleArgumentNotValid(MethodArgumentNotValidException ex,
                        HttpServletRequest req) {
                String errorMessage = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .collect(Collectors.joining(", "));

                logger.sendLog("INFO", String.format("Erreur de validation : %s", errorMessage));

                ApiError error = new ApiError(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                "Erreur de validation : " + errorMessage,
                                req.getRequestURI());

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(error);
        }

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                        HttpServletRequest req) {
                Class<?> requiredClass = ex.getRequiredType();
                String requiredType = requiredClass != null ? requiredClass.getSimpleName() : "inconnu";
                String value = String.valueOf(ex.getValue());

                logger.sendLog("INFO",
                                String.format("Erreur de type dans l'URL : attendu %s, reçu %s",
                                                requiredType,
                                                value));

                ApiError error = new ApiError(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                "Le paramètre '" + ex.getName() + "' doit être de type "
                                                + requiredType,
                                req.getRequestURI());

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(error);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ApiError> handleIllegalArgument(
                        IllegalArgumentException ex,
                        HttpServletRequest req) {
                logger.sendLog("INFO", String.format("Erreur d'argument : %s", ex.getMessage()));

                ApiError error = new ApiError(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                ex.getMessage(),
                                req.getRequestURI());

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(error);
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                        HttpServletRequest req) {
                logger.sendLog("INFO", "Erreur de lecture du JSON : " + ex.getMessage());

                ApiError error = new ApiError(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                "Format de requête invalide (JSON malformé ou type de donnée incorrect)",
                                req.getRequestURI());

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(error);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiError> handleGlobalError(Exception e, HttpServletRequest req) {
                logger.sendLog("WARN", String.format("Erreur interne au server lors d'un booking : %s", e));
                ApiError error = new ApiError(
                                LocalDateTime.now(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                                "Une erreur interne est survenue.",
                                req.getRequestURI());

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(error);
        }
}