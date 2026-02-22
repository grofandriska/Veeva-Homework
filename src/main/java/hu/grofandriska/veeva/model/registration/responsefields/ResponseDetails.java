package hu.grofandriska.veeva.model.registration.responsefields;

import lombok.Data;

@Data
public class ResponseDetails {
    private int pageSize;
    private int pageOffset;
    private int size;
    private int total;
}
