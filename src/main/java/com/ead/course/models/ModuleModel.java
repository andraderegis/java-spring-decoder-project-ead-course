package com.ead.course.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

// Lombok decorator. Avoid implement manual getters and setters
@Data
// All null attribute are hidden
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "MODULES")
public class ModuleModel implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false, length = 150)
  private String title;

  @Column(nullable = false, length = 250)
  private String description;

  @Column(nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
  @CreationTimestamp
  private LocalDateTime createdAt;

  /*
   * Utilizando o JsonProperty como feito abaixo, o mesmo será exibido em uma
   * serialização/deserialização somente em operações de escrita
   */
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @ManyToOne(optional = false)
  private CourseModel course;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @OneToMany(mappedBy = "module")
  private Set<LessonModel> lessons;
}
