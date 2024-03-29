package me.dio.devweek.adapters.out;

import feign.FeignException;
import feign.RequestInterceptor;
import me.dio.devweek.domain.repositories.GenerativeAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "OPENAI")
@FeignClient(name = "OpenAiChatGptApi", url = "${openai.base-url}", configuration = OpenAiChatGptApiService.Config.class)
public interface OpenAiChatGptApiService extends GenerativeAiApi {
    @PostMapping("/v1/chat/completions")
    OpenAIChatCompletionResponse chatCompletion(OpenAIChatCompletionRequest request);
    @Override
    default String generateContent(String objective, String context) {
        String model = "gpt-3.5-turbo";
        List<Message> messages = List.of(
                new Message ("sytem", objective),
                new Message("user", context)
        );
        OpenAIChatCompletionRequest request = new OpenAIChatCompletionRequest(model, messages);
        try {
            OpenAIChatCompletionResponse resp = chatCompletion(request);
            return resp.choices().getFirst().message().content();
        }catch (FeignException httpError){
            return "Foi mal! Erro de comunicação com  a API da OpenAI.";
        }catch (Exception unexpectedError){
            return "Deu ruim! O retorno da API do OpenAI não contem os dados esperados.";
        }
    }
    record OpenAIChatCompletionRequest(String model, List<Message> message){ }
    record Message(String role, String content){ }
    record OpenAIChatCompletionResponse(List<Choice> choices){ }
    record Choice(Message message){ }
    class Config {
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${openai.api-key}") String apiKey){
            return requestTemplate -> requestTemplate.header(
                    HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiKey));
        }
    }
}
