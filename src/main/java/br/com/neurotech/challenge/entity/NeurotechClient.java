package br.com.neurotech.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NeurotechClient {
	private String name;
	private Integer age;
	private Double income;
}
