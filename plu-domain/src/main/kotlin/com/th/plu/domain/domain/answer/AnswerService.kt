package com.th.plu.domain.domain.answer

import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.ConflictException
import com.th.plu.domain.domain.member.explorer.MemberExplorer
import com.th.plu.domain.domain.question.QuestionRetriever
import com.th.plu.domain.isUniqueError
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AnswerService(
    private val answerRegister: AnswerRegister,
    private val memberExplorer: MemberExplorer,
    private val questionRetriever: QuestionRetriever,
) {
    @Transactional
    fun writeAnswer(memberId: Long, questionId: Long, answerWriting: AnswerWriting): WritingAnswerResult {
        // validate not found
        val memberEntity = memberExplorer.findMemberById(memberId)
        val questionEntity = questionRetriever.findQuestion(questionId)

        return try {
            answerRegister.registerAnswer(memberEntity, questionEntity, answerWriting.body, answerWriting.open).let {
                WritingAnswerResult(
                    questionId = questionEntity.id,
                    questionTitle = questionEntity.title,
                    questionContent = questionEntity.content,
                    questionExposedAt = questionEntity.exposedAt,
                    questionElementType = questionEntity.elementType,
                    answerId = it.id,
                    answerBody = it.content,
                    reactionLikeCount = 0 // 최초 생성시는 0
                )
            }
        } catch (e: DataIntegrityViolationException) {
            if (e.isUniqueError()) {
                throw ConflictException(ErrorCode.CONFLICT_ANSWER_EXCEPTION, "이미 답변한 질문에 답변을 요청했습니다.")
            } else {
                throw e
            }
        }
    }
}