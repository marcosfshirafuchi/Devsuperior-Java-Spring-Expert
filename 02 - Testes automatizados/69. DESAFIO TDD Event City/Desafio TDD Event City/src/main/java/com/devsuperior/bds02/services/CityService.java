package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

/**
 * Serviço para gerenciar a lógica de negócio das cidades.
 */
@Service
public class CityService {

    // Injeção de dependência do repositório de cidades
    @Autowired
    private CityRepository repository;

    /**
     * Busca todas as cidades no banco de dados, ordenadas por nome.
     * A transação é marcada como somente leitura para otimizar a performance.
     * 
     * @return Uma lista de CityDTO representando todas as cidades.
     */
    @Transactional(readOnly = true)
    public List<CityDTO> findAll() {
        // Busca todas as entidades City, ordenando pelo campo "name"
        List<City> list = repository.findAll(Sort.by("name"));
        
        // Converte a lista de entidades City para uma lista de CityDTO
        return list.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
    }

    /**
     * Insere uma nova cidade no banco de dados.
     * 
     * @param dto Objeto contendo os dados da nova cidade.
     * @return O objeto CityDTO da cidade recém-criada, incluindo o ID gerado.
     */
    @Transactional
    public CityDTO insert(CityDTO dto) {
        // Cria uma nova entidade City e define o nome a partir do DTO
        City entity = new City();
        entity.setName(dto.getName());
        
        // Salva a entidade no banco de dados
        entity = repository.save(entity);
        
        // Retorna o DTO correspondente à entidade salva
        return new CityDTO(entity);
    }

    /**
     * Deleta uma cidade do banco de dados pelo seu ID.
     * 
     * @param id O ID da cidade a ser deletada.
     * @throws ResourceNotFoundException Se o ID não for encontrado.
     * @throws DatabaseException Se ocorrer uma violação de integridade referencial.
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cidade não encontrada");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }
}
