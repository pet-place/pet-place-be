package com.petplace.be.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.GroupedOpenApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*


@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(@Value("\${springdoc.version}") springdocVersion: String?): OpenAPI? {
        val info = io.swagger.v3.oas.models.info.Info()
            .title("Pet Place APIs")
            .version(springdocVersion)
            .description("펫 플레이스 API 명세서")

        val securityScheme = SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT").`in`(SecurityScheme.In.HEADER).name("Authorization").description("Token 값만 입력")
        val securityRequirement = SecurityRequirement().addList("bearerAuth")

        return OpenAPI()
                .components(Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(listOf(securityRequirement))
                .info(info)
    }

    @Bean
    fun placeAPI(): GroupedOpenApi {
        val paths = arrayOf("/places/**")

        return GroupedOpenApi.builder()
            .group("플레이스 API")
            .pathsToMatch(*paths)
            .build()
    }

    @Bean
    fun petAPI(): GroupedOpenApi {
        val paths = arrayOf("/pets/**")

        return GroupedOpenApi.builder()
            .group("펫 API")
            .pathsToMatch(*paths)
            .build()
    }

    @Bean
    fun userAPI(): GroupedOpenApi {
        val paths = arrayOf("/user/**")

        return GroupedOpenApi.builder()
            .group("사용자 API")
            .pathsToMatch(*paths)
            .build()
    }

    @Bean
    fun todoAPI(): GroupedOpenApi {
        val paths = arrayOf("/todo/**")

        return GroupedOpenApi.builder()
            .group("todo API")
            .pathsToMatch(*paths)
            .build()
    }

    @Bean
    fun storyAPI(): GroupedOpenApi {
        val paths = arrayOf("/story/**")

        return GroupedOpenApi.builder()
            .group("story API")
            .pathsToMatch(*paths)
            .build()
    }
}
