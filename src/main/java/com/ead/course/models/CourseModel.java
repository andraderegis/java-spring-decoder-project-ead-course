package com.ead.course.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.ead.course.constants.DateTimeConstants;
import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeConstants.DATE_TIME_FORMAT)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeConstants.DATE_TIME_FORMAT)
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CourseStatus status;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CourseLevel level;

  /*
   * Relacionamentos unidirecionais geram mais querys do que relacionamentos
   * bidirecionais, além do hibernante gerar automaticamente gera tabelas
   * associativas.
   * 
   * Outro ponto é sempre que possível utilizar Set em mapeamentos como esse. Além
   * de melhorar a performance, se houver mais coleções OneToMany mapeadas, ele
   * trará resultados somente da primeira coleção.
   * 
   * Utilizando o JsonProperty como feito abaixo, o mesmo será exibido em uma
   * serialização/deserialização somente em operações de escrita
   * 
   * FetchMode.SELECT: Realiza uma consulta inicial para trazer os cursos,
   * além de fazer uma consulta separada para cada um dos módulos vinculados aos
   * cursos. É um modo muito custoso.
   * 
   * FetchMode.JOIN: Realiza em uma única consulta todos os dados dos cursos, bem
   * como todos os dados dos módulos vinculados a ele. Nesse modo, o
   * FetchType.LAZY é ignorado,
   * retornando todos os dados como ocorre no FetchType.EAGER
   * 
   * FetchMode.SUBSELECT: Realiza uma consulta que retorna dos cursos e, uma única
   * consulta que retorna todos os dados referentes aos módulos. Nesse modo,
   * diferente do FetchMode.JOIN, o FetchType.LAZY é respeitado.
   * 
   * Caso nenhum FetchMode não seja definido, o modo padrão utilizado pelo
   * hibernate é o FetchMode.JOIN
   */
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
  @Fetch(FetchMode.SUBSELECT)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<ModuleModel> modules;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
  private Set<CourseUserModel> coursesUsers;
}
