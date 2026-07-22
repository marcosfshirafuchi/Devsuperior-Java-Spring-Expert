package com.devsuperior.dscommerce.controllers.it;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private String adminToken;

    private String productName;

    @BeforeEach
    void setUp(){
        productName = "Macbook Pro";
    }

    /*Exercícios de fixação: Testes de API com MockMvc*/

    /*Problema 1: Consultar produto por nome

    Implemente o teste de API usando MockMvc para consultar produtos por nome. Desta forma, ao fazer a requisição do tipo GET no endpoint /products?name={productName} onde productName deve ser correspondente a string “Macbook” deve retornar como response o status 200 (Ok) e verificar se os campos id, name, price e imgUrl retornados no jsonPath correspondem aos valores da Figura 1 (abaixo).

    * */

    @Test
    public void findAllShouldReturnPageWhenNameParamIsNotEmpty() throws Exception {
        ResultActions result = mockMvc
                //No perform vai testar o metódo get do controller e sua url
                .perform(get("/products?name={productName}", productName)
                //O Tipo da response do método
                .accept(MediaType.APPLICATION_JSON));

        //Métodos abaixo para fazer o teste de integração
        //Testes para ver se o resultado do Postman são iguais ao banco de dados
        //Resultado do status code
        result.andExpect(status().isOk());
        //Resultado do id
        result.andExpect(jsonPath("$.content[0].id").value(3L));
        //Resultado do name
        result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
        //Resultado do price
        result.andExpect(jsonPath("$.content[0].price").value(1250.0));
        //Resultado da url da imagem
        result.andExpect(jsonPath("$.content[0].imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg"));
    }

    @Test
    public void findAllShouldReturnPageWhenNameParamIsEmpty() throws Exception {
        ResultActions result =mockMvc
                //No perform vai testar o metódo get do controller e sua url
                .perform(get("/products")
                        //O Tipo da response do método
                        .accept(MediaType.APPLICATION_JSON));

        //Métodos abaixo para fazer o teste de integração
        //Testes para ver se o resultado do Postman são iguais ao banco de dados
        //Resultado do status code
        result.andExpect(status().isOk());
        //Resultado do id
        result.andExpect(jsonPath("$.content[0].id").value(1L));
        //Resultado do name
        result.andExpect(jsonPath("$.content[0].name").value("The Lord of the Rings"));
        //Resultado do price
        result.andExpect(jsonPath("$.content[0].price").value(90.5));
        //Resultado da url da imagem
        result.andExpect(jsonPath("$.content[0].imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg"));
    }


    @Test
    public void insertShouldReturnProductDTOCreatedWhenAdminLogged() throws Exception {
        String jsonBody = "";
        ResultActions result =mockMvc
                //No perform vai testar o metódo get do controller e sua url
                .perform(post("/products")
                //Vai passar o Bearer Token
                .header("Authorization", "Bearer " + adminToken)
                //Vai passar o corpo da requisição
                .content(jsonBody)
                //O Tipo da response do método
                .accept(MediaType.APPLICATION_JSON));
    }
}
