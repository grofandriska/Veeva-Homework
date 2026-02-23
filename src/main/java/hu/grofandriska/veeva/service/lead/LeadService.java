package hu.grofandriska.veeva.service.lead;

import hu.grofandriska.veeva.model.registration.lead.CreateLeadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class LeadService {

    private final String leadUrl;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;

    public LeadService(@Value("${lead.url}")String leadUrl,
                       ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.leadUrl = leadUrl;
    }

    public CreateLeadResponse createLead(String sessionId, String csvPayload) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(leadUrl))
                .header("Authorization", sessionId)
                .header("Content-Type", "text/csv")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(csvPayload, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException(
                    "Vault create failed. HTTP: " + response.statusCode() +
                            " Body: " + response.body()
            );
        }
        return objectMapper.readValue(response.body(), CreateLeadResponse.class);
    }
}
