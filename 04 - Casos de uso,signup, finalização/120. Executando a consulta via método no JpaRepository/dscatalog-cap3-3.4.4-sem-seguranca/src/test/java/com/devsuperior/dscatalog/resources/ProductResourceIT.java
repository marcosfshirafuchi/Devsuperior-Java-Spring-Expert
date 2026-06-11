package com.devsuperior.dscatalog.resources;

// Import estático para realizar requisições HTTP GET
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

// Import estático para realizar requisições HTTP PUT
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

// Import estático para validar campos do JSON retornado
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// Import estático para validar códigos de status HTTP
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devsuperior.dscatalog.tests.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @SpringBootTest
 * Carrega todo o contexto da aplicação para testes de integração.
 *
 * @AutoConfigureMockMvc
 * Configura o MockMvc para simular requisições HTTP sem subir o servidor.
 *
 * @Transactional
 * Garante que cada teste execute em uma transação separada
 * e faça rollback ao final, mantendo o banco consistente.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIT {

	/**
	 * MockMvc utilizado para simular requisições HTTP.
	 */
	@Autowired
	private MockMvc mockMvc;

	/**
	 * Responsável por converter objetos Java em JSON
	 * e JSON em objetos Java.
	 */
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Classe utilitária responsável por gerar
	 * tokens JWT para autenticação nos testes.
	 */
	@Autowired
	private TokenUtil tokenUtil;

	// ID de um produto que existe no banco
	private Long existingId;

	// ID inexistente para testes de erro
	private Long nonExistingId;

	// Quantidade total de produtos cadastrados
	private Long countTotalProducts;

	// Credenciais do usuário utilizado nos testes
	private String username;
	private String password;

	// Token JWT obtido antes de executar os testes
	private String bearerToken;

	/**
	 * Executado antes de cada método de teste.
	 *
	 * Inicializa os dados necessários para os cenários
	 * e gera um token JWT válido.
	 */
	@BeforeEach
	void setUp() throws Exception {

		// Produto existente no banco H2
		existingId = 1L;

		// Produto inexistente
		nonExistingId = 1000L;

		// Total de produtos carregados pelo import.sql
		countTotalProducts = 25L;

		// Usuário cadastrado para autenticação
		username = "maria@gmail.com";
		password = "123456";

		// Obtém um token JWT válido
		bearerToken = tokenUtil.obtainAccessToken(mockMvc, username, password);
	}

	/**
	 * Testa o endpoint:
	 *
	 * GET /products?page=0&size=12&sort=name,asc
	 *
	 * Verifica se:
	 * - Retorna status 200
	 * - Retorna uma página válida
	 * - Os produtos estão ordenados por nome
	 */
	@Test
	public void findAllShouldReturnSortedPageWhenSortByName() throws Exception {

		ResultActions result =
				mockMvc.perform(
						get("/products?page=0&size=12&sort=name,asc")
								.accept(MediaType.APPLICATION_JSON)
				);

		// Verifica status HTTP 200
		result.andExpect(status().isOk());

		// Verifica quantidade total de registros
		result.andExpect(jsonPath("$.totalElements")
				.value(countTotalProducts));

		// Verifica se existe conteúdo na página
		result.andExpect(jsonPath("$.content").exists());

		// Verifica ordenação dos registros
		result.andExpect(jsonPath("$.content[0].name")
				.value("Macbook Pro"));

		result.andExpect(jsonPath("$.content[1].name")
				.value("PC Gamer"));

		result.andExpect(jsonPath("$.content[2].name")
				.value("PC Gamer Alfa"));
	}

	/**
	 * Testa a atualização de um produto existente.
	 *
	 * Verifica se:
	 * - Retorna status 200
	 * - Retorna o ProductDTO atualizado
	 * - Os dados retornados são iguais aos enviados
	 */
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() throws Exception {

		// Cria um ProductDTO fictício para o teste
		ProductDTO productDTO = Factory.createProductDTO();

		// Converte o DTO para JSON
		String jsonBody =
				objectMapper.writeValueAsString(productDTO);

		// Valores esperados após atualização
		String expectedName = productDTO.getName();
		String expectedDescription = productDTO.getDescription();

		ResultActions result =
				mockMvc.perform(
						put("/products/{id}", existingId)

								// Adiciona o token JWT no cabeçalho
								.header("Authorization", "Bearer " + bearerToken)

								// Corpo da requisição
								.content(jsonBody)

								// Tipo do conteúdo enviado
								.contentType(MediaType.APPLICATION_JSON)

								// Tipo de resposta esperado
								.accept(MediaType.APPLICATION_JSON)
				);

		// Verifica status HTTP 200
		result.andExpect(status().isOk());

		// Verifica o ID retornado
		result.andExpect(jsonPath("$.id")
				.value(existingId));

		// Verifica nome atualizado
		result.andExpect(jsonPath("$.name")
				.value(expectedName));

		// Verifica descrição atualizada
		result.andExpect(jsonPath("$.description")
				.value(expectedDescription));
	}

	/**
	 * Testa a atualização de um produto inexistente.
	 *
	 * Verifica se:
	 * - Retorna status 404 (Not Found)
	 */
	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {

		// Cria um DTO para envio na requisição
		ProductDTO productDTO = Factory.createProductDTO();

		// Converte para JSON
		String jsonBody =
				objectMapper.writeValueAsString(productDTO);

		ResultActions result =
				mockMvc.perform(
						put("/products/{id}", nonExistingId)

								// Adiciona o token JWT
								.header("Authorization", "Bearer " + bearerToken)

								// Corpo da requisição
								.content(jsonBody)

								// Content-Type da requisição
								.contentType(MediaType.APPLICATION_JSON)

								// Tipo de resposta esperado
								.accept(MediaType.APPLICATION_JSON)
				);

		// Verifica retorno HTTP 404
		result.andExpect(status().isNotFound());
	}
}