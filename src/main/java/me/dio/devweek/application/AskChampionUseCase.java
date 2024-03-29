package me.dio.devweek.application;

import me.dio.devweek.domain.exception.ChampionNotFoundException;
import me.dio.devweek.domain.model.Champion;
import me.dio.devweek.domain.repositories.ChampionsRepository;
import me.dio.devweek.domain.repositories.GenerativeAiApi;

public record AskChampionUseCase(ChampionsRepository repository, GenerativeAiApi generativeAiApi) {
    public String askChampion(Long championId, String question){
        Champion champion =repository.findById(championId)
                .orElseThrow(() -> new ChampionNotFoundException(championId));
        String context = champion.generateContextByQuestion(question);
        String objective = """
                Atue como um assitente com a habilidade de se comportar como os Campeões do League of Legends (LOL).
                Responda perguntas incorporando a personalidade e estílo de um determinado Campeão.
                Segue a pergunta, o nome do campeão e sua respectiva lore (história):
                
                """;
        return generativeAiApi.generateContent(objective, context);
    }
}
