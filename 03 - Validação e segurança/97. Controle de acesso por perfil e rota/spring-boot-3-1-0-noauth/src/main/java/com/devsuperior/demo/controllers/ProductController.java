package com.devsuperior.demo.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Importa a anotação para controle de acesso baseado em papéis
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.demo.dto.ProductDTO;
import com.devsuperior.demo.services.ProductService;

/**
 * Controlador REST para gerenciar operações relacionadas a produtos.
 * Expõe endpoints HTTP para listar, buscar por ID e inserir produtos.
 */
@RestController // Indica que esta classe é um controlador REST e seus métodos retornam dados diretamente.
@RequestMapping(value = "/products") // Mapeia todas as requisições que começam com "/products" para este controlador.
public class ProductController {

    // Injeta uma instância de ProductService, que contém a lógica de negócio para produtos.
    @Autowired
    private ProductService productService;

    /**
     * Endpoint para buscar todos os produtos.
     *
     * @return ResponseEntity contendo uma lista de ProductDTOs e status HTTP 200 (OK).
     */
    @GetMapping // Mapeia requisições HTTP GET para "/products".
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<ProductDTO> list = productService.findAll(); // Chama o serviço para obter todos os produtos.
        return ResponseEntity.ok(list); // Retorna a lista com status HTTP 200 OK.
    }

    /**
     * Endpoint para buscar um produto por ID.
     * Requer que o usuário autenticado tenha o papel 'ROLE_ADMIN' ou 'ROLE_OPERATOR'.
     *
     * @param id O ID do produto a ser buscado.
     * @return ResponseEntity contendo o ProductDTO correspondente e status HTTP 200 (OK).
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')") // Garante que apenas usuários com ROLE_ADMIN ou ROLE_OPERATOR possam acessar este método.
    @GetMapping(value = "/{id}") // Mapeia requisições HTTP GET para "/products/{id}", onde {id} é uma variável de caminho.
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) { // @PathVariable extrai o ID da URL.
        ProductDTO dto = productService.findById(id); // Chama o serviço para buscar o produto pelo ID.
        return ResponseEntity.ok(dto); // Retorna o produto encontrado com status HTTP 200 OK.
    }

    /**
     * Endpoint para inserir um novo produto.
     * Requer que o usuário autenticado tenha o papel 'ROLE_ADMIN'.
     *
     * @param dto O ProductDTO contendo os dados do novo produto.
     * @return ResponseEntity contendo o ProductDTO inserido, status HTTP 201 (Created) e o URI do novo recurso.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Garante que apenas usuários com ROLE_ADMIN possam acessar este método.
    @PostMapping // Mapeia requisições HTTP POST para "/products".
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto) { // @RequestBody mapeia o corpo da requisição para um ProductDTO.
        dto = productService.insert(dto); // Chama o serviço para inserir o novo produto.
        // Constrói a URI do novo recurso criado para incluir no cabeçalho Location da resposta.
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        // Retorna o produto inserido com status HTTP 201 Created e a URI do novo recurso.
        return ResponseEntity.created(uri).body(dto);
    }
}