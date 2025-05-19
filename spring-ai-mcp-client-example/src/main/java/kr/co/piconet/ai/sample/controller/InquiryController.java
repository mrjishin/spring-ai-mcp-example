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

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.piconet.ai.sample.model.Inquiry;
import kr.co.piconet.ai.sample.service.InquiryService;
import kr.co.piconet.ai.sample.util.BeanUtilsEx;
import kr.co.piconet.ai.sample.util.Paging;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/inquiries")
@Controller
public class InquiryController
{
	private final InquiryService inquiryService;

	@GetMapping(value = {"", "/"})
	public String index(
			@RequestParam(name = "question", required = false) String question,
			@RequestParam(name = "page", required=false, defaultValue="1") Long page,
			@RequestParam(name = "limit", required=false, defaultValue="15") Integer limit,
			ModelMap modelMap)
	{
		try {
			long count = inquiryService.count(question);
			Paging paging = Paging.of(count, page, limit);
			modelMap.put("paging", paging);

			List<Inquiry> inquiries = inquiryService.find(question, paging.getStartRow(), paging.getLimit());
			modelMap.put("inquiries", inquiries);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "inquiries/index";
	}

	@GetMapping("/addForm")
	public String addForm() {
		return "consults/addForm";
	}

	@PostMapping("/add")
	public String add(Inquiry consult) {
		try {
			inquiryService.insert(consult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:./";
	}

	@GetMapping("/editForm")
	public String editForm(
			@RequestParam(name = "id", required = true) Long id,
			ModelMap modelMap)
	{
		try {
			Inquiry inquiry = inquiryService.getById(id);
			modelMap.put("inquiry", inquiry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "inquiries/editForm";
	}

	@PostMapping("/edit")
	public String edit(Inquiry consult)
	{
		try {
			Inquiry fetched = inquiryService.getById(consult.getId());
			BeanUtilsEx.copyNotNullProperties(consult, fetched);

			inquiryService.update(fetched);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:./";
	}
}