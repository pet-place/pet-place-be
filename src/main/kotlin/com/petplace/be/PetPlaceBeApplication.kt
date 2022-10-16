package com.petplace.be

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PetPlaceBeApplication

fun main(args: Array<String>) {
	runApplication<PetPlaceBeApplication>(*args)
}
