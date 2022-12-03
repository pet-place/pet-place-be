package com.petplace.be.entity

import com.petplace.be.entity.base.BaseEntity
import com.petplace.be.entity.base.BaseTimeEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Place(
    @Id
    @GeneratedValue
    var id: Long? = null,

    val name:String,                // 플레이스 이름
    val description:String,         // 플레이스 설명
    var profileUrl:String? = null,  // 플레이스 프로필 주소

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    var pets: MutableList<Pet> = mutableListOf(),   // 플레이스 소속 반려동물들
) : BaseTimeEntity()