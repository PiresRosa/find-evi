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
public class CommentSortConditionArgumentResolver implements HandlerMethodArgumentResolver {

    private static final int IGNORED_PAGE = 0;
    private static final int DEFAULT_SIZE = 15;
    private static final String DEFAULT_SORT_BY = "likeCount";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CommentSortCondition.class) && parameter.getParameterType()
                                                                                        .equals(Pageable.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        String sortBy = findSortBy(webRequest.getParameter("sortBy"));
        Direction sortOrder = findSortOrder(webRequest.getParameter("sortOrder"));
        int size = findSize(webRequest.getParameter("size"));

        return PageRequest.of(IGNORED_PAGE, size)
                          .withSort(sortOrder, sortBy);
    }

    private String findSortBy(String target) {
        if (target == null) {
            return DEFAULT_SORT_BY;
        }

        return target;
    }

    private Direction findSortOrder(String target) {
        if (target == null || Direction.DESC.name().equalsIgnoreCase(target)) {
            return Direction.DESC;
        }

        return Direction.ASC;
    }

    private int findSize(String target) {
        if (target == null) {
            return DEFAULT_SIZE;
        }
        try {
            return Integer.parseInt(target);
        } catch (NumberFormatException ignored) {
            return DEFAULT_SIZE;
        }
    }
}
