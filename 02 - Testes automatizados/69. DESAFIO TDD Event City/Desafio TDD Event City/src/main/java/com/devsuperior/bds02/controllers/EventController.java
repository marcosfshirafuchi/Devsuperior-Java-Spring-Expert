package com.devsuperior.bds02.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.services.EventService;

/**
 * Controlador REST responsável por gerenciar as requisições relacionadas a eventos.
 */
@RestController
@RequestMapping(value = "/events")
public class EventController {

    // Injeção de dependência do serviço de eventos
    @Autowired
    private EventService service;

    /**
     * Endpoint para atualizar um evento existente.
     * 
     * @param id O ID do evento a ser atualizado, passado na URL.
     * @param dto O objeto EventDTO contendo os novos dados do evento, passado no corpo da requisição.
     * @return ResponseEntity contendo o EventDTO atualizado e o status HTTP 200 (OK).
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<EventDTO> update(@PathVariable Long id, @RequestBody EventDTO dto) {
        // Chama o serviço para atualizar o evento com o ID fornecido e os novos dados
        dto = service.update(id, dto);
        
        // Retorna o DTO atualizado no corpo da resposta com status 200 OK
        return ResponseEntity.ok().body(dto);
    }
}
