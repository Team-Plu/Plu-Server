package com.th.plu.domain.member

import com.th.plu.domain.common.BaseEntity
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor
import java.time.LocalDateTime

@Table(name = "settings")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
class Setting(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "setting_id")
    private var id: Long? = null,

    @Column(name = "notification_status", nullable = false)
    private var notificationStatus: Boolean,

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = [CascadeType.ALL])
    @JoinColumn(name = "member_id", nullable = false)
    private var member: Member

) : BaseEntity(LocalDateTime.now(), LocalDateTime.now()) {
}
