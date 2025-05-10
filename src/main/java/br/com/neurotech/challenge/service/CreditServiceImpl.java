package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import org.springframework.stereotype.Service;

@Service
public class CreditServiceImpl implements CreditService{

    private final ClientService clientService;

    public CreditServiceImpl(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public boolean checkCredit(String clientId, VehicleModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Vehicle model must not be null");
        }

        NeurotechClient client = clientService.get(clientId);

        double income = client.getIncome();
        int age = client.getAge();

        return switch (model) {
            case HATCH -> income >= 5000 && income <= 15000;
            case SUV -> income > 8000 && age > 20;
        };
    }
}
