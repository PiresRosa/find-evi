package com.dnd.spaced.global.resolver.comment;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LikedCommentSortConditionArgumentResolver implements HandlerMethodArgumentResolver {

    private static final int IGNORED_PAGE = 0;
    private static final int DEFAULT_SIZE = 15;
    private static final String IGNORE_SORT_BY = "id";
    private static final Direction IGNORE_DIRECTION = Direction.DESC;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LikedCommentSortCondition.class) && parameter.getParameterType()
                                                                                             .equals(Pageable.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        return PageRequest.of(IGNORED_PAGE, DEFAULT_SIZE)
                          .withSort(IGNORE_DIRECTION, IGNORE_SORT_BY);
    }
}
