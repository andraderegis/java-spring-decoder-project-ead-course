package com.ead.course.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ead.course.models.ModuleModel;

public interface ModuleRepository extends JpaRepository<ModuleModel, UUID> {

  @Query(value = "select * from modules where course_id = :courseId", nativeQuery = true)
  List<ModuleModel> findAllModulesIntoCourse(@Param("courseId") UUID courseId);
}
