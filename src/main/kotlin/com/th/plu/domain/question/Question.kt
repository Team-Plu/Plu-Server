package com.th.plu.domain.question

import com.th.plu.domain.answer.Answer
import com.th.plu.domain.common.BaseEntity
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor
import java.time.LocalDateTime


@Table(name = "questions")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
class Question(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private var id: Long? = null,

    @Column(name = "element_type", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private var elementType: ElementType,

    @Column(name = "question_title", nullable = false, length = 100)
    private var title: String,

    @Column(name = "question_content", nullable = false, length = 300)
    private var content: String,

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    private var answers: List<Answer>

) : BaseEntity(LocalDateTime.now(), LocalDateTime.now()) {
}
