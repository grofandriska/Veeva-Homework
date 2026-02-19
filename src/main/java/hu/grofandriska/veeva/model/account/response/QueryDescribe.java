package hu.grofandriska.veeva.model.account.response;

import java.util.List;

public class QueryDescribe {

    private String type;
    private VaultObject object;
    private List<Field> fields;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public VaultObject getObject() { return object; }
    public void setObject(VaultObject object) { this.object = object; }

    public List<Field> getFields() { return fields; }
    public void setFields(List<Field> fields) { this.fields = fields; }
}
