package com.th.plu.domain.domain.like

import com.th.plu.domain.domain.answer.Answer
import com.th.plu.domain.domain.common.BaseEntity
import com.th.plu.domain.domain.member.Member
import com.th.plu.domain.domain.question.Question
import jakarta.persistence.*

@Table(name = "likes")
@Entity
class Like(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "like_id")
        var id: Long? = null,

        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "member_id", nullable = false)
        var member: Member,

        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "answer_id", nullable = false)
        var answer: Answer,

        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "question_id", nullable = false)
        var question: Question

) : BaseEntity() {

    companion object {
        fun newInstance(
                member: Member, answer: Answer, question: Question
        ): Like {
            return Like(
                    member = member,
                    answer = answer,
                    question = question
            )
        }
    }
}