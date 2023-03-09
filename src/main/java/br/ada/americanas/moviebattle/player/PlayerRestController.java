package br.ada.americanas.moviebattle.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerRestController {

    private PlayerService playerService;

    @Autowired
    public PlayerRestController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Player> create(@RequestBody Player player) {
        playerService.add(player);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Player> update(
            @PathVariable("id") Long id,
            @RequestBody Player player
    ) {
        playerService.findByIdOrThrow(id);
        player.setId(id);
        playerService.update(player);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Player>> list() {
        return ResponseEntity.ok(playerService.findAllByOrderByScoreDesc());
    }

    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Player> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(playerService.findByIdOrThrow(id));
    }

    @DeleteMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Player> delete(@PathVariable("id") Long id) {
        playerService.findByIdOrThrow(id);
        playerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
