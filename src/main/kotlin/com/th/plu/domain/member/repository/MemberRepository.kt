package com.th.plu.domain.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import java.lang.reflect.Member

interface MemberRepository: JpaRepository<Member, Long>, MemberRepositoryCustom {
}
