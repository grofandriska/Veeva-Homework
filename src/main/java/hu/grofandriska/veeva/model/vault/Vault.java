package hu.grofandriska.veeva.model.vault;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Vault {
    private String id;
    private String name;
    private String url;
}
