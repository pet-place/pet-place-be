package com.petplace.be.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.petplace.be.common.entity.BaseTimeEntity
import javax.persistence.*

@Entity
class Place(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val name:String,                // 플레이스 이름
    val description:String,         // 플레이스 설명
    var profileUrl:String? = null,  // 플레이스 프로필 주소

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    var pets: MutableList<Pet> = mutableListOf(),   // 플레이스 소속 반려동물들
) : BaseTimeEntity()
