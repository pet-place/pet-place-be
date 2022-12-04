package com.petplace.be.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import org.springdoc.core.GroupedOpenApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(@Value("\${springdoc.swagger-ui.version}") springdocVersion: String?): OpenAPI? {
        val info = io.swagger.v3.oas.models.info.Info()
            .title("Pet Place APIs")
            .version(springdocVersion)
            .description("펫 플레이스 API 명세서")
        return OpenAPI()
            .components(Components())
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
    fun petPAI(): GroupedOpenApi {
        val paths = arrayOf("/pets/**")

        return GroupedOpenApi.builder()
            .group("펫 API")
            .pathsToMatch(*paths)
            .build()
    }
}