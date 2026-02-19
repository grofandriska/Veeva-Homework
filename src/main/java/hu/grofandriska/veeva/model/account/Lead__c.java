package hu.grofandriska.veeva.model.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Lead__c {
    @NotBlank
    private String first_name__c;
    @NotBlank
    private String last_name__c;
    @NotBlank
    @Email
    @NotEmpty
    private String email__c;
}
