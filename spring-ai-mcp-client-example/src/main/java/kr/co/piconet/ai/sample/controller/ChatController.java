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

package kr.co.piconet.ai.sample.controller;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClient;

import kr.co.piconet.ai.sample.service.ConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
@Controller
public class ChatController
{
	private final ConfigService configService;

	@GetMapping("/ollama")
	public String ollama(ModelMap modelMap) {

		Set<String> models = getOllamaModels();
		modelMap.put("models", models);

		String model = configService.getModel();
		if(model != null) {
			modelMap.put("model", model);
		}
		return "chat/ollama";
	}

	@GetMapping("/openai")
	public String openai() {
		return "chat/openai";
	}

	@SuppressWarnings("unchecked")
	private Set<String> getOllamaModels() {
		RestClient restClient = RestClient.builder().baseUrl("http://localhost:11434").build();
		Map<String,Object> responseMap = restClient.get()
			.uri(uriBuilder -> uriBuilder.path("/api/tags").build())
			.retrieve()
			.body(new ParameterizedTypeReference<Map<String,Object>>() {});
		
		Set<String> models = new LinkedHashSet<>();
		
		List<Map<String,Object>> modelList = (List<Map<String, Object>>) responseMap.get("models");
		for(Map<String,Object> modelInfo: modelList) {
			String model = (String)modelInfo.get("model");
			models.add(model.split(":")[0]);
		}

		log.info("---> ollama.models: {}", responseMap);
		
		return models;
	}
}