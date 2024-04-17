package com.ai.generative.controller;

import com.ai.generative.dto.MessageDTO;
import com.ai.generative.factory.AiAssistantFactory;
import com.ai.generative.factory.ContentRetrieverFactory;
import com.ai.generative.factory.DocumentAssistantFactory;
import com.ai.generative.factory.EmbeddingFactory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/chat")
public class AiAssistantController {

    @Value("${langchain.huggingface.access-token}")
    private String accessToken;

    @PostMapping
    public ResponseEntity<String> chat(@RequestBody MessageDTO messageDTO){
        ChatLanguageModel chatModel = AiAssistantFactory.createLocalChatModel();
        var embeddingModel = EmbeddingFactory.createEmbeddingModel();
        var embeddingStore = EmbeddingFactory.createEmbeddingStore();
        var fileContentRetriever = ContentRetrieverFactory.createFileContentRetriever(
                embeddingModel,
                embeddingStore,
                "movies.txt");

        var documentAssistant = new DocumentAssistantFactory(chatModel, fileContentRetriever);
        String response = documentAssistant.chat(messageDTO.message());
        return ResponseEntity.ok().body(response);
    }

}
