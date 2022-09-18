package com.ead.course.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.ead.course.models.ModuleModel;

public interface ModuleService {
  void delete(ModuleModel module);

  ModuleModel save(ModuleModel moduleModel);

  Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID id);

  List<ModuleModel> findAllByCourse(UUID courseId);

  Optional<ModuleModel> findById(UUID id);

  Page<ModuleModel> findAllByCourse(Specification<ModuleModel> spec, Pageable pageable);
}
