package com.th.plu.domain.member

import com.th.plu.domain.answer.Answer
import com.th.plu.domain.common.BaseEntity
import jakarta.persistence.*

@Table(name = "members")
@Entity
class Member(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long? = null,

    @Embedded
    var socialInfo: SocialInfo,

    @Column(name = "member_role", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    var role: MemberRole,

    @Column(name = "fcm_token", nullable = false, length = 300)
    var fcmToken: String,

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var setting: Setting,

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var onboarding: Onboarding,

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var answers: List<Answer>,

    ) : BaseEntity() {
}
