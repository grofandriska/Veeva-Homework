package hu.grofandriska.veeva.model.registration.responsefields;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResponseDetails {
    private int pageSize;
    private int pageOffset;
    private int size;
    private int total;
}
