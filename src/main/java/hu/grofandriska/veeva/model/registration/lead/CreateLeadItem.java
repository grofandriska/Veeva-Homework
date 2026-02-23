package hu.grofandriska.veeva.model.registration.lead;

import hu.grofandriska.veeva.model.registration.responsefields.ErrorResponse;
import lombok.Data;

import java.util.List;

@Data
public class CreateLeadItem {

    private String responseStatus;
    private CreatedLead data;
    private List<ErrorResponse> errors;
}