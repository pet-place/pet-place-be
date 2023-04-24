package com.petplace.be.pet


enum class TodoCategory(
    val value: String
    ) {
    MEAL("밥"), SNACK("간식"), WALK("산책"), MEDICINE("약");

    companion object {
        fun toList(): List<Map<String, String>> {
            val list = mutableListOf<Map<String, String>>()
            for (fruit in values()) {
                val map = mutableMapOf<String, String>()
                map["key"] = fruit.name
                map["korValue"] = fruit.value
                list.add(map)
            }
            return list
        }
    }

}