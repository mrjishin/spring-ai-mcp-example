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

package kr.co.piconet.ai.sample.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import jakarta.annotation.PostConstruct;
import kr.co.piconet.ai.sample.model.Inquiry;

@Service
public class InquiryService
{
	@Value("${api.server.url}")
	private String apiServerUrl;

	private RestClient restClient;
	
	@PostConstruct
	private void init() {
		restClient = RestClient.builder().baseUrl(apiServerUrl).build();
	}
	
	public List<Inquiry> find(String question, Long offset, Integer limit) throws Exception
	{
		return restClient.get()
			.uri(uriBuilder -> uriBuilder.path("/api/inquiries")
					.queryParam("question", question)
					.queryParam("offset", offset)
					.queryParam("limit", limit)
					.build()
			)
			.retrieve()
			.body(new ParameterizedTypeReference<>() {});
	}

	public Long count(String question) throws Exception
	{
		Map<String,Object> resultMap = restClient.get()
			.uri(uriBuilder -> uriBuilder.path("/api/inquiries/count")
					.queryParam("question", question)
					.build()
			)
			.retrieve()
			.body(new ParameterizedTypeReference<Map<String,Object>>() {});

		return ((Integer)(resultMap.get("count"))).longValue();
	}

	public Inquiry getById(Long id) throws Exception {
		return restClient.get()
				.uri(uriBuilder -> uriBuilder.path("/api/inquiries/{id}")
						.build(id)
				)
				.retrieve()
				.body(Inquiry.class);
	}

	public void insert(Inquiry object) throws Exception {
		restClient.post()
				.uri(apiServerUrl + "/api/inquiries")
				.contentType(MediaType.APPLICATION_JSON)
				.body(object)
				.retrieve()
				.toBodilessEntity();			
	}

	public void update(Inquiry object) throws Exception {
		restClient.put()
				.uri(uriBuilder -> uriBuilder.path("/api/inquiries/{id}")
						.build(object.getId())
				)
				.contentType(MediaType.APPLICATION_JSON)
				.body(object)
				.retrieve()
				.toBodilessEntity();			
	}

	public void delete(Long id) throws Exception {
		restClient.delete()
			.uri(uriBuilder -> uriBuilder.path("/api/inquiries/{id}")
					.build(id)
			)
			.retrieve()
			.toBodilessEntity();
	}

	public void delete(List<Long> ids) throws Exception {
		restClient.post()
			.uri("/api/inquiries/dels")
			.contentType(MediaType.APPLICATION_JSON)
			.body(ids)
			.retrieve()
			.toBodilessEntity();

	}
}