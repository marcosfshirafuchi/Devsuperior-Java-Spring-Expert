package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {


    @Query("""
        SELECT obj
        FROM Movie obj
        WHERE (:genreId IS NULL OR obj.genre.id = :genreId)
        ORDER BY obj.title
        """)
    Page<Movie> findByGenre(@Param("genreId") Long genreId,
                            Pageable pageable);

    @Query("""
    SELECT r
    FROM Review r
    WHERE r.movie.id = :movieId
    """)
    List<Review> searchReviewsByMovieId(
            @Param("movieId") Long movieId);
}
