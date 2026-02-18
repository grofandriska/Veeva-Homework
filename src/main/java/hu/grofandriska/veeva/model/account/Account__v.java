package hu.grofandriska.veeva.model.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Account__v {
    private Long field_id;

    @NotBlank
    @Email
    private String email_cda__v;
}
