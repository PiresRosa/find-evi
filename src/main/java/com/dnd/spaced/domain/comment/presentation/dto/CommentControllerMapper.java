package com.dnd.spaced.domain.comment.presentation.dto;

import com.dnd.spaced.domain.comment.application.dto.response.LikedCommentDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultipleCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultiplePopularCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.WrittenCommentDto;
import com.dnd.spaced.domain.comment.presentation.dto.response.LikedCommentResponse;
import com.dnd.spaced.domain.comment.presentation.dto.response.MultipleCommentInfoResponse;
import com.dnd.spaced.domain.comment.presentation.dto.response.MultiplePopularCommentInfoResponse;
import com.dnd.spaced.domain.comment.presentation.dto.response.WrittenCommentResponse;
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
        return MultiplePopularCommentInfoResponse.from(dtos);
    }

    public static LikedCommentResponse ofLiked(List<LikedCommentDto> dtos, UrlProperties urlProperties) {
        return LikedCommentResponse.of(dtos, urlProperties.baseUrl(), urlProperties.imageUri());
    }

    public static WrittenCommentResponse ofWritten(List<WrittenCommentDto> dtos, UrlProperties urlProperties) {
        return WrittenCommentResponse.of(dtos, urlProperties.baseUrl(), urlProperties.imageUri());
    }
}
