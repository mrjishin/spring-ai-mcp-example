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

package kr.co.piconet.ai.sample.mcp.tools;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import kr.co.piconet.ai.sample.entity.Inquiry;
import kr.co.piconet.ai.sample.service.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class InquiryTool {
	private final InquiryService inquiryService;

	@Tool(
			description = "Search for inquiries by ID and display a list."
		)
	public List<Inquiry> findByIdOrQuestion(
			@ToolParam(description = "ID", required = false) Long id,
			@ToolParam(description = "Question", required = false) String question)
	{
		try {
			return inquiryService.find(id, question);
		} catch (Exception e) {
			log.error("inquiry.find.error.", e);
			return List.of();
		}
	}

	@Tool(
			description = "Search for inquiries by question and display a list."
		)
	public List<Inquiry> findByQuestion(
			@ToolParam(description = "Question", required = false) String question)
	{
		try {
			return inquiryService.findByQuestion(question);
		} catch (Exception e) {
			log.error("inquiry.find.error.", e);
			return List.of();
		}
	}

	@Tool(
			description = "Create a URL to search and download the inquiry details that include the question in the inquiry content and create a PDF file."
		)
	public String findByQuestionAndPdf(
			@ToolParam(description = "Question", required = false) String question)
	{
		try {
			// TODO Search by question.
			//

			// TODO Create PDF file.
			//
			
			// TODO Make URL of the pdf.
			String url = "http://localhost:8081/api/inquiries/list.pdf";

			return url;
		} catch (Exception e) {
			log.error("inquiry.find.error.", e);
			return e.getMessage();
		}
	}


	@Tool(
			description = "Create a new inquiry."
		)
	public String add(
			@ToolParam(description = "Question", required = true) String question)
	{
		try {
			Inquiry inquiry = new Inquiry();
			inquiry.setQuestion(question);

			inquiryService.save(inquiry);
			return String.format("Your inquiry has been successfully registered. (ID: %d)", inquiry.getId());
		} catch (Exception e) {
			log.error("inquiry.find.error.", e);
			return e.getMessage();
		}
	}

	@Tool(
			description = "Edit or change your inquiry answer."
		)
	public String updateAnswer(
			@ToolParam(description = "id, ID", required = true) Long id,
			@ToolParam(description = "Answer", required = true) String answer)
	{
		try {
			if(id == null) {
				return "The ID value is required to edit the inquiry contents.";
			}
			if(!StringUtils.hasText(answer)) {
				return "The reply value is required to modify the inquiry content.";
			}
			inquiryService.updateAnswer(id, answer);
			return "Your inquiry has been successfully changed.";
		} catch (Exception e) {
			log.error("inquiry.find.error.", e);
			return e.getMessage();
		}
	}

	@Tool(
			description = "Modify or change the inquiry content corresponding to the ID."
		)
	public String update(
			@ToolParam(description = "ID", required = true) Long id,
			@ToolParam(description = "Question", required = true) String question,
			@ToolParam(description = "Answer", required = true) String answer)
	{
		try {
			if(id == null) {
				return "상담내역을 수정하기 위해서는 ID 필요합니다.";
			}
			Inquiry inquiry = inquiryService.getById(id);
			if(StringUtils.hasText(question)) {
				inquiry.setQuestion(question);
			}
			if(StringUtils.hasText(answer)) {
				inquiry.setAnswer(answer);
			}

			inquiryService.save(inquiry);

			return "상담이 정상적으로 변경되었습니다.";
		} catch (Exception e) {
			log.error("inquiry.find.error.", e);
			return e.getMessage();
		}
	}
}