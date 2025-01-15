package com.hermes.core.modules.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private Long userId;
  private UserType userType;
  private String firstName;
  private String lastName;
  private String userName;
  private String password;
}
