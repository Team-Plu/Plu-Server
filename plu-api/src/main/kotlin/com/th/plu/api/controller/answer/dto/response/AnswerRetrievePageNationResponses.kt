package com.th.plu.api.controller.answer.dto.response

import com.querydsl.core.Tuple
import java.util.*

data class AnswerRetrievePageNationResponses(val answerInfos: List<AnswerRetrievePageNationResponse>) {

    companion object {
        fun fromAnswerInfos(answerTuples: List<Tuple>): AnswerRetrievePageNationResponses {
            var answerInfos = ArrayList<AnswerRetrievePageNationResponse>()
            for (answerTuple in answerTuples) {
                val answerId = answerTuple.get(0, Long::class.java)
                val likeCount = answerTuple.get(1, Long::class.java)
                val answerContent = answerTuple.get(2, String::class.java)
                answerInfos.add(AnswerRetrievePageNationResponse(answerId!!, likeCount!!, answerContent!!))
            }

            return AnswerRetrievePageNationResponses(Collections.unmodifiableList(answerInfos))
        }
    }
}