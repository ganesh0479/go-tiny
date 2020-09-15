package com.go.tiny.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_sequence")
@SequenceGenerator(allocationSize = 1, name = "s_tiny_sequence", sequenceName = "s_tiny_sequence")
public class SequenceEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_tiny_sequence")
  private long id;
}
