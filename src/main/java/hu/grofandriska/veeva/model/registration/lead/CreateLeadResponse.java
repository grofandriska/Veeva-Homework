package hu.grofandriska.veeva.model.registration.lead;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CreateLeadResponse {

    private String responseStatus;
    private List<CreateLeadItem> data;
}
