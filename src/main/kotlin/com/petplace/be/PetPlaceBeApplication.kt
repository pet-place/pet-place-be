package com.petplace.be

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class PetPlaceBeApplication

fun main(args: Array<String>) {
	runApplication<PetPlaceBeApplication>(*args)
}
