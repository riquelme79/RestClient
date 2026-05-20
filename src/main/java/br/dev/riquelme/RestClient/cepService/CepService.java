/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.dev.riquelme.RestClient.cepService;

import java.util.Optional;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CepService {

    private final RestClient restClient;

    public CepService(RestClient restClient) {
        this.restClient = restClient;
    }

    public Optional<CepDTO> buscarCep(String cep) {

        try {
            CepDTO result = restClient.get()
                    .uri("/{cep}/json", cep)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            (request, response) -> {
                                throw new RuntimeException("CEP não encontrado: " + cep);
                            })
                    .body(CepDTO.class);

            return Optional.of(result);
            
        } catch (RuntimeException ex) {
            
            return Optional.empty();
        }
    }
}
