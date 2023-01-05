package com.dfoff.demo.Domain;

import com.dfoff.demo.Domain.EnumType.UserAccount.SecurityRole;
import com.dfoff.demo.JpaAuditing.AuditingFields;
import io.micrometer.core.lang.Nullable;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
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
@EqualsAndHashCode(of = "userId", callSuper = false)
public class UserAccount extends AuditingFields {
    @Id
    @Column(length = 50)
    private String userId;

    @Setter
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
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<SecurityRole> roles;


    @Getter
    @RequiredArgsConstructor
    @Builder
    @Setter
    @ToString
    public static class PrincipalDto implements UserDetails {
        private final String username;
        private final String password;
        private final String nickname;
        private final String email;

        private final List<GrantedAuthority> authorities;


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
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
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class UserAccountResponse{
        private final String userId;
        private final String nickname;
        private final String email;
        private final Set<SecurityRole> roles;

        public static UserAccountResponse of(UserAccountDTO userAccount){
            return UserAccountResponse.builder()
                    .userId(userAccount.getUserId())
                    .nickname(userAccount.getNickname())
                    .email(userAccount.getEmail())
                    .roles(userAccount.getRoles())
                    .build();
        }
    }

    @AllArgsConstructor
    @Builder
    @Getter
    @ToString
    @EqualsAndHashCode
    public static class UserAccountDTO {
        private final String userId;
        @Setter
        private  String password;
        private final String nickname;
        private final String email;

        private final Set<SecurityRole> roles;

        private final LocalDateTime createdAt;
        private final String createdBy;
        private final LocalDateTime modifiedAt;
        private final String modifiedBy;

        public static UserAccountDTO from(UserAccount userAccount) {
            return UserAccountDTO.builder()
                    .userId(userAccount.getUserId())
                    .password(userAccount.getPassword())
                    .nickname(userAccount.getNickname())
                    .email(userAccount.getEmail())
                    .roles(userAccount.getRoles())
                    .createdAt(userAccount.getCreatedAt())
                    .createdBy(userAccount.getCreatedBy())
                    .modifiedAt(userAccount.getModifiedAt())
                    .modifiedBy(userAccount.getModifiedBy())
                    .build();
        }
        public static UserAccountDTO from(PrincipalDto principalDto){
            return UserAccountDTO.builder()
                    .userId(principalDto.getUsername())
                    .password(principalDto.getPassword())
                    .nickname(principalDto.getNickname())
                    .email(principalDto.getEmail())
                    .roles(null)
                    .createdAt(null)
                    .createdBy(null)
                    .modifiedAt(null)
                    .modifiedBy(null)
                    .build();
        }

        public static UserAccount toEntity(UserAccountDTO userAccountDto) {
            return UserAccount.builder()
                    .userId(userAccountDto.getUserId())
                    .password(userAccountDto.getPassword())
                    .nickname(userAccountDto.getNickname())
                    .email(userAccountDto.getEmail())
                    .roles(userAccountDto.getRoles())
                    .build();
        }


    }

    @Builder
    @Getter
    @ToString
    @EqualsAndHashCode
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

        public UserAccountDTO toDto(){
            return UserAccountDTO.builder()
                    .userId(userId)
                    .password(password)
                    .nickname(nickname)
                    .email(email)
                    .build();
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

    @Builder
    @Getter
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class UserAccountSignUpRequest{
        private final String userId;
        private final String password;
        private final String passwordCheck;
        private final String nickname;
        private final String email;

        private final Set<SecurityRole> roles = Set.of(SecurityRole.ROLE_USER);



        public UserAccountDTO toDto(){
            return UserAccountDTO.builder()
                    .userId(userId)
                    .password(password)
                    .nickname(nickname)
                    .email(email)
                    .roles(roles)
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
    @Getter
    @Builder
    @AllArgsConstructor
    public static class LoginDto{
        private String username;
        private String password;




    }


}
