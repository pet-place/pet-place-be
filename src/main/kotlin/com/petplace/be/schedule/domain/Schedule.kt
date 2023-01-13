package com.petplace.be.schedule.domain

import com.petplace.be.common.entity.BaseEntity
import com.petplace.be.user.domain.User
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "pp_calendar")
class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0L,

    @Column(name = "target_date")
    var targetDate: LocalDate,

    @Column(name = "title")
    var title: String? = null,

    @Column(name = "contents")
    var contents: String? = null,

    @Column(name = "place_id")
    val placeId: Long,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    val createdBy: User,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by")
    var modifiedBy: User,
) : BaseEntity()
