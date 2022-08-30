package com.ead.course.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ead.course.controllers.dtos.LessonDto;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.LessonService;
import com.ead.course.services.ModuleService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {

  @Autowired
  LessonService lessonService;

  @Autowired
  ModuleService moduleService;

  @PostMapping("/modules/{moduleId}/lessons")
  public ResponseEntity<Object> save(@PathVariable(value = "moduleId") UUID moduleId,
      @RequestBody @Valid LessonDto lessonDto) {
    Optional<ModuleModel> moduleModelOptional = moduleService.findById(moduleId);

    if (!moduleModelOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found.");
    }

    var lessonModel = new LessonModel();
    lessonModel.setModule(moduleModelOptional.get());

    BeanUtils.copyProperties(lessonDto, lessonModel);

    return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lessonModel));
  }

  @DeleteMapping("/modules/{moduleId}/lessons/{id}")
  public ResponseEntity<Object> delete(@PathVariable(value = "moduleId") UUID moduleId,
      @PathVariable(value = "id") UUID id) {
    Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, id);

    if (!lessonModelOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
    }

    lessonService.delete(lessonModelOptional.get());

    return ResponseEntity.noContent().build();
  }

  @PutMapping("/modules/{moduleId}/lessons/{id}")
  public ResponseEntity<Object> update(@PathVariable(value = "moduleId") UUID moduleId,
      @PathVariable(value = "id") UUID id,
      @RequestBody @Valid LessonDto lessonDto) {
    Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, id);

    if (!lessonModelOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
    }

    var lessonModel = lessonModelOptional.get();
    BeanUtils.copyProperties(lessonDto, lessonModel);

    return ResponseEntity.ok().body(lessonService.save(lessonModel));
  }

  @GetMapping("/modules/{moduleId}/lessons")
  public ResponseEntity<List<LessonModel>> getAll(@PathVariable(value = "moduleId") UUID moduleId) {
    return ResponseEntity.ok().body(lessonService.findAllByModule(moduleId));
  }

  @GetMapping("/modules/{moduleId}/lessons/{id}")
  public ResponseEntity<Object> getById(@PathVariable(value = "moduleId") UUID moduleId,
      @PathVariable(value = "id") UUID id) {
    Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, id);

    if (!lessonModelOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
    }

    return ResponseEntity.ok().body(lessonModelOptional.get());
  }
}
