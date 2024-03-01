package com.th.plu.domain.domain.member

import com.th.plu.domain.domain.common.BaseEntity
import jakarta.persistence.*

@Table(name = "onboardings")
@Entity
class Onboarding(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "onboarding_id")
    var id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = [CascadeType.ALL])
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member,

    @Column(name = "nickname", nullable = false, length = 30)
    var nickname: String,
) : BaseEntity() {

    companion object {

        fun newInstance(member: Member, nickname: String): Onboarding {
            return Onboarding(
                id = null,
                member = member,
                nickname = nickname
            )
        }
    }
}
