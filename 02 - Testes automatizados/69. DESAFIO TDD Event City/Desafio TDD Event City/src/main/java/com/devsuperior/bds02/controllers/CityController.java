package com.devsuperior.bds02.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.services.CityService;

/**
 * <h1> DEVSUPERIOR - Java Spring Expert - Capítulo: Testes automatizados</h1>
 * DESAFIO: DESAFIO TDD Event City
 * <p>
 * <b>Note:</b> Desenvolvido na linguagem Java.
 *
 * @author  Marcos Ferreira Shirafuchi
 * @version 1.0
 * @since   15/03/2026
 */


/**
 * Controlador REST responsável por gerenciar as requisições relacionadas a cidades.
 */
@RestController
@RequestMapping(value = "/cities")
public class CityController {

    // Injeção de dependência do serviço de cidades
    @Autowired
    private CityService service;

    /**
     * Endpoint para buscar todas as cidades.
     * As cidades são retornadas ordenadas por nome.
     * 
     * @return ResponseEntity contendo a lista de CityDTO e o status HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<CityDTO>> findAll() {
        // Chama o serviço para obter a lista de cidades
        List<CityDTO> list = service.findAll();
        
        // Retorna a lista no corpo da resposta com status 200 OK
        return ResponseEntity.ok().body(list);
    }

    /**
     * Endpoint para inserir uma nova cidade.
     * 
     * @param dto O objeto CityDTO enviado no corpo da requisição (request body).
     * @return ResponseEntity com o status HTTP 201 (Created), o cabeçalho Location
     *         apontando para a URL do novo recurso, e o corpo da resposta contendo
     *         o DTO da cidade criada.
     */
    @PostMapping
    public ResponseEntity<CityDTO> insert(@RequestBody CityDTO dto) {
        // Chama o serviço para inserir a nova cidade e retorna o DTO atualizado (com ID)
        dto = service.insert(dto);
        
        // Cria a URI para o novo recurso criado.
        // Pega a URI da requisição atual (ex: /cities) e adiciona o ID da nova cidade (ex: /cities/4)
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        
        // Retorna a resposta com o status 201 Created, a URI no cabeçalho Location e o DTO no corpo
        return ResponseEntity.created(uri).body(dto);
    }

    /**
     * Endpoint para deletar uma cidade pelo seu ID.
     * 
     * @param id O ID da cidade a ser deletada, passado na URL.
     * @return ResponseEntity com status 204 (No Content) se a deleção for bem-sucedida.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Chama o serviço para deletar a cidade com o ID fornecido
        service.delete(id);
        
        // Retorna uma resposta vazia com o status 204 No Content
        return ResponseEntity.noContent().build();
    }
}
