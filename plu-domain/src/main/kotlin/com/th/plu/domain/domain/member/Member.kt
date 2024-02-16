package com.th.plu.domain.domain.member

import com.th.plu.domain.domain.answer.Answer
import com.th.plu.domain.domain.common.BaseEntity
import jakarta.persistence.*

@Table(name = "members")
@Entity
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    var id: Long? = null,

    @Column(name = "social_id", nullable = false, length = 300)
    var socialId: String,

    @Column(name = "social_type", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    var socialType: MemberSocialType,

    @Column(name = "member_role", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    var role: MemberRole,

    @Column(name = "fcm_token", nullable = false, length = 300)
    var fcmToken: String,

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = [CascadeType.ALL])
    @JoinColumn(name = "setting_id", nullable = false)
    var setting: Setting,

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var onboarding: Onboarding?,

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var answers: List<Answer> = mutableListOf()

) : BaseEntity() {

    companion object {
        fun newInstance(
            socialId: String, socialType: MemberSocialType, fcmToken: String,
            setting: Setting
        ): Member {
            return Member(
                id = null,
                socialId = socialId,
                socialType = socialType,
                role = MemberRole.MEMBER,
                fcmToken = fcmToken,
                setting = setting,
                onboarding = null
            )
        }
    }

    fun initOnboarding(onboarding: Onboarding) {
        this.onboarding = onboarding
    }
}
