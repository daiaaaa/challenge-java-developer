package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;
    private final CreditService creditService;

    public ClientController(ClientService clientService, CreditService creditService) {
        this.clientService = clientService;
        this.creditService = creditService;
    }

    @PostMapping
    public ResponseEntity<Void> createClient(
            @RequestBody @Valid ClientRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        NeurotechClient client = new NeurotechClient();
        client.setName(request.name());
        client.setAge(request.age());
        client.setIncome(request.income());

        String id = clientService.save(client);

        URI location = uriBuilder.path("/api/client/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable String id) {
        NeurotechClient client = clientService.get(id);

        ClientResponse response = new ClientResponse(
                client.getName(),
                client.getAge(),
                client.getIncome()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/credit")
    public ResponseEntity<Boolean> checkCredit(
            @PathVariable String id,
            @RequestParam VehicleModel model
    ) {
        return ResponseEntity.ok(creditService.checkCredit(id, model));
    }


    @GetMapping("/eligible/fixed-hatch")
    public ResponseEntity<List<ClientSummary>> getEligibleClientsForFixedAndHatch() {
        List<ClientSummary> result = clientService.getAllClients().stream()
                .filter(c -> c.getAge() >= 23 && c.getAge() <= 25)
                .filter(c -> c.getIncome() >= 5000 && c.getIncome() <= 15000)
                .map(c -> new ClientSummary(c.getName(), c.getIncome()))
                .toList();

        return ResponseEntity.ok(result);
    }

}
