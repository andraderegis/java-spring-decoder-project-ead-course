package com.ead.course.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.ead.course.models.LessonModel;

public interface LessonService {

  LessonModel save(LessonModel lessonModel);

  Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID id);

  void delete(LessonModel lessonModel);

  List<LessonModel> findAllByModule(UUID moduleId);

  Page<LessonModel> findAllByModule(Specification<LessonModel> and, Pageable pageable);

}
