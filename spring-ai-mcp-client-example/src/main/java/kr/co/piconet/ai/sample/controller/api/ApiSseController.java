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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/sse")
@RestController
public class ApiSseController {
	long index = 0;
	@GetMapping(value = {"", "/"})
	public SseEmitter index() {
		SseEmitter emitter = new SseEmitter();
		new Thread(() -> {
			try {
				while(!Thread.currentThread().isInterrupted()) {
					UserInfo userInfo = new UserInfo();
					userInfo.setId(++index);
					userInfo.setAccount(String.format("account%d", index));
					userInfo.setName(String.format("User Name %d", index));
					emitter.send(SseEmitter.event().data(userInfo));
	                Thread.sleep(1000);
				}
			} catch (Exception e) {
				log.error("sse.error", e);
				emitter.completeWithError(e);
			} finally {
				emitter.complete();
			}
        }).start();

		return emitter;
	}

	@Getter
	@Setter
	private static class UserInfo {
		private Long id;
		private String account;
		private String name;
	}
}