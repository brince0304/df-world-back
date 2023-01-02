package com.dfoff.demo.Domain;

import com.dfoff.demo.Domain.EnumType.SecurityRole;
import com.dfoff.demo.JpaAuditing.AuditingFields;
import io.micrometer.core.lang.Nullable;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@ToString
@Table(indexes =
        {@Index(columnList = "email", unique = true)
                , @Index(columnList = "nickname", unique = true)})
@Builder
public class UserAccount extends AuditingFields {
    @Id
    @Column(length = 50)
    private String userId;

    @Setter
    @Column(length = 50)
    private String password;

    @Setter
    @Column(length = 50, unique = true)
    private String nickname;

    @Setter
    @Column(length = 100, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    @Setter
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<SecurityRole> roles;


    @Getter
    @RequiredArgsConstructor
    @Builder
    @Setter
    public static class PrincipalDto implements UserDetails {
        private final String userId;
        private final String password;
        private final String nickname;
        private final String email;


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

    @AllArgsConstructor
    @Builder
    @Getter
    public static class UserAccountDto {
        private final String userId;
        private final String password;
        private final String nickname;
        private final String email;
        private final Set<SecurityRole> roles;

        public static UserAccountDto from(UserAccount userAccount) {
            return UserAccountDto.builder()
                    .userId(userAccount.getUserId())
                    .password(userAccount.getPassword())
                    .nickname(userAccount.getNickname())
                    .email(userAccount.getEmail())
                    .roles(userAccount.getRoles())
                    .build();
        }

        public UserAccount toEntity() {
            return UserAccount.builder()
                    .userId(userId)
                    .password(password)
                    .nickname(nickname)
                    .email(email)
                    .roles(roles)
                    .build();
        }


    }

    @Builder
    @Getter
    public static class UserAccountUpdateRequest{
        @NotEmpty
        private final String userId;
        @Nullable
        private final String password;
        @Nullable
        private final String passwordCheck;
        @Nullable
        private final String nickname;
        @Nullable
        private final String email;

        public UserAccountUpdateRequest(String userId, String password, String passwordCheck, String nickname, String email) {
            this.userId = userId;
            this.password = password;
            this.passwordCheck = passwordCheck;
            this.nickname = nickname;
            this.email = email;
        }


        public UserAccount toEntity() {
            return UserAccount.builder()
                    .userId(userId)
                    .password(password)
                    .nickname(nickname)
                    .email(email)
                    .build();
        }
    }


}
