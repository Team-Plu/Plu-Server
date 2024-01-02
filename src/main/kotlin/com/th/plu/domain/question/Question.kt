package com.th.plu.domain.question

import com.th.plu.domain.answer.Answer
import com.th.plu.domain.common.BaseEntity
import jakarta.persistence.*


@Table(name = "questions")
@Entity
class Question(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    val id: Long? = null,

    @Column(name = "element_type", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    var elementType: ElementType,

    @Column(name = "question_title", nullable = false, length = 100)
    var title: String,

    @Column(name = "question_content", nullable = false, length = 300)
    var content: String,

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var answers: List<Answer>

) : BaseEntity() {
}
