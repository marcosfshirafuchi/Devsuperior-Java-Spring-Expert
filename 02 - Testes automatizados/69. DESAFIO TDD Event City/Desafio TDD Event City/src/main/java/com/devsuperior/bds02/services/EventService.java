package com.devsuperior.bds02.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    @Autowired
    private CityRepository cityRepository;

    @Transactional
    public EventDTO update(Long id, EventDTO dto) {
        try {
            // Tenta obter uma referência à entidade Event existente
            // getReferenceById (ou getOne) é mais eficiente pois não vai ao banco se não precisar acessar propriedades
            // mas findById é mais seguro se você quiser garantir que existe antes
            // Aqui usarei a abordagem padrão de recuperar e atualizar
            
            // Usando getReferenceById (ou getOne no JpaRepository antigo) lança EntityNotFoundException se não existir ao acessar
            // Mas como é JpaRepository, o findById é mais comum se quisermos validar explicitamente
            
            // Vamos usar a abordagem com exceção de runtime padrão do Spring Data ou manualmente
            
            Event entity = repository.getReferenceById(id);
            
            entity.setName(dto.getName());
            entity.setDate(dto.getDate());
            entity.setUrl(dto.getUrl());
            
            // Busca a cidade pelo ID e associa ao evento
            // O teste assume que o ID da cidade é válido se passarmos um ID existente
            // Se o ID da cidade não existir, cityRepository pode lançar exceção ou retornar null/empty
            // cityRepository.findById(dto.getCityId()).orElseThrow(...) seria ideal
            
            City city = cityRepository.findById(dto.getCityId())
                    .orElseThrow(() -> new ResourceNotFoundException("City not found " + dto.getCityId()));
            
            entity.setCity(city);
            
            entity = repository.save(entity);
            
            return new EventDTO(entity);
            
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }
}
