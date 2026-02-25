package org.wasim.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse<T> {
    private boolean success;
    private String message;
    private ExceptionStatus staus;
    private T errors;
    private LocalDateTime timestamp;
}
