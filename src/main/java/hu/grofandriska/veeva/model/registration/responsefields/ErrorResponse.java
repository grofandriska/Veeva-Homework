package hu.grofandriska.veeva.model.registration.responsefields;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponse {

    private String type;
    private String message;
}
