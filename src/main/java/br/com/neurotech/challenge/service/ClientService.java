package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.NeurotechClient;

import java.util.List;


public interface ClientService {
	
	/**
	 * Salva um novo cliente
	 * 
	 * @return ID do cliente recém-salvo
	 */
	String save(NeurotechClient client);
	
	/**
	 * Recupera um cliente baseado no seu ID
	 */
	NeurotechClient get(String id);

	List<NeurotechClient> getAllClients();

}
