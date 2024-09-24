package print.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import print.models.PrintTask;

@Service
public class PrintService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthService authService;

    public String uploadDocument(MultipartFile file) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getToken());
        headers.set("If-None-Match", "*");
        headers.set("scan", "true");
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.set("file", new ByteArrayResource(file.getBytes(), file.getOriginalFilename()));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://api.eu12.print.services.sap" + "/dm/api/v1/rest/print-documents",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            System.out.println("Document uploaded successfully: " + response.getBody());
            return response.getBody();
        } catch (Exception e) {
            return "Error uploading document: " + e.getMessage();
        }
    }

    public String updatePrintTaskByItemId(String documentId, PrintTask printTask) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getToken());
        headers.set("If-None-Match", "*");
        headers.set("scan", "true");
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        String printTaskJson = objectMapper.writeValueAsString(printTask);

        HttpEntity<String> requestEntity = new HttpEntity<>(printTaskJson, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://api.eu12.print.services.sap" + "/qm/api/v1/rest/print-tasks/" + documentId,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
            );
            System.out.println("Updated Document successfully: " + response.getBody());
            return response.getBody();
        } catch (Exception e) {
            return "Error uploading document: " + e.getMessage();
        }
    }
}
