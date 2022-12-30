package com.dfoff.demo.Domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@ToString
public class UserAccount {
    @Id
    @Column(length = 50)
    String userId;

    @Setter
    @Column(length = 50)
    String password;

    @Setter
    @Column(length = 50,unique = true)
    String nickname;

    @Setter
    @Column(length = 100,unique = true)
    String email;

    @Getter
    @RequiredArgsConstructor
    @Builder
    static class PrincipalDto implements UserDetails {

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public String getUsername() {
            return null;
        }

        @Override
        public boolean isAccountNonExpired() {
            return false;
        }

        @Override
        public boolean isAccountNonLocked() {
            return false;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }
    }


}
