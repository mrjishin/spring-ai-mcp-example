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

package kr.co.piconet.ai.sample.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Comment("Inquiry")
@Table(name = "inquiries")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Inquiry {
	@Comment("ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("Question")
	@Column(nullable = false, columnDefinition = "text")
	private String question;

	@Comment("Answer")
	@Column(columnDefinition = "text")
	private String answer;

	@JsonIgnore
	@Comment("Modified date")
	@Column(name="mod_date")
	@LastModifiedDate
	private LocalDateTime modDate;

	@Comment("Created date")
	@Column(name="reg_date")
	@CreatedDate
	private LocalDateTime regDate;
}