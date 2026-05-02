package com.biprangshu.subtracker.core.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class User(
    val id: Int = 0,
    val name: String? = null,
    val email: String? = null,
    val preferredCurrency: String = "$",
    val budget: Double
)
