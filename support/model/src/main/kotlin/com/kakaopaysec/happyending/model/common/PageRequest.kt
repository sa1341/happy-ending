package com.kakaopaysec.happyending.model.common
data class PageRequest(
    val page: Int,
    val size: Int,
) {
    companion object {
        fun of(page: Int, size: Int) =
            PageRequest(if (page > 0) page - 1 else 0, size)
    }
}
