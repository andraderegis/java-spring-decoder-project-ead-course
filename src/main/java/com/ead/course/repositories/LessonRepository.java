package com.ead.course.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ead.course.models.LessonModel;

public interface LessonRepository extends JpaRepository<LessonModel, UUID>, JpaSpecificationExecutor<LessonModel> {

  @Query(value = "select * from lessons where module_id = :moduleId", nativeQuery = true)
  List<LessonModel> findAllLessonsIntoModule(@Param("moduleId") UUID moduleId);

  @Query(value = "select * from lessons where module_id = :moduleId and id = :id", nativeQuery = true)
  Optional<LessonModel> findLessonIntoModule(@Param("moduleId") UUID moduleId, @Param("id") UUID id);

}
