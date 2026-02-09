package com.example.store.store_management.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Setter
@Getter
@NoArgsConstructor
public class GenericResponse {
    static final String ERROR = "error";
    static final String WARNING = "warning";
    static final String SUCCESS = "success";

    String id;
    Integer status;
    String message;
    String type;
    String stackTrace;

    private GenericResponse(Integer status, String message, String type, String id) {
        this.status = status;
        this.message = message;
        this.type = type;
        this.id = id;
    }

    private GenericResponse(Integer status, String message, String type) {
        this.status = status;
        this.message = message;
        this.type = type;
    }

    public static ResponseEntity<GenericResponse> success() {
        return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(), "", SUCCESS));
    }

    public static ResponseEntity<GenericResponse> success(String message) {
        return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(), message, SUCCESS));
    }

    public static ResponseEntity<GenericResponse> success(String message, String id) {
        return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(), message, SUCCESS, id));
    }

    public static ResponseEntity<GenericResponse> warning(String message) {
        return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(), message, WARNING));
    }

    public static ResponseEntity<GenericResponse> warning(String message, String id) {
        return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(), message, WARNING, id));
    }

    public static ResponseEntity<GenericResponse> error(String message) {
        return ResponseEntity.internalServerError()
                .body(new GenericResponse(INTERNAL_SERVER_ERROR.value(), message, ERROR));
    }

    public static ResponseEntity<GenericResponse> error(String message, String id) {
        return ResponseEntity.internalServerError()
                .body(new GenericResponse(INTERNAL_SERVER_ERROR.value(), message, ERROR, id));
    }


    public static ResponseEntity<GenericResponse> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new GenericResponse(status.value(), message, ERROR));
    }

    public static ResponseEntity<GenericResponse> error(HttpStatus status, String message, String id) {
        return ResponseEntity.status(status).body(new GenericResponse(status.value(), message, ERROR, id));
    }

}
