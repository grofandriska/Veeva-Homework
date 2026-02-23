package hu.grofandriska.veeva.model.registration.responsefields;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class VaultObject {
    private String name;
    private String label;
    @JsonProperty("label_plural")
    private String labelPlural;
}
