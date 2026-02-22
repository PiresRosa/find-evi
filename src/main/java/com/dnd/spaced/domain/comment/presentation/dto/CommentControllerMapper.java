package com.dnd.spaced.domain.comment.presentation.dto;

import com.dnd.spaced.domain.comment.application.dto.response.MultipleCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultiplePopularCommentInfoDto;
import com.dnd.spaced.domain.comment.presentation.dto.response.MultipleCommentInfoResponse;
import com.dnd.spaced.domain.comment.presentation.dto.response.MultiplePopularCommentInfoResponse;
import com.dnd.spaced.global.config.properties.UrlProperties;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommentControllerMapper {

    public static MultipleCommentInfoResponse ofComments(
            List<MultipleCommentInfoDto> dtos,
            UrlProperties urlProperties
    ) {
        return MultipleCommentInfoResponse.of(dtos, urlProperties.baseUrl(), urlProperties.imageUri());
    }

    public static MultiplePopularCommentInfoResponse ofPopularComments(
            List<MultiplePopularCommentInfoDto> dtos,
            UrlProperties urlProperties
    ) {
        return MultiplePopularCommentInfoResponse.of(dtos, urlProperties.baseUrl(), urlProperties.imageUri());
    }
}
