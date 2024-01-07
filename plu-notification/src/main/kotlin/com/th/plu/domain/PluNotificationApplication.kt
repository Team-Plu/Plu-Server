package com.th.plu.domain

import com.th.plu.common.PluCommonRoot
import com.th.plu.external.PluExternalRoot
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackageClasses = [
	PluNotificationApplication::class,
	PluCommonRoot::class,
	PluExternalRoot::class
])
class PluNotificationApplication

fun main(args: Array<String>) {
	runApplication<PluNotificationApplication>(*args)
}
