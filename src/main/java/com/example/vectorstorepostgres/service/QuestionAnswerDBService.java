package com.example.vectorstorepostgres.service;

import com.example.vectorstorepostgres.model.Answer;
import com.example.vectorstorepostgres.model.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionAnswerDBService {
    private final ChatModel chatModel;
    private final VectorStore vectorStore;
    @Value("classpath:/templates/rag-prompt-template.st")
    private Resource ragPromptTemplate;
    @Value("classpath:/templates/system-message.st")
    private Resource systemMessageTemplate;

    public Answer getAnswerForQuestion(Question question) {

        PromptTemplate systemMessagePromptTemplate = new SystemPromptTemplate(systemMessageTemplate);
        Message systemMessage = systemMessagePromptTemplate.createMessage();

        List<Document> documents = vectorStore
                .similaritySearch(SearchRequest.builder().query(question.question()).topK(5).build());

        List<String> contentList = documents.stream().map(Document::getText).toList();

        log.info("answering question {}", question.question());
        String contents = String.join("\n", contentList)
                .replaceAll("\\n", "")
                .replaceAll("\\r", "")
                .replaceAll("  ", "");
        log.info("documents: {}", contents);

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Message userMessage = promptTemplate.createMessage(Map.of(
                "input", question.question(),
                "documents", contents
        ));
        log.info("userMessage: {}", userMessage.getText());


        log.info("===================================================");
        log.info("===================================================");
        log.info("===================================================");
        ChatResponse response = chatModel.call(new Prompt(List.of(systemMessage, userMessage)));


        String answer = response.getResult().getOutput().getText();
        log.info("answer: {}", answer);
        return new Answer(answer);
    }

}
