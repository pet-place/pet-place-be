package com.petplace.be.pet.domain

import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
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
    var count: Int = 0,

    @Enumerated(EnumType.STRING)
    var category: TodoCategory,

    val petId: Long
){
    fun update(category: TodoCategory, frequency: Int, startDate: LocalDate?, endDate: LocalDate?, memo: String?){
        this.category = category
        this.frequency = frequency
        this.startDate = startDate
        this.endDate = endDate
        this.memo = memo
    }

    fun updateFrequency(checked: Boolean): Int{
        if ((checked && this.frequency == this.count)
            ||(!checked && this.count <= 0)){
            throw CommonException(ErrorCode.UNKNOWN)
        }

        if (checked){
            this.count ++
        }else{
            this.count --
        }
        return this.count
    }
}
