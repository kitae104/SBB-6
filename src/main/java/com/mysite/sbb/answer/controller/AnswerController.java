package com.mysite.sbb.answer.controller;

import com.mysite.sbb.answer.dto.AnswerDto;
import com.mysite.sbb.answer.service.AnswerService;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.service.MemberService;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {

  private final QuestionService questionService;
  private final AnswerService answerService;

  // 사용자 정보 추가
  private final MemberService memberService;

  @PostMapping("/create/{id}")
  public String createAnswer(@PathVariable("id") Long id,
                             @Valid AnswerDto answerDto,
                             BindingResult bindingResult,
                             Principal principal, // 현재 로그인한 사용자 정보
                             Model model ) {

    Question question = questionService.getQuestion(id);

    Member member = memberService.getMember(principal.getName()); // 현재 로그인한 사용자의 Member 엔티티 조회


    if(bindingResult.hasErrors()) {
      model.addAttribute("question", question);
      model.addAttribute("answerDto", answerDto);
      return "question/detail";
    }

    log.info("====== question : {}, answerDto: {}, member : {}", id, answerDto, member);
    answerService.create(question, answerDto, member);

    return "redirect:/question/detail/" + id;
  }
}
