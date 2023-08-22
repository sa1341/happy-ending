package com.kakaopaysec.happyending.utils

import java.util.UUID

fun UUID.toStringRemoveDashes() = this.toString().replace("-".toRegex(), "")
fun UUID.nodeGroup() = this.toString().substringAfterLast("-")
