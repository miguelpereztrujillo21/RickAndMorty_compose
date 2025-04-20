package com.mperezt.rick.ui.mappers

import androidx.compose.ui.graphics.Color
import com.mperezt.rick.ui.models.GenderUi
import com.mperezt.rick.ui.theme.FemaleColor
import com.mperezt.rick.ui.theme.GenderlessColor
import com.mperezt.rick.ui.theme.MaleColor
import com.mperezt.rick.ui.theme.UnknownGenderColor

object GenderColorMapper {
    fun mapToColor(gender: GenderUi): Color {
        return when (gender) {
            GenderUi.Female -> FemaleColor
            GenderUi.Male -> MaleColor
            GenderUi.Genderless -> GenderlessColor
            GenderUi.Unknown -> UnknownGenderColor
        }
    }

    fun mapToColor(genderString: String): Color {
        val gender = GenderUi.entries.find { it.value == genderString.lowercase() } ?: GenderUi.Unknown
        return mapToColor(gender)
    }
}