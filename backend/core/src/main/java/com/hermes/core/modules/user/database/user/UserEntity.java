package com.hermes.core.modules.user.database.user;

import com.hermes.core.common.mapping.Mapper;
import com.hermes.core.common.model.AuditableModel;
import com.hermes.core.common.model.ModelAdapter;
import com.hermes.core.modules.user.model.User;
import com.hermes.core.modules.user.model.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity extends AuditableModel implements UserDetails, ModelAdapter<User> {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long userId;
  @Enumerated(EnumType.STRING) private UserType userType;
  @NotBlank private String firstName;
  @NotBlank private String lastName;
  @NotBlank @Column(unique = true) private String username;
  @NotBlank private String password;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + this.userType.name()));
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return Boolean.TRUE;
  }

  @Override
  public boolean isAccountNonLocked() {
    return Boolean.TRUE;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return Boolean.TRUE;
  }

  @Override
  public boolean isEnabled() {
    return Boolean.TRUE;
  }

  @Override
  public User toModel() {
    return Mapper.get().map(this, User.class);
  }
}
