package com.mysite.sbb.question.controller;

import com.mysite.sbb.answer.dto.AnswerDto;
import com.mysite.sbb.member.dto.MemberDto;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.service.MemberService;
import com.mysite.sbb.question.dto.QuestionDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

  private final QuestionService questionService;

  // 사용자 정보 추가
  private final MemberService memberService;

  @GetMapping("/list")
  public String list(Model model,
                    @RequestParam(value = "page", defaultValue = "0") int page) { // 페이지 기능 추가
    Page<Question> paging = questionService.getList(page);
    log.info("paging info = {}", paging);
    model.addAttribute("paging", paging);
    return "question/list";
  }

  @GetMapping("/detail/{id}")
  public String detail(@PathVariable("id") Long id, Model model,
                       AnswerDto answerDto) { // 답변 등록 기능 시 추가
    Question question = questionService.getQuestion(id);
    model.addAttribute("question", question);
    model.addAttribute("answerDto", new AnswerDto());
    return "question/detail";
  }

  @PreAuthorize("isAuthenticated()") // 로그인한 사용자만 접근 가능
  @GetMapping("/create")
  public String createQuestion(Model model) {
    model.addAttribute("questionDto", new QuestionDto());
    return "question/inputForm";
  }

  @PreAuthorize("isAuthenticated()") // 로그인한 사용자만 접근 가능
  @PostMapping("/create")
  public String createQuestion(@Valid QuestionDto questionDto,
                               BindingResult bindingResult,
                               Principal principal,
                               Model model) {

    if (bindingResult.hasErrors()) {
      model.addAttribute("questionDto", questionDto);
      return "question/inputForm";
    }

    Member member = memberService.getMember(principal.getName());

    questionService.create(questionDto, member);
    return "redirect:/question/list";
  }

}
