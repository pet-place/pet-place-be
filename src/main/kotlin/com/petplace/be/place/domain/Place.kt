package com.petplace.be.place.domain

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.petplace.be.common.entity.BaseEntity
import com.petplace.be.pet.domain.Pet
import javax.persistence.*

@Entity
class Place(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0L,

    var name:String,                // 플레이스 이름
    var description:String,         // 플레이스 설명
    var profileUrl:String? = null,  // 플레이스 프로필 주소
    var deleted: Boolean = false,

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    var pets: MutableList<Pet> = mutableListOf(),   // 플레이스 소속 반려동물들
) : BaseEntity() {

    fun update(name: String?, description: String?, profileUrl: String?){
        if (name != null) {
            this.name = name
        }
        if (description != null) {
            this.description = description
        }
        this.profileUrl = profileUrl
    }

    fun delete(){
        this.deleted = true
    }

    fun updateProfileImage(profileUrl: String){
        this.profileUrl = profileUrl
    }
}
