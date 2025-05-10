package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.exception.ClientNotFoundException;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private CreditService creditService;

    @Test
    @DisplayName("Should create a client successfully")
    void shouldCreateClientSuccessfully() throws Exception {
        when(clientService.save(any())).thenReturn("1");

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "name": "Daia",
                              "age": 25,
                              "income": 9000
                            }
                        """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/client/1")));
    }

    @Test
    @DisplayName("Should return 400 when client data is invalid")
    void shouldReturn400WhenClientDataIsInvalid() throws Exception {
        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "name": "",
                              "age": null,
                              "income": null
                            }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message.name").value("Name is required and cannot be blank"))
                .andExpect(jsonPath("$.message.age").value("Age is required"))
                .andExpect(jsonPath("$.message.income").value("Income is required"));
    }

    @Test
    @DisplayName("Should return client data when found")
    void shouldReturnClientById() throws Exception {
        NeurotechClient client = new NeurotechClient("Daia", 27, 9500.0);

        when(clientService.get("1")).thenReturn(client);

        mockMvc.perform(get("/api/client/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Daia"))
                .andExpect(jsonPath("$.age").value(27))
                .andExpect(jsonPath("$.income").value(9500.0));
    }

    @Test
    @DisplayName("Should return 404 when client is not found")
    void shouldReturn404WhenClientNotFound() throws Exception {
        when(clientService.get("999")).thenThrow(new ClientNotFoundException("999"));

        mockMvc.perform(get("/api/client/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Client with ID 999 not found"));
    }

    @Test
    @DisplayName("Should return true when client is eligible for credit")
    void shouldReturnTrueWhenClientIsEligibleForCredit() throws Exception {
        when(creditService.checkCredit("1", VehicleModel.HATCH)).thenReturn(true);

        mockMvc.perform(get("/api/client/1/credit?model=HATCH"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("Should return only clients eligible for fixed credit and hatch")
    void shouldReturnEligibleClientsForFixedCreditAndHatch() throws Exception {
        NeurotechClient alice = new NeurotechClient("Alice", 24, 9000.0);
        NeurotechClient bob = new NeurotechClient("Bob", 26, 9000.0);
        NeurotechClient carlos = new NeurotechClient("Carlos", 20, 9000.0);
        NeurotechClient dora = new NeurotechClient("Dora", 24, 16000.0);
        NeurotechClient eva = new NeurotechClient("Eva", 24, 3000.0);

        when(clientService.getAllClients()).thenReturn(List.of(alice, bob, carlos, dora, eva));

        mockMvc.perform(get("/api/client/eligible/fixed-hatch"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[0].income").value(9000.0));
    }



}