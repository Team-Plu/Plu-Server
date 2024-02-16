package com.th.plu.domain.domain.member

import com.th.plu.domain.domain.common.BaseEntity
import jakarta.persistence.*

@Table(name = "settings")
@Entity
class Setting(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "setting_id")
    var id: Long? = null,

    @Column(name = "notification_status", nullable = false)
    var notificationStatus: Boolean

) : BaseEntity() {

    companion object {
        fun newInstance(): Setting {
            return Setting(
                id = null,
                notificationStatus = false
            )
        }
    }
}
