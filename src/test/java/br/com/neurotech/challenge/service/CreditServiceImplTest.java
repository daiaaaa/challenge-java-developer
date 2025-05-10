package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CreditServiceImplTest {
    @Mock
    private ClientService clientService;

    private CreditServiceImpl creditService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        creditService = new CreditServiceImpl(clientService);
    }

    @Test
    @DisplayName("Should return true for HATCH when income is within range")
    void shouldReturnTrueWhenEligibleForHatch() {
        NeurotechClient client = new NeurotechClient();
        client.setIncome(6000.0);
        client.setAge(30);

        when(clientService.get("1")).thenReturn(client);

        boolean result = creditService.checkCredit("1", VehicleModel.HATCH);
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false for HATCH when income is out of range")
    void shouldReturnFalseWhenNotEligibleForHatch() {
        NeurotechClient client = new NeurotechClient();
        client.setIncome(3000.0);
        client.setAge(30);

        when(clientService.get("1")).thenReturn(client);

        boolean result = creditService.checkCredit("1", VehicleModel.HATCH);
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return true for SUV when income and age meet the criteria")
    void shouldReturnTrueWhenEligibleForSuv() {
        NeurotechClient client = new NeurotechClient();
        client.setIncome(9000.0);
        client.setAge(35);

        when(clientService.get("1")).thenReturn(client);

        boolean result = creditService.checkCredit("1", VehicleModel.SUV);
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false for SUV when age is too low")
    void shouldReturnFalseWhenNotEligibleForSuvDueToAge() {
        NeurotechClient client = new NeurotechClient();
        client.setIncome(10000.0);
        client.setAge(19);

        when(clientService.get("1")).thenReturn(client);

        boolean result = creditService.checkCredit("1", VehicleModel.SUV);
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return false for SUV when income is too low")
    void shouldReturnFalseWhenNotEligibleForSuvDueToIncome() {
        NeurotechClient client = new NeurotechClient();
        client.setIncome(7000.0);
        client.setAge(35);

        when(clientService.get("1")).thenReturn(client);

        boolean result = creditService.checkCredit("1", VehicleModel.SUV);
        assertFalse(result);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when model is null")
    void shouldThrowExceptionWhenModelIsNull() {
        NeurotechClient client = new NeurotechClient("Ana", 30, 9000.0);
        when(clientService.get("1")).thenReturn(client);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                creditService.checkCredit("1", null)
        );

        assertEquals("Vehicle model must not be null", exception.getMessage());
    }

}