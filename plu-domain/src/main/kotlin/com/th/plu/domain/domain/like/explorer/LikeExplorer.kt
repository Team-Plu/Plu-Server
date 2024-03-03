package com.th.plu.domain.domain.like.explorer

import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.NotFoundException
import com.th.plu.domain.domain.like.repository.LikeRepository
import org.springframework.stereotype.Component

@Component
class LikeExplorer(
        private val likeRepository: LikeRepository
) {
    fun findLikeById(id: Long) {
        likeRepository.findLikeById(id)
                ?: throw NotFoundException(ErrorCode.NOT_FOUND_LIKE_EXCEPTION, "존재하지 않는 공감(ID: $id) 입니다")
    }
}