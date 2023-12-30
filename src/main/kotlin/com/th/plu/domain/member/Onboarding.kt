package com.th.plu.domain.member

import com.th.plu.domain.common.BaseEntity
import jakarta.persistence.*

@Table(name = "onboardings")
@Entity
class Onboarding(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "onboarding_id")
    private val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = [CascadeType.ALL])
    @JoinColumn(name = "member_id", nullable = false)
    private var member: Member,
) : BaseEntity() {
}
