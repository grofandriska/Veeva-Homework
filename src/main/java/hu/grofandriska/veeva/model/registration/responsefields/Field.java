package hu.grofandriska.veeva.model.registration.responsefields;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Field {
    private String label;
    private String type;
    private boolean required;
    private boolean unique;
    private String name;
    private List<String> status;
    private boolean encrypted;
    @JsonProperty("max_length")
    private Integer maxLength;
    @JsonProperty("format_mask")
    private String formatMask;
}
