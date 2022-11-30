package com.petplace.be.entity.base

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
class BaseEntity(
        @CreatedBy
        @Column(updatable = false)
        private var createdBy: String,

        @LastModifiedBy
        private var modifiedBy: String,
        override var createAt: LocalDateTime,
        override var updateAt: LocalDateTime
): BaseTimeEntity(createAt, updateAt) {
}