package br.ada.americanas.moviebattle.battle;

import br.ada.americanas.moviebattle.movie.Movie;
import br.ada.americanas.moviebattle.player.Player;
import br.ada.americanas.moviebattle.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/battles")
public class BattleRestController {
    private BattleService battleService;
    private PlayerService playerService;

    @Autowired
    public BattleRestController(BattleService battleService, PlayerService playerService) {
        this.battleService = battleService;
        this.playerService = playerService;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Battle> create(@RequestBody Player player) {
        playerService.findByIdOrThrow(player.getId());
        Battle battle = battleService.create(player);
        return ResponseEntity.ok(battle);
    }

    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Battle> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(battleService.findByIdOrThrow(id));
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Battle> answer(
            @PathVariable("id") Long id,
            @RequestBody Movie movie
    ) {
        Battle battle = battleService.findByIdOrThrow(id);
        battleService.answer(battle, movie);
        return ResponseEntity.ok(battle);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Battle>> list() {
        return ResponseEntity.ok(this.battleService.list());
    }

    @DeleteMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Battle> delete(@PathVariable("id") Long id) {
        battleService.findByIdOrThrow(id);
        battleService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}