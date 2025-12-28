package br.com.fiap.paymentservice.adapter.prensenter.handler;

import br.com.fiap.paymentservice.application.exceptions.NotFoundException;
import jakarta.validation.Path;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNotFoundException_returns404() {
        NotFoundException ex = new NotFoundException("not found");
        ResponseEntity<ErrorResponse> res = handler.handleNotFoundException(ex);
        assertEquals(404, res.getStatusCode().value());
        assertEquals("not found", res.getBody().error());
    }

    @Test
    void handleAnyException_returns500() {
        Exception ex = new Exception("boom");
        ResponseEntity<ErrorResponse> res = handler.handleAnyException(ex);
        assertEquals(500, res.getStatusCode().value());
        assertEquals("boom", res.getBody().error());
    }

    @Test
    void handleConstraintViolation_buildsList() {
        ConstraintViolation<String> v = mock(ConstraintViolation.class);
        when(v.getPropertyPath()).thenReturn(PathImpl.createPathFromString("field"));
        when(v.getMessage()).thenReturn("must not be null");
        ConstraintViolationException ex = new ConstraintViolationException(Set.of(v));

        ResponseEntity<ValidationErrorResponse> res = handler.handleConstraintViolation(ex);
        assertEquals(400, res.getStatusCode().value());
        assertTrue(res.getBody().errors().get(0).contains("must not be null"));
    }

    @Test
    void handleValidationErrors_buildsList() {
        BindingResult binding = mock(BindingResult.class);
        when(binding.getFieldErrors()).thenReturn(List.of(new FieldError("obj","field","must not be empty")));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, binding);

        ResponseEntity<ValidationErrorResponse> res = handler.handleValidationErrors(ex);
        assertEquals(400, res.getStatusCode().value());
        assertTrue(res.getBody().errors().get(0).contains("must not be empty"));
    }
}
