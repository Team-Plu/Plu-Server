package com.th.plu.api.service.answer

import com.th.plu.api.controller.answer.dto.response.AnswerInfoResponse
import com.th.plu.api.service.like.LikeValidator
import com.th.plu.domain.domain.answer.dto.EveryAnswerRetrievePageResponses
import com.th.plu.domain.domain.answer.explorer.AnswerExplorer
import com.th.plu.domain.domain.answer.explorer.QuestionExplorer
import com.th.plu.domain.domain.answer.repository.AnswerRepository
import com.th.plu.domain.domain.like.Like
import com.th.plu.domain.domain.like.explorer.LikeExplorer
import com.th.plu.domain.domain.like.repository.LikeRepository
import com.th.plu.domain.domain.member.explorer.MemberExplorer
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AnswerService(
        private val answerExplorer: AnswerExplorer,
        private val answerRepository: AnswerRepository,
        private val answerValidator: AnswerValidator,
        private val likeRepository: LikeRepository,
        private val likeExplorer: LikeExplorer,
        private val likeValidator: LikeValidator,
        private val memberExplorer: MemberExplorer,
        private val questionExplorer: QuestionExplorer
) {
    @Transactional(readOnly = true)
    fun findAnswerInfoById(answerId: Long, memberId: Long): AnswerInfoResponse {
        val answer = answerExplorer.findAnswerById(answerId)
        if (!answer.isPublic) {
            answerValidator.validateIsMemberOwnerOfAnswer(answerId, memberId)
        }
        val question = questionExplorer.findQuestionById(answer.getQuestionId())

        return AnswerInfoResponse.of(question, answer)
    }

    @Transactional
    fun createLike(memberId: Long, answerId: Long) {
        val member = memberExplorer.findMemberById(memberId)
        val answer = answerExplorer.findAnswerById(answerId)

        likeValidator.validateNotExistLike(member = member, answer = answer, question = answer.question)
        likeRepository.save(Like.newInstance(answer = answer, member = member, question = answer.question))
    }

    @Transactional
    fun deleteLike(memberId: Long, answerId: Long) {
        val member = memberExplorer.findMemberById(memberId)
        val answer = answerExplorer.findAnswerById(answerId)

        val like = likeExplorer.findLikeByMemberAndAnswerAndQuestion(member = member, answer = answer, question = answer.question)
        likeRepository.delete(like)
    }

    @Transactional(readOnly = true)
    fun retrieveEveryAnswersWithCursor(lastAnswerId: Long, pageSize: Long): EveryAnswerRetrievePageResponses {
        val todayQuestionId = questionExplorer.findTodayQuestion().id
        val answerInfos = answerRepository.findEveryAnswersWithCursorAndPageSize(todayQuestionId!!, lastAnswerId, pageSize)
        return EveryAnswerRetrievePageResponses(answerInfos)
    }
}