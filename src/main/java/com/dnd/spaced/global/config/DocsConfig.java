package com.dnd.spaced.global.config;

import com.dnd.spaced.global.docs.ExampleHolder;
import com.dnd.spaced.global.docs.annotation.ExceptionSpec;
import com.dnd.spaced.global.docs.annotation.ExcludeCommonHeaderSpec;
import com.dnd.spaced.global.docs.annotation.NotRequiredCommonHeaderSpec;
import com.dnd.spaced.global.exception.ExceptionCode;
import com.dnd.spaced.global.exception.ExceptionTranslator;
import com.dnd.spaced.global.exception.dto.ExceptionDto;
import com.dnd.spaced.global.security.dto.response.ExceptionResponse;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class DocsConfig {

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(apiInfo())
                .components(new Components().addSecuritySchemes(
                        "bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT token authentication")
                                .in(SecurityScheme.In.HEADER))
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));

        addLoginApiDocsSpec(openAPI);

        return openAPI;
    }

    private void addLoginApiDocsSpec(OpenAPI openAPI) {
        openAPI.path("/login/{providerType}",
                new PathItem().get(new Operation()
                        .summary("OAuth2 로그인")
                        .description("OAuth2 로그인 API")
                        .tags(List.of("OAuth2 관련 API"))
                        .addParametersItem(new Parameter().name("providerType")
                                                          .description("OAuth2 Provider 식별자")
                                                          .required(true)
                                                          .in("path")
                                                          .schema(new StringSchema()._default("google")
                                                                                    .addEnumItem("google")
                                                          )
                        )
                        .responses(new ApiResponses()
                                .addApiResponse("302", new ApiResponse().description("OAuth 동의 화면으로 리다이렉션"))
                                .addApiResponse("401", createInvalidRegistrationIdExceptionResponse())
                        )
                )
        );
    }

    private ApiResponse createInvalidRegistrationIdExceptionResponse() {
        ApiResponse apiResponse = new ApiResponse();
        Content content = new Content();
        MediaType mediaType = new MediaType();
        String message = "지원하지 않는 소셜 로그인 방식입니다.";
        Example example = new Example().description(message);

        example.setValue(
                new ExceptionResponse(
                        "INVALID_REGISTRATION_ID",
                        message
                )
        );
        mediaType.addExamples("400", example);
        content.addMediaType("application/json", mediaType);
        apiResponse.setContent(content);

        return apiResponse;
    }

    @Bean
    public OperationCustomizer customGlobalHeaders() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            if (!isExcludedApi(handlerMethod)) {
                Parameter commonHeader = new Parameter();

                commonHeader.in("header")
                            .name("Authorization")
                            .description("access token 헤더, Bearer 타입만 허용")
                            .required(isNotRequiredHeader(handlerMethod))
                            .schema(new StringSchema())
                            .example("Bearer <access-token-value>");
                operation.addParametersItem(commonHeader);
            }
            return operation;
        };
    }

    @Bean
    public OperationCustomizer customize() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            ExceptionSpec exceptionSpec = handlerMethod.getMethodAnnotation(ExceptionSpec.class);
            Map<Integer, List<ExampleHolder>> result = new HashMap<>();

            if (exceptionSpec != null) {
                generateExceptionExample(result, exceptionSpec.values());
            }

            addExamples(operation.getResponses(), result);

            return operation;
        };
    }

    private Info apiInfo() {
        return new Info()
                .title("SPACE D API 명세")
                .description("API 명세")
                .version("1.0.0");
    }

    private boolean isExcludedApi(HandlerMethod handlerMethod) {
        return handlerMethod.hasMethodAnnotation(ExcludeCommonHeaderSpec.class);
    }

    private boolean isNotRequiredHeader(HandlerMethod handlerMethod) {
        return !handlerMethod.hasMethodAnnotation(NotRequiredCommonHeaderSpec.class);
    }

    private void generateExceptionExample(Map<Integer, List<ExampleHolder>> result, ExceptionCode[] codes) {
        for (ExceptionCode code : codes) {
            ExceptionTranslator translator = ExceptionTranslator.find(code);

            calculateExampleHolder(
                    result,
                    translator.getMessage(),
                    translator.getHttpStatus().value(),
                    translator.getCode().toString(),
                    translator.translate()
            );
        }
    }

    private void calculateExampleHolder(
            Map<Integer, List<ExampleHolder>> result,
            String message,
            int httpStatus,
            String errorCode,
            ExceptionDto dto
    ) {
        Example example = new Example();

        example.description(message);
        example.setValue(dto);

        ExampleHolder exampleHolder = new ExampleHolder(example, errorCode, httpStatus);
        List<ExampleHolder> exampleHolders = result.computeIfAbsent(exampleHolder.code(), key -> new ArrayList<>());

        exampleHolders.add(exampleHolder);
    }

    private void addExamples(ApiResponses responses, Map<Integer, List<ExampleHolder>> result) {
        result.forEach(
                (httpStatus, exampleHolders) -> {
                    Content content = new Content();
                    MediaType mediaType = new MediaType();
                    ApiResponse apiResponse = new ApiResponse();

                    exampleHolders.forEach(
                            exampleHolder -> mediaType.addExamples(exampleHolder.name(), exampleHolder.holder())
                    );
                    content.addMediaType("application/json", mediaType);
                    apiResponse.setContent(content);
                    responses.addApiResponse(httpStatus.toString(), apiResponse);
                }
        );
    }
}
