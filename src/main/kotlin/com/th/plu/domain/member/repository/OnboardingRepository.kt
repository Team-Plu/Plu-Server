package com.th.plu.domain.member.repository

import com.th.plu.domain.member.Onboarding
import org.springframework.data.jpa.repository.JpaRepository

interface OnboardingRepository: JpaRepository<Onboarding, Long>, OnboardingRepositoryCustom {

}
