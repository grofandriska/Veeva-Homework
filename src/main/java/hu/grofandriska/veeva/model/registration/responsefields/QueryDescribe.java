package hu.grofandriska.veeva.model.registration.responsefields;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@RequiredArgsConstructor
public class QueryDescribe {
    private String type;
    private VaultObject object;
    private List<Field> fields;
}
