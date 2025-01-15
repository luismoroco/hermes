package com.hermes.core.modules.auth.request;

import com.hermes.core.modules.user.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
  private UserType userType;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
}
