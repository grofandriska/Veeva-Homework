package hu.grofandriska.veeva.model.account.response;

import lombok.Data;

import java.util.List;

@Data
public class AccountResponse {
    private String responseStatus;
    private QueryDescribe queryDescribe;
    private ResponseDetails responseDetails;
    private List<AccountData> data;
}
