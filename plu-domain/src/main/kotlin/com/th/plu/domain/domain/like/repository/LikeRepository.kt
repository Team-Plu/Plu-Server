package com.th.plu.domain.domain.like.repository

import com.th.plu.domain.domain.like.Like
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<Like, Long>, LikeRepositoryCustom {
}
