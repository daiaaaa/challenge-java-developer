package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exception.ClientNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ClientServiceImpl implements ClientService {

    public final Map<String, NeurotechClient> clientStorage =new HashMap<>();

    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public String save(NeurotechClient client) {
        String id = String.valueOf(idGenerator.incrementAndGet());
        clientStorage.put(id, client);
        return id;
    }

    @Override
    public NeurotechClient get(String id) {
        NeurotechClient client = clientStorage.get(id);
        if (client == null) {
            throw new ClientNotFoundException(id);
        }
        return client;
    }


    @Override
    public List<NeurotechClient> getAllClients() {
        return new ArrayList<>(clientStorage.values());
    }

}
