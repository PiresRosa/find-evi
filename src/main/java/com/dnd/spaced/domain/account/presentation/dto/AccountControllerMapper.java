package com.dnd.spaced.domain.account.presentation.dto;

import com.dnd.spaced.domain.account.application.dto.response.ReadAccountInfoDto;
import com.dnd.spaced.domain.account.presentation.dto.response.AccountInfoResponse;
import com.dnd.spaced.global.config.properties.UrlProperties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountControllerMapper {

    public static AccountInfoResponse of(ReadAccountInfoDto dto, UrlProperties urlProperties) {
        return AccountInfoResponse.of(dto, urlProperties.baseUrl(), urlProperties.imageUri());
    }
}
