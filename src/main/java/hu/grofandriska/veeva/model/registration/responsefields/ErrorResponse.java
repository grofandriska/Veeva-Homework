package hu.grofandriska.veeva.model.registration.responsefields;

import lombok.Data;

@Data
public class ErrorResponse {

    private String type;
    private String message;
}
