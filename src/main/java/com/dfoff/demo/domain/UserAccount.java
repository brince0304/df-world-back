package com.dfoff.demo.domain;

import com.dfoff.demo.domain.enums.useraccount.SecurityRole;
import com.dfoff.demo.enums.OAuthProvider;
import com.dfoff.demo.oauth.OAuthInfoResponse;
import com.dfoff.demo.utils.CharactersUtil;
import com.dfoff.demo.utils.FileUtil;
import com.dfoff.demo.utils.NeopleApiUtil;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serializable;
import java.security.Principal;
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
@SQLDelete(sql = "UPDATE user_account SET deleted = true, deleted_at = now() WHERE user_id = ?")
public class UserAccount extends AuditingFields {
    @Id
    private String userId;
    @Setter
    private String password;
    @Setter
    @Column(length = 8, unique = true)
    private String nickname;
    @Setter
    @Column(length = 100, unique = true)
    private String email;

    @Setter
    @Enumerated(EnumType.STRING)
    @Nullable
    private OAuthProvider oAuthProvider;

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
    private Boolean deleted= Boolean.FALSE;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adventure_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Adventure adventure;

    @OneToMany (mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private Set<Notification> notifications = new LinkedHashSet<>();

    @Setter
    private String profileCharacterIcon;

    @Setter
    private String profileCharacterIconClassName;


    private LocalDateTime deletedAt;

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }


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
    public static class PrincipalDto implements UserDetails, Serializable {
        private final String username;
        private final String password;
        private final String nickname;
        private final String email;
        private final OAuthProvider oAuthProvider;
        private final SaveFile.SaveFileDto profileIcon;
        private final List<GrantedAuthority> authorities;

