package hu.grofandriska.veeva.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Lead {
    @NotBlank
    @JsonProperty("first_name__c")
    private String firstName;
    @NotBlank
    @JsonProperty("last_name__c")
    private String lastName;
    @NotBlank
    @Email
    @NotEmpty
    @JsonProperty("email_cda__v")
    private String emailAddress;
}
