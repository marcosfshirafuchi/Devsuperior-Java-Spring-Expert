package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById(Long id) {

        Optional<Movie> obj = movieRepository.findById(id);

        Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

        return new MovieDetailsDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> findByGenre(Long genreId, Pageable pageable) {
        Long genreFilter = genreId == null || genreId == 0 ? null : genreId;

        Page<Movie> page = movieRepository.findByGenre(genreFilter, pageable);

        return page.map(MovieCardDTO::new);
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> findReviewsByMovieId(Long movieId) {

        List<Review> reviews =
                movieRepository.searchReviewsByMovieId(movieId);

        return reviews.stream()
                .map(ReviewDTO::new)
                .toList();
    }

}
