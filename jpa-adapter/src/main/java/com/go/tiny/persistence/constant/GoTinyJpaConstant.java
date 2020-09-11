package com.go.tiny.persistence.constant;

public interface GoTinyJpaConstant {
  String UPDATE_PENDING = "UPDATE_PENDING";
  String DELETE_PENDING = "DELETE_PENDING";
  String SEQUENCE_NAME = "select MY_SEQ_NAME.currval from DUAL";
}
