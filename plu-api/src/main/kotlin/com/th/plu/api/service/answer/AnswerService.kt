package com.th.plu.api.service.answer

import com.th.plu.api.controller.answer.dto.response.AnswerInfoResponse
import com.th.plu.api.service.like.LikeValidator
import com.th.plu.domain.domain.answer.explorer.AnswerExplorer
import com.th.plu.domain.domain.answer.explorer.QuestionExplorer
import com.th.plu.domain.domain.like.Like
import com.th.plu.domain.domain.like.repository.LikeRepository
import com.th.plu.domain.domain.member.explorer.MemberExplorer
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AnswerService(
        private val questionExplorer: QuestionExplorer,
        private val answerExplorer: AnswerExplorer,
        private val likeRepository: LikeRepository,
        private val likeValidator: LikeValidator,
        private val memberExplorer: MemberExplorer
) {
    @Transactional(readOnly = true)
    fun findAnswerInfoById(id: Long): AnswerInfoResponse {
        val answer = answerExplorer.findAnswerById(id)
        val question = questionExplorer.findQuestionById(answer.getQuestionId())

        return AnswerInfoResponse.of(question, answer)
    }

    @Transactional
    fun likeAnswerById(memberId: Long, answerId: Long) {
        val member = memberExplorer.findMemberById(memberId)
        val answer = answerExplorer.findAnswerById(answerId)
        val question = questionExplorer.findQuestionById(answer.getQuestionId())

        likeValidator.validateNotExistLike(member = member, answer = answer, question = question)
        likeRepository.save(Like.newInstance(answer = answer, member = member, question = question))
    }
}