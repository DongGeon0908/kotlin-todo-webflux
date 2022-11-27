package com.goofy.todo.config

import com.goofy.todo.extension.mapper
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.HttpMessageReader
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.http.codec.multipart.DefaultPartHttpMessageReader
import org.springframework.http.codec.multipart.MultipartHttpMessageReader
import org.springframework.http.codec.multipart.Part
import org.springframework.util.MimeType
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.DelegatingWebFluxConfiguration
import java.nio.charset.Charset

@Configuration
class WebConfig : DelegatingWebFluxConfiguration() {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOriginPatterns(CorsConfiguration.ALL)
            .allowedMethods(CorsConfiguration.ALL)
            .allowedHeaders(CorsConfiguration.ALL)
            .allowCredentials(true)
            .maxAge(3600)
    }

    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        configurer.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder(mapper, *mimeTypes))
        configurer.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(mapper))
        configurer.defaultCodecs().configureDefaultCodec { codec: Any ->
            if (codec is MultipartHttpMessageReader) {
                val partReader: HttpMessageReader<Part> = codec.partReader

                if (partReader is DefaultPartHttpMessageReader) {
                    partReader.setMaxHeadersSize(1024 * 16)
                }
            }
        }
    }

    private val mimeTypes: Array<MimeType> = arrayOf(
        MimeType("application", "json"),
        MimeType("application", "*+json"),
        MimeType("application", "json", Charset.forName("UTF-8"))
    )
}
