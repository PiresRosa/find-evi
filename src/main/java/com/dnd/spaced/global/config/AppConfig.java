package com.dnd.spaced.global.config;

import com.dnd.spaced.global.config.properties.ImageStorePathProperties;
import com.dnd.spaced.global.config.properties.UrlProperties;
import com.dnd.spaced.global.interceptor.AuthInterceptor;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfoArgumentResolver;
import com.dnd.spaced.global.resolver.comment.CommentSortConditionArgumentResolver;
import com.dnd.spaced.global.resolver.comment.PopularCommentSortConditionArgumentResolver;
import com.dnd.spaced.global.resolver.word.SearchWordSortConditionArgumentResolver;
import com.dnd.spaced.global.resolver.word.WordSortConditionArgumentResolver;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.Clock;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@EnableConfigurationProperties(value = {UrlProperties.class, ImageStorePathProperties.class})
public class AppConfig implements WebMvcConfigurer {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private final AuthInterceptor authInterceptor;
    private final AuthAccountInfoArgumentResolver authAccountInfoArgumentResolver;
    private final WordSortConditionArgumentResolver wordSortConditionArgumentResolver;
    private final CommentSortConditionArgumentResolver commentSortConditionArgumentResolver;
    private final SearchWordSortConditionArgumentResolver searchWordSortConditionArgumentResolver;
    private final PopularCommentSortConditionArgumentResolver popularCommentSortConditionArgumentResolver;

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.simpleDateFormat(DATE_TIME_FORMAT);
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        };
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authAccountInfoArgumentResolver);
        resolvers.add(wordSortConditionArgumentResolver);
        resolvers.add(commentSortConditionArgumentResolver);
        resolvers.add(searchWordSortConditionArgumentResolver);
        resolvers.add(popularCommentSortConditionArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login");
    }
}
