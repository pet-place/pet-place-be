package com.petplace.be.entity.base

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class BaseEntity: BaseTimeEntity() {

    @CreatedBy
    @Column(updatable = false)
    private lateinit var createdBy: String

    @LastModifiedBy
    private lateinit var modifiedBy: String
}