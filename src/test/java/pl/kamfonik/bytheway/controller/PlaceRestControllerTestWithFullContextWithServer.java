package pl.kamfonik.bytheway.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import pl.kamfonik.bytheway.dto.rest.PlaceDto;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-tests.properties")
class PlaceRestControllerTestWithFullContextWithServer {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldFindPartynice(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("X-CSRF-Token","TO BE DEFINED!!!!!!!!!"); // TO BE DEFINED
        HttpEntity<String> request = new HttpEntity<>("query=partynice", headers);

        ResponseEntity<PlaceDto> placeDtoResponseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/place/find", request, PlaceDto.class);
        assertFalse(placeDtoResponseEntity.getStatusCode().isError());
        assertTrue(placeDtoResponseEntity.getBody().getName().toLowerCase().contains("partynice"));
    }
}