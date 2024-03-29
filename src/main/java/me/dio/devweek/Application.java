package me.dio.devweek;

import me.dio.devweek.application.AskChampionUseCase;
import me.dio.devweek.application.ListChampionsUseCase;
import me.dio.devweek.domain.repositories.ChampionsRepository;
import me.dio.devweek.domain.repositories.GenerativeAiApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ListChampionsUseCase provideListChampionsUseCase(ChampionsRepository repository) {
		return new ListChampionsUseCase(repository);
	}
	@Bean
	public AskChampionUseCase provideAskChampionUseCase(ChampionsRepository repository,
														GenerativeAiApi genAiService) {
		return new AskChampionUseCase(repository, genAiService);
	}
}