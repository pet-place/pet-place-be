package com.petplace.be.common.entity

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
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
