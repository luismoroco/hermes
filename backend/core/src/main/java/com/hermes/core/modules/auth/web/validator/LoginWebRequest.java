package com.hermes.core.modules.auth.web.validator;

import com.hermes.core.common.RequestAdapter;
import com.hermes.core.modules.auth.request.LoginRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginWebRequest implements RequestAdapter<LoginRequest> {
  @NotBlank @Email private String username;
  @NotBlank private String password;

  @Override
  public Class<LoginRequest> getTargetClass() {
    return LoginRequest.class;
  }
}
