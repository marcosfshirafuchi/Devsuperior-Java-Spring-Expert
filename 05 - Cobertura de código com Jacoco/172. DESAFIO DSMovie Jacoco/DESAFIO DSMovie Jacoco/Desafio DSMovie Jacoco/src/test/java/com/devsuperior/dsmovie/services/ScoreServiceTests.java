package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.dto.ScoreDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.entities.ScoreEntity;
import com.devsuperior.dsmovie.entities.UserEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dsmovie.tests.MovieFactory;
import com.devsuperior.dsmovie.tests.ScoreFactory;
import com.devsuperior.dsmovie.tests.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class ScoreServiceTests {
	
	@InjectMocks
	private ScoreService service;

	@Mock
	private UserService userService;

	@Mock
	private MovieRepository movieRepository;

	@Mock
	private ScoreRepository scoreRepository;

	private ScoreDTO scoreDTO;
	private UserEntity userEntity;
	private MovieEntity movieEntity;
	private ScoreEntity scoreEntity;
	private long existingMovieId;
	private String existingTitle;
	private long nonExistingMovieId;

	@BeforeEach
	void setUp(){
		scoreDTO = ScoreFactory.createScoreDTO();
		userEntity = UserFactory.createUserEntity();
		movieEntity = MovieFactory.createMovieEntity();
		scoreEntity = ScoreFactory.createScoreEntity();
		existingMovieId = 1L;
		existingTitle = "Test Movie";
		nonExistingMovieId = 2L;

		Mockito.lenient().when(movieRepository.findById(existingMovieId)).thenReturn(Optional.ofNullable(movieEntity));
		Mockito.lenient().when(scoreRepository.saveAndFlush(any())).thenReturn(scoreEntity);
		Mockito.lenient().when(movieRepository.save(any())).thenReturn(movieEntity);
		Mockito.lenient().when(movieRepository.findById(nonExistingMovieId)).thenReturn(Optional.empty());
	}

	@Test
	public void saveScoreShouldReturnMovieDTO() {
		Mockito.lenient().when(userService.authenticated()).thenReturn(userEntity);
		ScoreEntity score = new ScoreEntity();
		score.setMovie(movieEntity);
		score.setUser(userEntity);
		score.setValue(scoreDTO.getScore());
		movieEntity.getScores().add(score);
		MovieDTO result = service.saveScore(scoreDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(movieEntity.getId(), result.getId());
		Assertions.assertEquals(movieEntity.getScore(),result.getScore());
		Assertions.assertEquals(1, result.getCount());
	}
	
	@Test
	public void saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId() {
		Mockito.lenient().when(userService.authenticated()).thenReturn(userEntity);
		ScoreDTO dto = new ScoreDTO(nonExistingMovieId, 4.5);

		Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			service.saveScore(dto);
		});
	}
}
