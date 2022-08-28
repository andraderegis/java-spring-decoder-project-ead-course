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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.course.controllers.dtos.CourseDto;
import com.ead.course.models.CourseModel;
import com.ead.course.services.CourseService;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

  @Autowired
  CourseService courseService;

  @PostMapping
  public ResponseEntity<Object> save(@RequestBody @Valid CourseDto courseDto) {
    var courseModel = new CourseModel();

    BeanUtils.copyProperties(courseDto, courseModel);

    return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseModel));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
    Optional<CourseModel> courseModelOptional = courseService.findById(id);

    if (!courseModelOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    courseService.delete(courseModelOptional.get());

    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id,
      @RequestBody @Valid CourseDto courseDto) {
    Optional<CourseModel> courseModelOptional = courseService.findById(id);

    if (!courseModelOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    var courseModel = courseModelOptional.get();
    BeanUtils.copyProperties(courseDto, courseModel);

    return ResponseEntity.ok().body(courseService.save(courseModel));
  }

  @GetMapping
  public ResponseEntity<List<CourseModel>> getAllCourses() {
    return ResponseEntity.ok().body(courseService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getById(@PathVariable(value = "id") UUID id) {
    Optional<CourseModel> courseModelOptional = courseService.findById(id);

    if (!courseModelOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok().body(courseModelOptional.get());
  }
}
