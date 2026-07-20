package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.services.exceptions.DatabaseException;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dsmovie.tests.MovieFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class MovieServiceTests {
	
	@InjectMocks
	private MovieService service;

	@Mock
	private MovieRepository repository;

	private String existingTitle;
	private MovieEntity movieEntity;
	private PageImpl<MovieEntity> page;
	private long existingMovieId;
	private long nonExistingMovieId;
	private MovieDTO movieDTO;
	private long dependentMovietId;

	@BeforeEach
	void setUp() throws Exception {
		existingTitle = "Test Movie";
		movieEntity = MovieFactory.createMovieEntity();
		page = new PageImpl<>(List.of((movieEntity)));
		existingMovieId = 1L;
		nonExistingMovieId = 2L;
		movieDTO = MovieFactory.createMovieDTO();
		dependentMovietId = 3L;

		Mockito.lenient().when(repository.searchByTitle(any(), (Pageable) any())).thenReturn(page);
		Mockito.lenient().when(repository.findById(existingMovieId)).thenReturn(Optional.of(movieEntity));
		Mockito.lenient().when(repository.findById(nonExistingMovieId)).thenReturn(Optional.empty());
		Mockito.lenient().when(repository.save(any())).thenReturn(movieEntity);
		Mockito.lenient().when(repository.getReferenceById(existingMovieId)).thenReturn(movieEntity);
		Mockito.lenient().when(repository.getReferenceById(nonExistingMovieId)).thenThrow(new EntityNotFoundException());
		Mockito.lenient().when(repository.existsById(existingMovieId)).thenReturn(true);
		Mockito.lenient().when(repository.existsById(nonExistingMovieId)).thenReturn(false);
		Mockito.lenient().when(repository.existsById(dependentMovietId)).thenReturn(true);
		Mockito.lenient().doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentMovietId);
	}
	
	@Test
	public void findAllShouldReturnPagedMovieDTO() {
		Pageable pageable = PageRequest.of(0,12);
		String movie = "Test Movie";
		Page<MovieDTO> result = service.findAll(movie,pageable);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getTotalElements(),1);
		Assertions.assertEquals(result.iterator().next().getTitle(),existingTitle);
	}
	
	@Test
	public void findByIdShouldReturnMovieDTOWhenIdExists() {
		MovieDTO result = service.findById(existingMovieId);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(),existingMovieId);
		Assertions.assertEquals(result.getTitle(),movieEntity.getTitle());
	}

	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			service.findById(nonExistingMovieId);
		});
	}

	@Test
	public void insertShouldReturnMovieDTO() {
		MovieDTO result = service.insert(new MovieDTO(movieEntity));

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), movieEntity.getId());
	}

	@Test
	public void updateShouldReturnMovieDTOWhenIdExists() {
		MovieDTO result = service.update(existingMovieId,movieDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), movieDTO.getId());
		Assertions.assertEquals(result.getTitle(), movieDTO.getTitle());
	}

	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			service.update(nonExistingMovieId, movieDTO);
		});
	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(()->{
			service.delete(existingMovieId);
		});
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			service.delete(nonExistingMovieId);
		});
	}

	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
		Assertions.assertThrows(DatabaseException.class, ()->{
			service.delete(dependentMovietId);
		});
	}
}
