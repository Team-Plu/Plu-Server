package com.th.plu.api.config.swagger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.slf4j.LoggerFactory
import org.springdoc.core.utils.SpringDocUtils
import org.springframework.web.server.WebSession
@Configuration
class SwaggerConfig {
    private val logger = LoggerFactory.getLogger(SwaggerConfig::class.java)

    init {
        SpringDocUtils.getConfig().addRequestWrapperToIgnore(
                WebSession::class.java,
        )
    }

    @Bean
    fun openApi(): OpenAPI {
        logger.debug("Starting Swagger")

        return OpenAPI()
                .info(
                        Info()
                                .title("Plu API Server")
                                .version("v1.0.0")
                                .description("Plu API Docs")
                )
    }
}
