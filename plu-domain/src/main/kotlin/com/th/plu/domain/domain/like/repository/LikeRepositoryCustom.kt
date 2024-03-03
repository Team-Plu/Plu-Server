package com.th.plu.domain.domain.like.repository

import com.th.plu.domain.domain.like.Like

interface LikeRepositoryCustom {

    fun findLikeById(id: Long): Like?
}
