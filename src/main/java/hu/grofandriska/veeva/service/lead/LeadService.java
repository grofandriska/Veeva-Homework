package hu.grofandriska.veeva.service.lead;

import hu.grofandriska.veeva.model.registration.lead.CreateLeadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LeadService {

    private final String leadUrl;
    private final RestTemplate restTemplate;

    public LeadService(@Value("${lead.url}") String leadUrl,
                       RestTemplate restTemplate) {
        this.leadUrl = leadUrl;
        this.restTemplate = restTemplate;
    }

    public CreateLeadResponse createLead(String sessionId, String csvPayload) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(sessionId);
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(csvPayload, headers);

        ResponseEntity<CreateLeadResponse> response =
                restTemplate.exchange(
                        leadUrl,
                        HttpMethod.POST,
                        request,
                        CreateLeadResponse.class
                );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Vault create lead failed. HTTP: "
                            + response.getStatusCode()
            );
        }

        return response.getBody();
    }
}