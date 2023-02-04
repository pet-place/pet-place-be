package com.petplace.be.place.domain

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.petplace.be.common.entity.BaseEntity
import com.petplace.be.pet.domain.Pet
import javax.persistence.*

@Entity
@Table(name = "pp_place")
class Place(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0L,

    var name:String,                // 플레이스 이름
    var description:String,         // 플레이스 설명
    var profileUrl:String? = null,  // 플레이스 프로필 주소
    var deleted: Boolean = false,

    @OneToMany(mappedBy = "placeId", cascade = [CascadeType.REMOVE])
    var pets: MutableList<Pet> = mutableListOf(),   // 플레이스 소속 반려동물들
) : BaseEntity() {

    fun update(name: String, description: String, profileUrl: String?){
        this.name = name
        this.description = description

        if(!profileUrl.isNullOrEmpty()){
            this.profileUrl = profileUrl
        }
    }

    fun delete(){
        this.deleted = true
    }

    fun updateProfileImage(profileUrl: String){
        this.profileUrl = profileUrl
    }
}
