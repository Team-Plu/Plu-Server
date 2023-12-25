package com.th.plu.domain.member

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

    @Column(name = "SOCIAL_ID", nullable = false, length = 300)
    private val socialId: String,

    @Column(name = "SOCIAL_TYPE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private val socialType : MemberSocialType
)
