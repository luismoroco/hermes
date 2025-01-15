package com.hermes.core.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@MappedSuperclass
public class AuditableModel {
  @Column(insertable = false, updatable = false)
  protected Timestamp createdAt;

  @Column(insertable = false)
  protected Timestamp updatedAt;
}
