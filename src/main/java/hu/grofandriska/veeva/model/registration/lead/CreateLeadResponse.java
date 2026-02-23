package hu.grofandriska.veeva.model.registration.lead;

import lombok.Data;

import java.util.List;

@Data
public class CreateLeadResponse {

    private String responseStatus;
    private List<CreateLeadItem> data;
}
