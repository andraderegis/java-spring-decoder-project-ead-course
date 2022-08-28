package com.ead.course.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ead.course.models.ModuleModel;

public interface ModuleService {
  void delete(ModuleModel module);

  ModuleModel save(ModuleModel moduleModel);

  Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID id);

  List<ModuleModel> findAllByCourse(UUID courseId);
}
