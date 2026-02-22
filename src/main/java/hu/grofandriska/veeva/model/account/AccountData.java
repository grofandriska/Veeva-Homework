package hu.grofandriska.veeva.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountData {
    @JsonProperty("email_cda__v")
    private String email;
    @JsonProperty("id")
    private String id;
}