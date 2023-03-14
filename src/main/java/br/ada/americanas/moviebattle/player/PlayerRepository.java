package br.ada.americanas.moviebattle.player;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findAllByOrderByScoreDesc();

    List<Player> findAllByOrderByNameAsc();
}
