package com.mysite.sbb.question.repository;

import com.mysite.sbb.question.entity.Question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

  Question findBySubject(String subject);

  List<Question> findBySubjectLike(String subject);

  // 페이지 기능 추가
  Page<Question> findAll(Pageable pageable);
}
