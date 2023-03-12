package br.ada.americanas.moviebattle.player;

import br.ada.americanas.moviebattle.exceptions.PlayerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private PlayerRepository repository;

    @Autowired
    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }


    public Player add(Player player) {
        return repository.save(player);
    }

    public Player update(Player player) {
        return repository.save(player);
    }

    public List<Player> findAllByOrderByScoreDesc() {
        return repository.findAllByOrderByScoreDesc();
    }

    public List<Player> findAllByOrderByNameAsc() {
        return repository.findAllByOrderByNameAsc();
    }

    public Player findByIdOrThrow(Long id) {
        Optional<Player> player = repository.findById(id);
        if (player.isPresent()) {
            return player.get();
        } else {
            throw new PlayerNotFoundException(id);
        }
    }

    public Player delete(Long id) {
        Player deleted = findByIdOrThrow(id);
        this.repository.deleteById(id);
        return deleted;
    }
}
