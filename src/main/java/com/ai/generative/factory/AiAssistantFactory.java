package com.ai.generative.factory;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.huggingface.HuggingFaceChatModel;

import java.time.Duration;

public class AiAssistantFactory {

    public static ChatLanguageModel createHuggingFace(String accessToken) {
        return HuggingFaceChatModel.builder()
                .accessToken(accessToken)
                .modelId("facebook/opt-125m")
                .timeout(Duration.ofSeconds(300))
                .build();
    }
}
