package com.dfoff.demo.Domain;

import com.dfoff.demo.Domain.EnumType.UserAccount.SecurityRole;
import com.dfoff.demo.JpaAuditing.AuditingFields;
import io.micrometer.core.lang.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Entity
@Getter
@ToString
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor (access = AccessLevel.PRIVATE)
@Builder
@Table(indexes =
        {@Index(columnList = "email", unique = true)
                , @Index(columnList = "nickname", unique = true)})
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
    @Setter
    @JoinColumn(name="profile_icon", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    @OneToOne(fetch = FetchType.EAGER)
    private SaveFile profileIcon ;

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private final Set<UserAccountCharacterMapper> characterEntities = new LinkedHashSet<>();

    @OneToMany(mappedBy="userAccount",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private final Set<Board> articles = new LinkedHashSet<>();

    @OneToMany(mappedBy ="userAccount",fetch=  FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private final Set<BoardComment> comments = new LinkedHashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private final Set<SecurityRole> roles = new HashSet<>(Set.of(SecurityRole.ROLE_USER));

    @Setter
    @Builder.Default
    private String isDeleted = "N";



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount)) return false;
        UserAccount that = (UserAccount) o;
        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }


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

        private final SaveFile.SaveFileDTO profileIcon;

        private final List<GrantedAuthority> authorities;


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
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





    @Builder
    public record UserAccountDto(String userId, String password, String nickname, String email,
                                 SaveFile.SaveFileDTO profileIcon,
                                  Set<SecurityRole> roles, LocalDateTime createdAt,
                                 String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        public static UserAccountDto from(UserAccount userAccount) {
            return UserAccountDto.builder()
                    .userId(userAccount.getUserId())
                    .password(userAccount.getPassword())
                    .nickname(userAccount.getNickname())
                    .email(userAccount.getEmail())
                    .roles(userAccount.getRoles())
                    .profileIcon(SaveFile.SaveFileDTO.from(userAccount.getProfileIcon()))
                    .createdAt(userAccount.getCreatedAt())
                    .createdBy(userAccount.getCreatedBy())
                    .modifiedAt(userAccount.getModifiedAt())
                    .modifiedBy(userAccount.getModifiedBy())
                    .build();
        }

        public static UserAccountDto from(PrincipalDto principalDto) {
            return UserAccountDto.builder()
                    .userId(principalDto.getUsername())
                    .password(principalDto.getPassword())
                    .nickname(principalDto.getNickname())
                    .email(principalDto.getEmail())
                    .profileIcon(principalDto.getProfileIcon())
                    .build();
        }

        public UserAccount toEntity() {
            return UserAccount.builder()
                    .userId(this.userId())
                    .password(this.password())
                    .nickname(this.nickname())
                    .email(this.email())
                    .profileIcon(this.profileIcon() == null ? null : this.profileIcon().toEntity())
                    .build();
        }


    }

    @Builder
    public record UserAccountUpdateRequest(@NotEmpty String userId, @Nullable String password,
                                           @Nullable String passwordCheck, @Nullable String nickname,
                                           @Nullable String email) {
        public UserAccountUpdateRequest(String userId, String password, String passwordCheck, String nickname, String email) {
            this.userId = userId;
            this.password = password;
            this.passwordCheck = passwordCheck;
            this.nickname = nickname;
            this.email = email;
        }

        public UserAccountDto toDto() {
            return UserAccountDto.builder()
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
    public static class UserAccountSignUpRequest {
        private final String userId;
        private final String password;
        private final String passwordCheck;
        private final String nickname;
        private final String email;

        private final Set<SecurityRole> roles = Set.of(SecurityRole.ROLE_USER);


        public UserAccountDto toDto() {
            return UserAccountDto.builder()
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
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class LoginDto {
        private String username;
        private String password;
    }


    /**
     * A DTO for the {@link CharacterEntity} entity
     */
    @Data
    @Builder
    public static class CharacterUserAccountResponse implements Serializable {
        private final String characterId;
        private final String serverId;
        private final String characterName;
        private final Integer level;
        private final String jobGrowName;
        private final String adventureFame;
        private final String adventureName;

        public static CharacterUserAccountResponse from(CharacterEntity characterEntity) {
            return CharacterUserAccountResponse.builder()
                    .characterId(characterEntity.getCharacterId())
                    .serverId(characterEntity.getServerId())
                    .characterName(characterEntity.getCharacterName())
                    .level(characterEntity.getLevel())
                    .jobGrowName(characterEntity.getJobGrowName())
                    .adventureFame(characterEntity.getAdventureFame())
                    .adventureName(characterEntity.getAdventureName())
                    .build();
        }

        public static Set<CharacterUserAccountResponse> from(Set<UserAccountCharacterMapper> mapper){
            return mapper.stream().map(UserAccountCharacterMapper::getCharacter).map(CharacterUserAccountResponse::from).collect(Collectors.toSet());

        }
    }

    @Builder
    @Data
    public static class UserAccountMyPageResponse {
        private final String userId;
        private final String nickname;
        private final String email;
        private final Set<CharacterUserAccountResponse> characters;
        private final String profileIconPath;
        private final LocalDateTime createdAt;
        private final String createdBy;
        private final LocalDateTime modifiedAt;
        private final String modifiedBy;

        public static UserAccountMyPageResponse from(UserAccount userAccount) {
            return UserAccountMyPageResponse.builder()
                    .userId(userAccount.getUserId())
                    .nickname(userAccount.getNickname())
                    .email(userAccount.getEmail())
                    .characters(CharacterUserAccountResponse.from(userAccount.getCharacterEntities()))
                    .profileIconPath(userAccount.getProfileIcon() == null ? null : userAccount.getProfileIcon().getFilePath())
                    .createdAt(userAccount.getCreatedAt())
                    .createdBy(userAccount.getCreatedBy())
                    .modifiedAt(userAccount.getModifiedAt())
                    .modifiedBy(userAccount.getModifiedBy())
                    .build();
        }



    }
}
