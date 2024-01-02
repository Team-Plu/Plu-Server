package com.th.plu.domain.member

import com.th.plu.domain.common.BaseEntity
import jakarta.persistence.*

@Table(name = "settings")
@Entity
class Setting(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "setting_id")
    val id: Long? = null,

    @Column(name = "notification_status", nullable = false)
    var notificationStatus: Boolean,

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = [CascadeType.ALL])
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member

) : BaseEntity() {
}
