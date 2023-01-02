package com.petplace.be.pet.domain

import com.petplace.be.pet.TodoCategory
import java.time.LocalDate
import javax.persistence.*


@Entity
@Table(name = "pp_todo")
data class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0L,

    var frequency: Int,
    var startDate: LocalDate?,
    var endDate: LocalDate?,
    var memo: String? = null,

    @Enumerated(EnumType.STRING)
    var category: TodoCategory,

    @ManyToOne
    @JoinColumn(name = "pet_id")
    val pet: Pet
){
    fun update(category: TodoCategory, frequency: Int, startDate: LocalDate?, endDate: LocalDate?, memo: String?){
        this.category = category
        this.frequency = frequency
        this.startDate = startDate
        this.endDate = endDate
        this.memo = memo
    }
}
