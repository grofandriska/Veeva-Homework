package hu.grofandriska.veeva.model.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class AccountRegistrationResponse {
    private String vaultId;
    private RegistrationResult registrationResult;
}
