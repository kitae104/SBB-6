package com.mysite.sbb.answer.service;

import com.mysite.sbb.answer.dto.AnswerDto;
import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.answer.repository.AnswerRespository;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AnswerService {

  private final AnswerRespository answerRespository;

  public void create(Question question, AnswerDto answerDto, Member member) {
    Answer answer = Answer.builder()
        .content(answerDto.getContent())
        .question(question)
        .author(member)
        .build();

    answerRespository.save(answer);
  }
}
