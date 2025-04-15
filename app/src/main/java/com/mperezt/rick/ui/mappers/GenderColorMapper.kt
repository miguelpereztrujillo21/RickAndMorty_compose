package com.mperezt.rick.ui.mappers

import androidx.compose.ui.graphics.Color
import com.mperezt.rick.domain.models.Gender
import com.mperezt.rick.ui.theme.FemaleColor
import com.mperezt.rick.ui.theme.GenderlessColor
import com.mperezt.rick.ui.theme.MaleColor
import com.mperezt.rick.ui.theme.UnknownGenderColor

object GenderColorMapper {
    fun mapToColor(gender: Gender): Color {
        return when (gender) {
            Gender.Female -> FemaleColor
            Gender.Male -> MaleColor
            Gender.Genderless -> GenderlessColor
            Gender.Unknown -> UnknownGenderColor
        }
    }

    fun mapToColor(genderString: String): Color {
        val gender = Gender.entries.find { it.value == genderString.lowercase() } ?: Gender.Unknown
        return mapToColor(gender)
    }
}