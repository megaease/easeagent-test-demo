package com.megaease.template.server.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRuleConvention;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
@Profile({"dev", "test"})
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class) // only support @NotNull, @Min, @Max, and @Size, version=2.9.2
@EnableConfigurationProperties(SpringDataWebProperties.class)
public class WebMvcSwaggerConfig {
    private final SpringDataWebProperties springDataWebProperties;

    public WebMvcSwaggerConfig(SpringDataWebProperties springDataWebProperties) {
        this.springDataWebProperties = springDataWebProperties;
    }

    @Bean
    public Docket petApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.megaease"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
                .securitySchemes((apiKey()))
                .securityContexts(securityContext());
    }

    @Bean
    public AlternateTypeRuleConvention pageableConvention(final TypeResolver resolver) {
        return new AlternateTypeRuleConvention() {

            @Override
            public int getOrder() {
                return Ordered.HIGHEST_PRECEDENCE;
            }

            @Override
            public List<AlternateTypeRule> rules() {
                return singletonList(
                        newRule(resolver.resolve(Pageable.class), resolver.resolve(pageableMixin()))
                );
            }
        };
    }

    /**
     * 支持正常显示Pageable
     *
     * @return
     */
    private Type pageableMixin() {
        return new AlternateTypeBuilder()
                .fullyQualifiedClassName(
                        String.format("%s.generated.%s",
                                Pageable.class.getPackage().getName(),
                                Pageable.class.getSimpleName()))
                .withProperties(Stream.of(
                        property(Integer.class, springDataWebProperties.getPageable().getPageParameter()),
                        property(Integer.class, springDataWebProperties.getPageable().getSizeParameter()),
                        property(String.class, springDataWebProperties.getSort().getSortParameter())
                ).collect(toList()))
                .build();
    }

    private AlternateTypePropertyBuilder property(Class<?> type, String name) {
        return new AlternateTypePropertyBuilder()
                .withName(name)
                .withType(type)
                .withCanRead(true)
                .withCanWrite(true);
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("MegaEase Template REST API")
                .description("MegaEase Template REST API for Online Management System")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("MegaEase", "https://megaease.com/", "Service@MegaEase.com"))
                .build();
    }


    private List<ApiKey> apiKey() {
        return singletonList(new ApiKey("JWT", "Authorization", "header"));
    }

    private List<SecurityContext> securityContext() {
        return singletonList(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build());
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return singletonList(new SecurityReference("JWT", authorizationScopes));
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId("swagger-client-id")
                .clientSecret("swagger-client-clientSecret")
                .realm("swagger-realm")
                .appName("swagger-app")
                .scopeSeparator(",")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false)
                .build();
    }
}
