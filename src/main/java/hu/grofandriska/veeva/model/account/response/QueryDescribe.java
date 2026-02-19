package hu.grofandriska.veeva.model.account.response;

import lombok.Data;

import java.util.List;
@Data
public class QueryDescribe {

    private String type;
    private VaultObject object;
    private List<Field> fields;


}
