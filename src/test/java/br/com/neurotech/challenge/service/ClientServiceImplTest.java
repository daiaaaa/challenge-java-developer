package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exception.ClientNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceImplTest {

    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        clientService = new ClientServiceImpl();
    }

    @Test
    @DisplayName("Should save a client and return its ID")
    void shouldSaveClientAndReturnId() {
        NeurotechClient client = new NeurotechClient();
        client.setName("Daia");
        client.setAge(30);
        client.setIncome(9500.0);

        String id = clientService.save(client);

        assertNotNull(id);
        NeurotechClient saved = clientService.get(id);
        assertEquals("Daia", saved.getName());
        assertEquals(30, saved.getAge());
        assertEquals(9500.0, saved.getIncome());
    }

    @Test
    @DisplayName("Should throw exception when client ID is not found")
    void shouldThrowExceptionWhenClientNotFound() {
        String invalidId = "999";

        assertThrows(ClientNotFoundException.class, () -> {
            clientService.get(invalidId);
        });
    }

    @Test
    @DisplayName("Should return all saved clients")
    void shouldReturnAllClients() {
        NeurotechClient c1 = new NeurotechClient();
        c1.setName("Ana");
        c1.setAge(24);
        c1.setIncome(6000.0);

        NeurotechClient c2 = new NeurotechClient();
        c2.setName("Joao");
        c2.setAge(40);
        c2.setIncome(12000.0);

        clientService.save(c1);
        clientService.save(c2);

        List<NeurotechClient> result = clientService.getAllClients();

        assertEquals(2, result.size());
    }
}
