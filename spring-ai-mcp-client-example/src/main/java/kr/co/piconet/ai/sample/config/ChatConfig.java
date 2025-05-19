/**
 * Copyright 2025 Jaeik Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kr.co.piconet.ai.sample.config;

import java.util.Set;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class ChatConfig
{
	private static final Set<String> defaultSystemTexts = Set.of(
			"""
				You are an inquiry information management assistant. You can help users search for inquiry information.
				You can search inquiry history based on the question in the inquiry history.
				You can register a new inquiry history or edit an existing inquiry history.
				Your answers are concise and friendly, and your inquiry history is organized in an easy-to-understand format.
			""",
			"You are the weather helper."
	);

	private final ToolCallbackProvider toolCallbackProvider;

	private ChatMemoryRepository chatMemoryRepository = new InMemoryChatMemoryRepository();
	private ChatMemory chatMemory = MessageWindowChatMemory.builder()
		    .chatMemoryRepository(chatMemoryRepository)
		    .maxMessages(1000)
		    .build();

	@Bean
	@Qualifier("ollamaChatClient")
	ChatClient ollamaChatClient(
			OllamaChatModel ollamaChatModel)
	{
		ChatClient.Builder builder = ChatClient.builder(ollamaChatModel);

		for(String defaultSystemText: defaultSystemTexts)
			builder.defaultSystem(defaultSystemText);

		ChatClient chatClient = builder
				.defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
				.defaultToolCallbacks(toolCallbackProvider)
				.build();

		return chatClient;
	}

	@Bean
	@Qualifier("openAiChatClient")
	ChatClient openAiChatClient(
			OpenAiChatModel openAiChatModel)
	{
		ChatClient.Builder builder = ChatClient.builder(openAiChatModel);

		for(String defaultSystemText: defaultSystemTexts)
			builder.defaultSystem(defaultSystemText);

		ChatClient chatClient = builder
				.defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
				.defaultToolCallbacks(toolCallbackProvider)
				.build();

		return chatClient;
	}
}