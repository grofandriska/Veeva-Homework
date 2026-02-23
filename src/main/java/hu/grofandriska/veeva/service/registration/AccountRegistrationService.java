package hu.grofandriska.veeva.service.registration;

import hu.grofandriska.veeva.model.account.Lead;
import hu.grofandriska.veeva.model.registration.RegistrationResult;
import hu.grofandriska.veeva.model.registration.RegistrationServerResponse;
import hu.grofandriska.veeva.model.registration.lead.CreateLeadResponse;
import hu.grofandriska.veeva.service.csv.CSVConverter;
import hu.grofandriska.veeva.service.lead.LeadService;
import hu.grofandriska.veeva.service.vql.VQLService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class AccountRegistrationService {

    private final CSVConverter converter;
    private final VQLService emailService;
    private final LeadService leadService;


    public AccountRegistrationService(CSVConverter converter, VQLService emailService, LeadService leadService) {
        this.converter = converter;
        this.emailService = emailService;
        this.leadService = leadService;
    }

    public RegistrationResult register(String sessionId, Lead lead, String vaultId) throws Exception {
        RegistrationServerResponse serverResponse = emailService.findAccountByEmail(lead, sessionId);

        if ("WARNING".equals(serverResponse.getResponseStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Vault error: " + serverResponse
            );
        }
        if (!"SUCCESS".equals(serverResponse.getResponseStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Unexpected Vault response");
        }

        int size = serverResponse.getResponseDetails().getSize();

        if (size == 1) {
            return new RegistrationResult(HttpStatus.OK, vaultId);
        }

        CreateLeadResponse leadResponse;

        String leadObj = converter.convertLeadToCsv(lead);
        leadResponse = leadService.createLead(sessionId, leadObj);

        if (leadResponse.getData() == null || leadResponse.getData().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Invalid Vault response - no data returned");
        }

        var item = leadResponse.getData().get(0);

        if (!"SUCCESS".equals(item.getResponseStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Account already exists"
            );
        }
        return new RegistrationResult(HttpStatus.CREATED, vaultId);
    }
}
