package com.th.plu.domain.domain.like.repository

import com.th.plu.domain.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<Member, Long>, LikeRepositoryCustom {
}
