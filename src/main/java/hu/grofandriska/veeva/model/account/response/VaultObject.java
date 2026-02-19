package hu.grofandriska.veeva.model.account.response;

public class VaultObject {

    private String name;
    private String label;
    private String label_plural;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getLabel_plural() { return label_plural; }
    public void setLabel_plural(String label_plural) { this.label_plural = label_plural; }
}
