package com.th.plu.domain.domain.member.repository

import com.th.plu.domain.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>, MemberRepositoryCustom {

}
