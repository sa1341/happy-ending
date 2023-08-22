package com.kakaopaysec.happyending.mysql.support

import com.kakaopaysec.happyending.mysql.configuration.DatabaseConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@Transactional
@ActiveProfiles("test")
@SpringBootTest(classes = [DatabaseConfiguration::class])
class DataJpaTestSupport
