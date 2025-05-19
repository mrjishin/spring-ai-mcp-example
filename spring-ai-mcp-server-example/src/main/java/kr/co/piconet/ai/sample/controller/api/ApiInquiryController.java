/**
 * Copyright 2025 PICONET
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

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.piconet.ai.sample.entity.Inquiry;
import kr.co.piconet.ai.sample.service.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/inquiries")
@RestController
public class ApiInquiryController
{
	private final InquiryService inquiryService;

	@GetMapping(value = {"", "/"})
	public ResponseEntity<?> list(
			@RequestParam(name = "id", required = false) Long id,
			@RequestParam(name = "question", required = false) String question,
			@RequestParam(name = "offset", required=false, defaultValue="1") Long offset,
			@RequestParam(name = "limit", required=false, defaultValue="15") Integer limit) {
		try {
			List<Inquiry> list = inquiryService.find(id, question, offset, limit);

			return ResponseEntity.ok(list);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@GetMapping(value = {"count", "/count"})
	public ResponseEntity<?> count(
			@RequestParam(name = "id", required = false) Long id,
			@RequestParam(name = "question", required = false) String question) {
		try {
			long count = inquiryService.count(id, question);
			return ResponseEntity.ok(Map.of("count", count));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(
			@PathVariable(name = "id", required = true) Long id) {
		try {
			Inquiry inquiry = inquiryService.getById(id);
			return ResponseEntity.ok(inquiry);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@PostMapping(value = {"", "/"})
	public ResponseEntity<?> add(
			@RequestBody Inquiry consult)
	{
		try {
			inquiryService.save(consult);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("consult.find.error.", e);
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(
			@RequestBody Inquiry inquiry)
	{
		try {
				Inquiry fetched = inquiryService.getById(inquiry.getId());
				if(StringUtils.hasText(inquiry.getQuestion())) {
					fetched.setQuestion(inquiry.getQuestion());
				}
				if(StringUtils.hasText(inquiry.getAnswer())) {
					fetched.setAnswer(inquiry.getAnswer());
				}

				inquiryService.save(fetched);
				
				return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("consult.update.error.", e);
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(
			@PathVariable(name = "id", required = true) Long id)
	{
		try {
			inquiryService.delete(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("consult.delete.error.", e);
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@PostMapping("/dels")
	public ResponseEntity<?> deletes(
			@RequestBody List<Long> ids)
	{
		try {
			inquiryService.delete(ids);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("consult.delete.error.", e);
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}