package com.dnd.spaced.domain.account.domain;

import com.dnd.spaced.domain.account.domain.exception.InvalidEmailException;
import com.dnd.spaced.domain.account.domain.exception.InvalidNicknameException;
import com.dnd.spaced.domain.account.domain.exception.InvalidProfileImageException;
import com.dnd.spaced.global.entity.CreateTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "accounts")
@Getter
@Entity
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends CreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    private Account(String email, String nickname, String profileImage, String roleName) {
        validateContent(email, nickname, profileImage);

        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.role = Role.findBy(roleName);
    }

    private void validateContent(String email, String nickname, String profileImage) {
        if (isInvalidEmail(email)) {
            throw new InvalidEmailException();
        }
        if (isInvalidNickname(nickname)) {
            throw new InvalidNicknameException();
        }
        if (isInvalidProfileImage(profileImage)) {
            throw new InvalidProfileImageException();
        }
    }

    private boolean isInvalidEmail(String email) {
        return email == null || email.isBlank();
    }

    private boolean isInvalidNickname(String nickname) {
        return nickname == null || nickname.isBlank();
    }

    private boolean isInvalidProfileImage(String profileImage) {
        return profileImage == null || profileImage.isBlank();
    }
}
