package com.hermes.core.modules.auth.web.validator;

import com.hermes.core.common.request.RequestAdapter;
import com.hermes.core.modules.auth.request.SignUpRequest;
import com.hermes.core.modules.user.model.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpWebRequest implements RequestAdapter<SignUpRequest> {
  @NotNull private UserType userType;
  @NotBlank private String firstName;
  @NotBlank private String lastName;
  @NotBlank private String password;
  @NotBlank @Email private String username;

  @Override
  public Class<SignUpRequest> getTargetClass() {
    return SignUpRequest.class;
  }
}
