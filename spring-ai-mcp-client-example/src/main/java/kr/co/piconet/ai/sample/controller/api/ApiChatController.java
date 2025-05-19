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

package kr.co.piconet.ai.sample.controller.api;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.piconet.ai.sample.controller.api.dto.ApiRequests;
import kr.co.piconet.ai.sample.service.ConfigService;

@RequestMapping("/api/chat")
@RestController
public class ApiChatController
{
	private final ChatClient ollamaChatClient;
	private final ChatClient openAiChatClient;
	private final ConfigService configService;

	public ApiChatController(
			@Qualifier("ollamaChatClient") ChatClient ollamaChatClient,
			@Qualifier("openAiChatClient") ChatClient openAiChatClient,
			ConfigService configService)
	{
		this.ollamaChatClient = ollamaChatClient;
		this.openAiChatClient = openAiChatClient;
		this.configService = configService;
	}
	
	@PostMapping("/ollama")
	public ResponseEntity<?> ollama(
			@RequestBody ApiRequests.ChatRequest request)
	{
		configService.saveModel(request.getModel());

		Prompt prompt = new Prompt(
				request.getContent(),
		        OllamaOptions.builder()
		        	.model(request.getModel())
		        	.build()
		        );

		String responseText = ollamaChatClient
				.prompt(prompt)
				.call()
				.content();

		return ResponseEntity.ok(responseText);
	}

	@PostMapping("/openai")
	public ResponseEntity<?> openai(
			@RequestBody ApiRequests.ChatRequest request)
	{
		Prompt prompt = new Prompt(request.getContent());

		String responseText = openAiChatClient
				.prompt(prompt)
				.call()
				.content();

		return ResponseEntity.ok(responseText);
	}
}