package hu.grofandriska.veeva.model.account.response;

import java.util.List;

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

    // Getterek és Setterek
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isRequired() { return required; }
    public void setRequired(boolean required) { this.required = required; }

    public boolean isUnique() { return unique; }
    public void setUnique(boolean unique) { this.unique = unique; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getStatus() { return status; }
    public void setStatus(List<String> status) { this.status = status; }

    public boolean isEncrypted() { return encrypted; }
    public void setEncrypted(boolean encrypted) { this.encrypted = encrypted; }

    public Integer getMax_length() { return max_length; }
    public void setMax_length(Integer max_length) { this.max_length = max_length; }

    public String getFormat_mask() { return format_mask; }
    public void setFormat_mask(String format_mask) { this.format_mask = format_mask; }
}
