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
public class PopularCommentSortConditionArgumentResolver implements HandlerMethodArgumentResolver {

    private static final int IGNORED_PAGE = 0;
    private static final int DEFAULT_SIZE = 3;
    private static final String DEFAULT_SORT_BY = "likeCount";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(PopularCommentSortCondition.class) && parameter.getParameterType()
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
                          .withSort(Direction.DESC, DEFAULT_SORT_BY);
    }
}
