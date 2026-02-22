package hu.grofandriska.veeva.model.registration;

import hu.grofandriska.veeva.model.account.AccountData;
import hu.grofandriska.veeva.model.registration.responsefields.QueryDescribe;
import hu.grofandriska.veeva.model.registration.responsefields.ResponseDetails;
import lombok.Data;

import java.util.List;

@Data
public class RegistrationServerResponse {
    private String responseStatus;
    private QueryDescribe queryDescribe;
    private ResponseDetails responseDetails;
    private List<AccountData> data;
}
