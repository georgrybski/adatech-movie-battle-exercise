package br.ada.americanas.moviebattle.movie;

import br.ada.americanas.moviebattle.exceptions.MovieNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class MovieService {

    private MovieRepository repository;

    @Autowired
    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public Movie add(@Valid Movie movie) {
        return this.repository.save(movie);
    }

    public Movie update(@Valid Movie movie) {
        return this.repository.save(movie);
    }

    public List<Movie> list() {
        return this.repository.findAll();
    }

    public Movie findByIdOrThrow(Long id) {
        Optional<Movie> movie = repository.findById(id);
        if (movie.isPresent()) {
            return movie.get();
        } else {
            throw new MovieNotFoundException(id);
        }
    }

    public void delete(Long id) {
        findByIdOrThrow(id);
        repository.deleteById(id);
    }

}
