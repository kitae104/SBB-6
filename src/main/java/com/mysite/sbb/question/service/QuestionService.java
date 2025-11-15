package com.mysite.sbb.question.service;

import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.question.dto.QuestionDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

  private final QuestionRepository questionRepository;

  // 페이지 기능 추가
  public Page<Question> getList(int page) {
    List<Sort.Order> sorts = new ArrayList<Sort.Order>(); // 여러 정렬 기준을 위한 리스트
    sorts.add(Sort.Order.desc("created")); // 생성일 기준 내림차순 정렬
    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 페이지당 10개씩 조회 + 정렬 기능
    Page<Question> questionList = questionRepository.findAll(pageable);
    return questionList;
  }

  public Question getQuestion(Long id) {
    Question question = questionRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 질문이 없습니다 : " + id));
    return question;
  }

  public void create(QuestionDto questionDto, Member member) {
    Question question = Question.builder()
        .subject(questionDto.getSubject())
        .content(questionDto.getContent())
        .author(member) // 질문 작성자 설정(추가)
        .build();
    questionRepository.save(question);
  }

  public void modify(Question question, String subject, String content) {
    question.setSubject(subject);
    question.setContent(content);
    questionRepository.save(question);
  }

  public void delete(Question question) {
    questionRepository.delete(question);
  }
}
