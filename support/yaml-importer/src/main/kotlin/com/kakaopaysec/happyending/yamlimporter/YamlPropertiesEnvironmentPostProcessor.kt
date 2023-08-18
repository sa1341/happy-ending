package com.kakaopaysec.happyending.yamlimporter

import org.springframework.boot.SpringApplication
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.boot.env.YamlPropertySourceLoader
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.PropertySource
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import kotlin.streams.asSequence

@Order(Ordered.LOWEST_PRECEDENCE)
class YamlPropertiesEnvironmentPostProcessor : EnvironmentPostProcessor {

    private val yamlPropertySourceLoader: YamlPropertySourceLoader = YamlPropertySourceLoader()

    override fun postProcessEnvironment(environment: ConfigurableEnvironment, application: SpringApplication) {
        val activeProfiles = environment.activeProfiles
        val resourceLoader = application.resourceLoader ?: DefaultResourceLoader()
        val resourcePatternResolver = PathMatchingResourcePatternResolver(resourceLoader)
        val resources = resourcePatternResolver.getResources(FILE_LOCATION_PATTERN)

        resources.forEach { resource ->
            if (!resource.exists()) {
                throw IllegalArgumentException("Resource $resource does not exist")
            }

            yamlPropertySourceLoader.load(resource.filename, resource)
                .stream().asSequence()
                .filter { propertySource -> propertySource.source is Map<*, *> }
                .forEach { propertySource ->
                    val sourceMap = propertySource.source as Map<*, *>
                    val profiles = sourceMap[SPRING_PROFILES] ?: sourceMap[SPRING_PROFILES_ACTIVE]
                    if (profiles == null) {
                        addPropertySource(environment, propertySource)
                    } else {
                        profiles.toString().split(",")
                            .forEach { profile ->
                                if (activeProfiles.contains(profile.trim())) {
                                    addPropertySource(environment, propertySource)
                                }
                            }
                    }
                }
        }
    }

    private fun addPropertySource(environment: ConfigurableEnvironment, propertySource: PropertySource<*>) {
        environment.propertySources.addFirst(propertySource)
    }

    companion object {
        private const val SPRING_PROFILES = "spring.profiles"
        private const val SPRING_PROFILES_ACTIVE = "spring.config.activate.on-profile"
        private const val FILE_LOCATION_PATTERN = "classpath*:/config/*.yml"
    }
}
