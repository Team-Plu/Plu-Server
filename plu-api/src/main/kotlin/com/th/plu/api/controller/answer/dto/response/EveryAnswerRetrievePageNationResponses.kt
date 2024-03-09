package com.th.plu.api.controller.answer.dto.response

import com.querydsl.core.Tuple
import java.util.*

data class EveryAnswerRetrievePageNationResponses(val answerInfos: List<EveryAnswerRetrievePageNationResponse>) {

    companion object {
        fun fromAnswerInfos(answerTuples: List<Tuple>): EveryAnswerRetrievePageNationResponses {
            var answerInfos = ArrayList<EveryAnswerRetrievePageNationResponse>()
            for (answerTuple in answerTuples) {
                val answerId = answerTuple.get(0, Long::class.java)
                val likeCount = answerTuple.get(1, Long::class.java)
                val answerContent = answerTuple.get(2, String::class.java)
                answerInfos.add(EveryAnswerRetrievePageNationResponse(answerId!!, likeCount!!, answerContent!!))
            }

            return EveryAnswerRetrievePageNationResponses(Collections.unmodifiableList(answerInfos))
        }
    }
}