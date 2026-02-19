package hu.grofandriska.veeva.model.account.response;

import lombok.Data;

import java.util.List;

@Data
public class Field {

    private String label;
    private String type;
    private boolean required;
    private boolean unique;
    private String name;
    private List<String> status;
    private boolean encrypted;
    private Integer max_length;
    private String format_mask;

}
