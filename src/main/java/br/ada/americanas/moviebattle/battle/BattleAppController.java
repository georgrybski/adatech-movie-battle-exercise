package br.ada.americanas.moviebattle.battle;

import br.ada.americanas.moviebattle.movie.Movie;
import br.ada.americanas.moviebattle.player.Player;
import br.ada.americanas.moviebattle.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/app/battles")
public class BattleAppController {

    private BattleService battleService;
    private PlayerService playerService;

    @Autowired
    public BattleAppController(
            BattleService battleService,
            PlayerService playerService
    ) {
        this.battleService = battleService;
        this.playerService = playerService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<Player> players = playerService.findAllByOrderByNameAsc();
        model.addAttribute("players", players);
        model.addAttribute("player", new Player());
        return "battle/form";
    }

    @PostMapping
    public String save(
            @ModelAttribute Player player,
            Model model
    ) {
        player = playerService.findByIdOrThrow(player.getId());
        Battle battle = battleService.create(player);
        model.addAttribute("battle", battle);
        model.addAttribute("selected_movie", new Movie());
        return "battle/play";
    }

    @PostMapping("/{id}/answer")
    public String answer(
            @PathVariable("id") Long id,
            @ModelAttribute Movie selected,
            Model model
    ) {
        Battle battle = battleService.findByIdOrThrow(id);
        battleService.answer(battle, selected);
        model.addAttribute("player", battle.getPlayer());
        boolean hit = battle.getHit();
        return hit ? "battle/congrats" : "battle/wasted";
    }
}
