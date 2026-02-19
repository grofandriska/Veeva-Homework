package hu.grofandriska.veeva.model.vault;

import hu.grofandriska.veeva.model.account.Account__v;
import lombok.Data;

import java.util.List;

@Data
public class VaultQueryResponse {
    private String responseStatus;
    private List<Account__v> data;

    public List<Account__v> getDataNow() {
        return data;
    }

}
