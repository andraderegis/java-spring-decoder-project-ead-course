package com.ead.course.controllers;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import com.ead.course.controllers.dtos.ModuleDto;
import com.ead.course.models.CourseModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.ModuleService;
import com.ead.course.specifications.SpecificationTemplate;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {

  @Autowired
  ModuleService moduleService;

  @Autowired
  CourseService courseService;

  @PostMapping("/courses/{courseId}/modules")
  public ResponseEntity<Object> save(@PathVariable(value = "courseId") UUID courseId,
      @RequestBody @Valid ModuleDto moduleDto) {
    Optional<CourseModel> courseModelOptional = courseService.findById(courseId);

    if (!courseModelOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
    }

    var moduleModel = new ModuleModel();

    BeanUtils.copyProperties(moduleDto, moduleModel);
    moduleModel.setCourse(courseModelOptional.get());

    return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(moduleModel));
  }

  @DeleteMapping("/courses/{courseId}/modules/{id}")
  public ResponseEntity<Object> delete(@PathVariable(value = "courseId") UUID courseId,
      @PathVariable(value = "id") UUID id) {
    Optional<ModuleModel> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, id);

    if (!moduleModelOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.");
    }

    moduleService.delete(moduleModelOptional.get());

    return ResponseEntity.noContent().build();
  }

  @PutMapping("/courses/{courseId}/modules/{id}")
  public ResponseEntity<Object> update(@PathVariable(value = "courseId") UUID courseId,
      @PathVariable(value = "id") UUID id,
      @RequestBody @Valid ModuleDto moduleDto) {
    Optional<ModuleModel> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, id);

    if (!moduleModelOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.");
    }

    var moduleModel = moduleModelOptional.get();
    BeanUtils.copyProperties(moduleDto, moduleModel);

    return ResponseEntity.ok().body(moduleService.save(moduleModel));
  }

  @GetMapping("/courses/{courseId}/modules")
  public ResponseEntity<Page<ModuleModel>> getAllModules(@PathVariable(value = "courseId") UUID courseId,
      SpecificationTemplate.ModuleSpec spec,
      @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
    return ResponseEntity.ok()
        .body(moduleService.findAllByCourse(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable));
  }

  @GetMapping("/courses/{courseId}/modules/{id}")
  public ResponseEntity<Object> getById(@PathVariable(value = "courseId") UUID courseId,
      @PathVariable(value = "id") UUID id) {
    Optional<ModuleModel> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, id);

    if (!moduleModelOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.");
    }

    return ResponseEntity.ok().body(moduleModelOptional.get());
  }
}
