package me.dio.devweek.adapters.in;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.devweek.application.AskChampionUseCase;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Campeões", description = "Endpoints do domínio de Campeões do LOL")
@RestController
@RequestMapping("/champions")
public record AskChampionRestController(AskChampionUseCase useCase) {
    @CrossOrigin
    @PostMapping("/{championId}/ask")
    public askChampionResponse askChampion(@PathVariable Long championId,
                                           @RequestBody askChampionRequest request){

        String answer = useCase.askChampion(championId, request.question());

        return new askChampionResponse(answer);
    }

    public record askChampionRequest(String question) { }
    public record askChampionResponse(String answer){ }
}
