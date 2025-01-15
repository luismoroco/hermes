package com.hermes.core.common.session.model;

import com.hermes.core.modules.user.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionUser implements Serializable {
  private Long userId;
  private UserType userType;
}
