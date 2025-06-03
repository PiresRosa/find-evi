package com.dnd.spaced.domain.account.domain;

import com.dnd.spaced.global.entity.CreateTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NicknameMetadata extends CreateTimeEntity implements Persistable<String> {

    private static final long START_COUNT_VALUE = 1L;

    @Id
    private String nickname;

    private long count = START_COUNT_VALUE;

    public void addCount() {
        this.count++;
    }

    public NicknameMetadata(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean isNew() {
        return this.getCreatedAt() == null;
    }

    @Override
    public String getId() {
        return nickname;
    }
}
