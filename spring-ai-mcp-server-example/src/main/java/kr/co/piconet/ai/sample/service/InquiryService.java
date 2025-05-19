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

package kr.co.piconet.ai.sample.service;

import static kr.co.piconet.ai.sample.entity.QInquiry.inquiry;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.co.piconet.ai.sample.entity.Inquiry;
import kr.co.piconet.ai.sample.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class InquiryService
{
	private final InquiryRepository inquiryRepository;
	private final JPAQueryFactory queryFactory;

	public List<Inquiry> find(Long id, String question) throws Exception {
		return find(id, question, null, null);		
	}
	
	public List<Inquiry> find(Long id, String question, Long offset, Integer limit) throws Exception {
		JPAQuery<Inquiry> query = queryFactory.select(inquiry)
				.from(inquiry)
				.where(
						id != null ? inquiry.id.eq(id) : null,
						StringUtils.hasText(question) ? inquiry.question.contains(question) : null
				)
				.orderBy(inquiry.id.desc());

		if(offset != null && limit != null) {
			query
				.offset(offset)
				.limit(limit);
		}

		return query.fetch();
	}

	public long count(Long id, String question) throws Exception {
		return queryFactory.select(inquiry.count())
				.from(inquiry)
				.where(
						id != null ? inquiry.id.eq(id) : null,
						StringUtils.hasText(question) ? inquiry.question.contains(question) : null
				)
				.orderBy(inquiry.id.desc())
				.fetchOne();
	}

	public List<Inquiry> findByQuestion(String question) throws Exception {
		return queryFactory.select(inquiry)
				.from(inquiry)
				.where(
						StringUtils.hasText(question) ? inquiry.question.contains(question) : null
				)
				.orderBy(inquiry.id.desc())
				.limit(10)
				.fetch();
				
	}

	public Inquiry getById(Long id) throws Exception {
		return queryFactory.select(inquiry)
				.from(inquiry)
				.where(
						inquiry.id.eq(id)
				)
				.fetchOne();
	}

	@Transactional(readOnly = false)
	public void save(Inquiry object) throws Exception {
		inquiryRepository.saveAndFlush(object);
	}

	@Transactional(readOnly = false)
	public void updateAnswer(Long id, String answer) throws Exception {
		queryFactory.update(inquiry)
			.set(inquiry.answer, answer)
			.execute();
	}

	@Transactional(readOnly = false)
	public void delete(Long id) throws Exception {
		inquiryRepository.deleteById(id);;
	}

	@Transactional(readOnly = false)
	public void delete(List<Long> ids) throws Exception {
		inquiryRepository.deleteAllById(ids);
	}
}