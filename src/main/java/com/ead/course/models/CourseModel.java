package com.ead.course.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

// Lombok decorator. Avoid implement manual getters and setters
@Data
// All null attribute are hidden
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "COURSES")
public class CourseModel implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false)
  private UUID userInstructor;

  @Column(nullable = false, length = 150)
  private String name;

  @Column(nullable = false, length = 250)
  private String description;

  @Column
  private String imageUrl;

  @Column(nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CourseStatus status;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CourseLevel level;
}