package com.petplace.be.entity.base

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
class BaseEntity: BaseTimeEntity() {
        @CreatedBy
        @Column(updatable = false)
        protected lateinit var createdBy: String

        @LastModifiedBy
        protected lateinit var modifiedBy: String
}