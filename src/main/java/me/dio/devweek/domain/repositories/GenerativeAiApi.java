package me.dio.devweek.domain.repositories;

public interface GenerativeAiApi {
    String generateContent(String objective, String context);
}
