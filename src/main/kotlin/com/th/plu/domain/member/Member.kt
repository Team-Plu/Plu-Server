package com.th.plu.domain.member

import com.th.plu.domain.answer.Answer
import com.th.plu.domain.common.BaseEntity
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor
import java.time.LocalDateTime

@Table(name = "members")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
class Member(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private var id: Long? = null,

    @Embedded
    private var socialInfo: SocialInfo,

    @Column(name = "member_role", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private var role: MemberRole,

    @Column(name = "fcm_token", nullable = false, length = 300)
    private var fcmToken: String,

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    private var setting: Setting,

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    private var onboarding: Onboarding,

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    private var answers: List<Answer>,

) : BaseEntity(LocalDateTime.now(), LocalDateTime.now()) {}
