package com.th.plu.domain.domain.member.repository

import com.th.plu.domain.domain.member.Setting
import org.springframework.data.jpa.repository.JpaRepository

interface SettingRepository : JpaRepository<Setting, Long> {
}
