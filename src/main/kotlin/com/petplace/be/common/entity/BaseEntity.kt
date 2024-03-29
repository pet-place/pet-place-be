package com.petplace.be.common.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@MappedSuperclass
class BaseEntity {
    @Column(name = "created_at", nullable = false)
    protected lateinit var createAt: LocalDateTime

    @Column(name = "updated_at")
    protected lateinit var updateAt: LocalDateTime

    @PrePersist
    fun createAt(){
        this.createAt = LocalDateTime.now()
    }

    @PreUpdate
    fun updateAt(){
        this.updateAt = LocalDateTime.now()
    }
}
