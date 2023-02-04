package com.petplace.be.pet.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.petplace.be.common.entity.BaseEntity
import com.petplace.be.place.domain.Place
import com.petplace.be.pet.Gender
import com.petplace.be.pet.dto.param.PetUpdateParam
import javax.persistence.*

@Entity
@Table(name = "pp_pet")
class Pet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0L,

    var name: String,                   // 이름
    var age: String?,                   // 나이

    @Enumerated(EnumType.STRING)
    var gender: Gender,                 // 성별
    var characteristic: String?,        // 특징
    var liked: String?,                 // 좋아하는 것
    var disliked: String?,              // 싫어하는 것
    var profileImage: String? = null,

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pet")
    var todoList: MutableList<Todo> = mutableListOf(),

    @Column(name="place_id")
    var placeId: Long
) : BaseEntity() {

    fun updateProfileImage(profileImage: String?){
        this.profileImage = profileImage
    }

    fun update(pet: PetUpdateParam){
        this.name = pet.name
        this.age = pet.age
        this.gender = pet.gender
        this.characteristic = pet.characteristic
        this.liked = pet.liked
        this.disliked = pet.disliked
    }
}
