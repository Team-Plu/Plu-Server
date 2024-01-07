package com.th.plu.domain.domain.member

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.AllArgsConstructor
import lombok.EqualsAndHashCode
import lombok.Getter
import lombok.NoArgsConstructor

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Embeddable
class SocialInfo(

    @Column(name = "social_id", nullable = false, length = 300)
    val socialId: String,

    @Column(name = "social_type", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    val socialType : MemberSocialType
)
