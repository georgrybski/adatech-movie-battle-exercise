package br.ada.americanas.moviebattle.battle;

import br.ada.americanas.moviebattle.exceptions.BattleNotFoundExeption;
import br.ada.americanas.moviebattle.movie.Movie;
import br.ada.americanas.moviebattle.movie.MovieRepository;
import br.ada.americanas.moviebattle.moviesort.MoviePairSort;
import br.ada.americanas.moviebattle.player.Player;
import br.ada.americanas.moviebattle.player.PlayerRepository;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BattleService {

    private PlayerRepository playerRepository;
    private MovieRepository movieRepository;
    private BattleRepository battleRepository;
    private MoviePairSort movieSort;

    public BattleService(
            PlayerRepository playerRepository,
            MovieRepository movieRepository,
            BattleRepository battleRepository,
            MoviePairSort movieSort
    ) {
        this.playerRepository = playerRepository;
        this.movieRepository = movieRepository;
        this.battleRepository = battleRepository;
        this.movieSort = movieSort;
    }

    public Battle create(Player player) {
        Iterable<Movie> movies = movieRepository.findAll();
        Pair<Movie, Movie> sorted = movieSort.sort(movies);

        Battle battle = new Battle();
        battle.setPlayer(player);
        battle.setLeft(sorted.getFirst());
        battle.setRight(sorted.getSecond());
        return battleRepository.save(battle);
    }

    public Battle answer(Battle battle, Movie selected) {
        Float leftRatings = battle.getLeft().getRatings();
        Float rightRatings = battle.getRight().getRatings();
        boolean answerIsRight =
                (leftRatings >= rightRatings && Objects.equals(battle.getLeft().getId(), selected.getId()))
                        || rightRatings >= leftRatings && Objects.equals(battle.getRight().getId(), selected.getId());
        if (answerIsRight) {
            increaseScore(battle.getPlayer());
            battle.setHit(true);
        } else {
            battle.setHit(false);
        }
        battle.setSelected(selected);
        return battleRepository.save(battle);
    }

    public Battle findByIdOrThrow(Long id) {
        Optional<Battle> battle = battleRepository.findById(id);
        if (battle.isPresent()) {
            return battle.get();
        } else {
            throw new BattleNotFoundExeption(id);
        }
    }

    public List<Battle> list() {
        return battleRepository.findAll();
    }

    private void increaseScore(Player player) {
        player.setScore(player.getScore() + 1);
        playerRepository.save(player);
    }

    public Battle delete(Long id) {
        Optional<Battle> battle = battleRepository.findById(id);
        battle.ifPresent(value -> battleRepository.delete(value));
        return battle.orElse(null);
    }
}
