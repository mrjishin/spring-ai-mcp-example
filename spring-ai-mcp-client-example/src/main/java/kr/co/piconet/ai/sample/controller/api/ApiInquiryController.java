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

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@DeleteMapping(value = {"", "/"})
	public ResponseEntity<?> deleteConsult(
			@RequestBody List<Long> ids) {
		try {
			inquiryService.delete(ids);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("consult.delete.error", e);
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}