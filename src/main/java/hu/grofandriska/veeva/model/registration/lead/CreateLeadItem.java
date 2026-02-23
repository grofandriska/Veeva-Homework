package hu.grofandriska.veeva.model.registration.lead;

import hu.grofandriska.veeva.model.registration.responsefields.ErrorResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CreateLeadItem {

    private String responseStatus;
    private CreatedLead data;
    private List<ErrorResponse> errors;
}