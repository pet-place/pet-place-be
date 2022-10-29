package com.petplace.be.entity.base

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@MappedSuperclass
class BaseTimeEntity(
        @Column(name = "created_at", nullable = false)
        protected var createAt: LocalDateTime,

        @Column(name = "updated_at")
        protected var updateAt: LocalDateTime
) {
    @PrePersist
    fun createAt(){
        this.createAt = LocalDateTime.now()
    }

    @PreUpdate
    fun updateAt(){
        this.updateAt = LocalDateTime.now()
    }

}