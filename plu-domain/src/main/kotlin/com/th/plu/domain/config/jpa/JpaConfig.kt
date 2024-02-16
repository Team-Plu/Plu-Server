package com.th.plu.domain.config.jpa

import com.th.plu.domain.PluDomainRoot
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackageClasses = [PluDomainRoot::class])
@EnableJpaRepositories(basePackageClasses = [PluDomainRoot::class])
@EnableJpaAuditing
class JpaConfig
