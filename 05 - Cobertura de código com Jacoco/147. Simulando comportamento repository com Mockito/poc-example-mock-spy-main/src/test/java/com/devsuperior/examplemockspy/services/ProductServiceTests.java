package com.devsuperior.examplemockspy.services;

import com.devsuperior.examplemockspy.dto.ProductDTO;
import com.devsuperior.examplemockspy.entities.Product;
import com.devsuperior.examplemockspy.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private Long existingId, nonExistingId;

    private Product product;

    private ProductDTO productDTO;

    @BeforeEach
    void setUp() throws Exception{

        existingId = 1L;
        nonExistingId = 2L;


        product = new Product(1L,"Playstation",2000.0);
        productDTO = new ProductDTO(product);

        //Cenários para testes da classe ProductService
        Mockito.when(repository.save(Mockito.any())).thenReturn(product);

        Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);

        Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(new EntityNotFoundException());
    }
}
