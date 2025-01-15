package com.hermes.core.modules.user.database.user;

import com.hermes.core.common.mapping.Mapper;
import com.hermes.core.common.ModelAdapter;
import com.hermes.core.modules.user.model.User;
import com.hermes.core.modules.user.model.UserType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity implements UserDetails, ModelAdapter<User> {
  @Id private Long userId;
  private UserType userType;
  private String firstName;
  private String lastName;
  private String username;
  private String password;

  private Timestamp createdAt;
  private Timestamp updatedAt;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + this.userType.name()));
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
