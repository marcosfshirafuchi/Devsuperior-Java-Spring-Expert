package com.devsuperior.dscatalog.tests;

// Import estático para adicionar autenticação HTTP Basic na requisição
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

// Import estático para validar o conteúdo da resposta
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

// Import estático para realizar requisições HTTP POST
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

// Import estático para validar o status HTTP retornado
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Classe utilitária responsável por obter um Access Token JWT
 * durante a execução dos testes automatizados.
 *
 * Essa classe é muito utilizada em testes de integração
 * quando endpoints protegidos por autenticação precisam ser testados.
 */
@Component
public class TokenUtil {

    /**
     * Injeta o Client ID configurado no arquivo application.properties.
     *
     * Exemplo:
     * security.client-id=myclientid
     */
    @Value("${security.client-id}")
    private String clientId;

    /**
     * Injeta o Client Secret configurado no arquivo application.properties.
     *
     * Exemplo:
     * security.client-secret=myclientsecret
     */
    @Value("${security.client-secret}")
    private String clientSecret;

    /**
     * Obtém um Access Token válido para um usuário específico.
     *
     * @param mockMvc Objeto responsável por simular requisições HTTP.
     * @param username Nome do usuário que fará a autenticação.
     * @param password Senha do usuário.
     * @return Access Token JWT gerado pelo Authorization Server.
     * @throws Exception Caso ocorra algum erro durante a requisição.
     */
    public String obtainAccessToken(MockMvc mockMvc, String username, String password) throws Exception {

        // Cria o mapa de parâmetros que serão enviados para o endpoint de autenticação
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        // Define o tipo de autenticação utilizado
        params.add("grant_type", "password");

        // Informa o Client ID registrado no Authorization Server
        params.add("client_id", clientId);

        // Usuário que será autenticado
        params.add("username", username);

        // Senha do usuário
        params.add("password", password);

        // Executa uma requisição POST para o endpoint de geração de token
        ResultActions result = mockMvc
                .perform(post("/oauth2/token")

                        // Adiciona os parâmetros da requisição
                        .params(params)

                        // Adiciona autenticação HTTP Basic usando Client ID e Client Secret
                        .with(httpBasic(clientId, clientSecret))

                        // Define que a resposta esperada será JSON
                        .accept("application/json;charset=UTF-8"))

                // Verifica se o retorno foi HTTP 200 (OK)
                .andExpect(status().isOk())

                // Verifica se o Content-Type retornado é JSON
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        // Obtém o corpo da resposta como String
        String resultString = result
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Cria um parser para converter o JSON retornado em um Map
        JacksonJsonParser jsonParser = new JacksonJsonParser();

        // Extrai e retorna apenas o valor do campo access_token
        return jsonParser
                .parseMap(resultString)
                .get("access_token")
                .toString();
    }
}