        public static PrincipalDto from (UserAccountDto userAccountDto) {
            return PrincipalDto.builder()
                    .username(userAccountDto.userId)
                    .password(userAccountDto.password)
                    .nickname(userAccountDto.nickname)
                    .email(userAccountDto.email)
                    .oAuthProvider(userAccountDto.oAuthProvider)
                    .profileIcon(userAccountDto.profileIcon)
                    .authorities(userAccountDto.roles.stream()
                            .map(SecurityRole::name)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList()))
                    .build();
        }


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
                                 SaveFile.SaveFileDto profileIcon,
                                  Set<SecurityRole> roles, LocalDateTime createdAt,
                                 String createdBy, LocalDateTime modifiedAt, String modifiedBy,OAuthProvider oAuthProvider) {
        public static UserAccountDto from(UserAccount userAccount) {
            return UserAccountDto.builder()
                    .userId(userAccount.getUserId())
                    .password(userAccount.getPassword())
                    .nickname(userAccount.getNickname())
                    .email(userAccount.getEmail())
                    .roles(userAccount.getRoles())
                    .profileIcon(SaveFile.SaveFileDto.from(userAccount.getProfileIcon()))
                    .createdAt(userAccount.getCreatedAt())
                    .createdBy(userAccount.getCreatedBy())
                    .modifiedAt(userAccount.getModifiedAt())
                    .modifiedBy(userAccount.getModifiedBy())
                    .oAuthProvider(userAccount.getOAuthProvider())
                    .build();
        }

        public static UserAccountDto from(OAuthInfoResponse infoResponse){
            return UserAccountDto.builder()
                    .userId(UUID.randomUUID().toString())
                    .nickname(infoResponse.getNickname())
                    .email(infoResponse.getEmail() != null ? infoResponse.getEmail() : infoResponse.getNickname())
                    .oAuthProvider(infoResponse.getOAuthProvider())
                    .password(UUID.randomUUID().toString())
                    .build();
        }

        public static UserAccountDto from(PrincipalDto principalDto) {
            return UserAccountDto.builder()
                    .userId(principalDto.getUsername())
                    .password(principalDto.getPassword())
                    .nickname(principalDto.getNickname())
                    .email(principalDto.getEmail())
                    .profileIcon(principalDto.getProfileIcon())
                    .oAuthProvider(principalDto.getOAuthProvider())
                    .build();
        }

        public UserAccount toEntity() {
            return UserAccount.builder()
                    .userId(this.userId())
                    .password(this.password())
                    .nickname(this.nickname())
                    .email(this.email())
                    .profileIcon(this.profileIcon() == null ? null : this.profileIcon().toEntity())
                    .oAuthProvider(this.oAuthProvider())
                    .build();
        }


    }

    @Builder
    public static class UserAccountUpdateRequest {
        @Size(min = 4, max = 20)
        private  String userId;
        @Setter
        @Size(min = 8, max = 20)
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$")
        private  String password;
        @Size(min = 8, max = 20)
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$")
        private String passwordCheck;
        @Size(min = 2, max = 8)
        private  String nickname;
        @Email
        private  String email;

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
    @NoArgsConstructor
    public static class UserAccountSignUpRequest {
        @Size(min = 4, max = 20)
        private  String username;
        @Setter
        @Size(min = 8, max = 20)
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$")
        private  String password;
        @Size(min = 8, max = 20)
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$")
        private String passwordCheck;
        @Size(min = 2, max = 8)
        private  String nickname;
        @Email
        private  String email;

        private OAuthProvider oAuthProvider;

        @Builder.Default
        private  Set<SecurityRole> roles = Set.of(SecurityRole.ROLE_USER);


        public UserAccountDto toDto() {
            return UserAccountDto.builder()
                    .userId(username)
                    .password(password)
                    .nickname(nickname)
                    .email(email)
                    .roles(roles)
                    .oAuthProvider(oAuthProvider)
                    .build();
        }


        public UserAccount toEntity() {
            return UserAccount.builder()
                    .userId(username)
                    .password(password)
                    .nickname(nickname)
                    .email(email)
                    .oAuthProvider(oAuthProvider)
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
        private final Integer adventureFame;
        private final String adventureName;
        private final String characterImgPath;
        private final Integer buffPower;
        private final Integer damageIncrease;
        private final String serverName;

        public static String getServerName(String serverId) {
            if (serverId.equals("bakal")) {
                return "바칼";
            } else if (serverId.equals("cain")) {
                return "카인";
            } else if (serverId.equals("diregie")) {
                return "디레지에";
            } else if (serverId.equals("hilder")) {
                return "힐더";
            } else if (serverId.equals("prey")) {
                return "프레이";
            } else if (serverId.equals("siroco")) {
                return "시로코";
            } else if (serverId.equals("casillas")) {
                return "카시야스";
            } else if (serverId.equals("anton")) {
                return "안톤";
            } else {
                return serverId;
            }
        }

        public static CharacterUserAccountResponse from(CharacterEntity characterEntity) {
            return CharacterUserAccountResponse.builder()
                    .characterId(characterEntity.getCharacterId())
                    .serverId(characterEntity.getServerId())
                    .characterName(characterEntity.getCharacterName())
                    .level(characterEntity.getLevel())
                    .jobGrowName(characterEntity.getJobGrowName())
                    .adventureFame(characterEntity.getAdventureFame())
                    .adventureName(characterEntity.getAdventureName())
                    .characterImgPath(NeopleApiUtil.getCharacterImgUri(characterEntity.getServerId(),characterEntity.getCharacterId(),"2"))
                    .buffPower(characterEntity.getBuffPower())
                    .damageIncrease(characterEntity.getDamageIncrease())
                    .serverName(getServerName(characterEntity.getServerId()))
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
        private final Set<CharacterUserAccountResponse> adventureCharacters;
        private final String representCharacterName;
        private final String profileCharacterIcon;
        private final String profileCharacterIconClassName;
        private final String adventureName;
        private final OAuthProvider oAuthProvider;


        public static UserAccountMyPageResponse from(UserAccount userAccount) {
            return UserAccountMyPageResponse.builder()
                    .userId(userAccount.getUserId())
                    .nickname(userAccount.getNickname())
                    .email(userAccount.getEmail())
                    .characters(CharacterUserAccountResponse.from(userAccount.getCharacterEntities()))
                    .profileIconPath(userAccount.getProfileIcon() == null ? null : FileUtil.getProfileIconPath(userAccount.getProfileIcon().getFileName()))
                    .createdAt(userAccount.getCreatedAt())
                    .createdBy(userAccount.getCreatedBy())
                    .modifiedAt(userAccount.getModifiedAt())
                    .modifiedBy(userAccount.getModifiedBy())
                    .adventureCharacters(userAccount.getAdventure() !=null ? userAccount.getAdventure().getCharacters().stream().map(CharacterUserAccountResponse::from).collect(Collectors.toSet()) : new HashSet<>())
                    .representCharacterName(userAccount.getAdventure() == null ? "" : userAccount.getAdventure().getRepresentCharacter().getCharacterName())
                    .profileCharacterIcon(userAccount.getProfileCharacterIcon() == null ? "" : userAccount.getProfileCharacterIcon())
                    .profileCharacterIconClassName(userAccount.getProfileCharacterIconClassName() == null ? "" : userAccount.getProfileCharacterIconClassName())
                    .adventureName(userAccount.getAdventure() == null ? "" : userAccount.getAdventure().getAdventureName())
                    .oAuthProvider(userAccount.getOAuthProvider())
                    .build();
        }



    }

    @Builder
    @Data
    public static class UserAdventureResponse{
        private String adventureName;
        private String representCharacterName;
        private String serverId;
        private String serverName;
        private String adventureFame;
        private String adventureDamageIncreaseAndBuffPower;
        private String modifiedAt;
        private Set<CharacterEntity.CharacterEntityMainPageResponse> characters;

        public static UserAdventureResponse from(UserAccount userAccount){
            return UserAdventureResponse.builder()
                    .adventureName(userAccount.getAdventure().getAdventureName())
                    .representCharacterName(userAccount.getAdventure().getRepresentCharacter().getCharacterName())
                    .serverId(userAccount.getAdventure().getRepresentCharacter().getServerId())
                    .serverName(CharacterEntity.CharacterEntityResponse.getServerName(userAccount.getAdventure().getRepresentCharacter().getServerId()))
                    .adventureFame(userAccount.getAdventure().getAdventureFame().toString())
                    .adventureDamageIncreaseAndBuffPower(userAccount.getAdventure().getAdventureDamageIncreaseAndBuffPower().toString())
                    .characters(userAccount.getAdventure().getCharacters().stream().map(CharacterEntity.CharacterEntityMainPageResponse::from).collect(Collectors.toSet()))
                    .modifiedAt(CharactersUtil.timesAgo(userAccount.getAdventure().getModifiedAt()))
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserLoginRequst {
        private String username;
        private String password;
    }

    @Data
    @Builder
    public static class UserLoginResponse{
        private String userId;
        private String nickname;
        private String profileImgPath;
        private String adventureName;
        private Long notificationCount;
        private OAuthProvider oAuthProvider;

        public static UserLoginResponse from(UserAccount userAccount){
            return UserLoginResponse.builder()
                    .userId(userAccount.getUserId())
                    .nickname(userAccount.getNickname())
                    .profileImgPath(userAccount.getProfileIcon() == null ? null : FileUtil.getProfileIconPath(userAccount.getProfileIcon().getFileName()))
                    .notificationCount(userAccount.notifications.stream().filter(notification -> !notification.getChecked()).count())
                    .adventureName(userAccount.getAdventure() == null ? "" : userAccount.getAdventure().getAdventureName())
                    .oAuthProvider(userAccount.getOAuthProvider())
                    .build();
        }
    }
}
