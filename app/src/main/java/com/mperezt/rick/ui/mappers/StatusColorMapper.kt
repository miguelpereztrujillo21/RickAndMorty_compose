package com.mperezt.rick.ui.mappers

import androidx.compose.ui.graphics.Color
import com.mperezt.rick.ui.models.StatusUi
import com.mperezt.rick.ui.theme.AliveGreen
import com.mperezt.rick.ui.theme.DeadRed
import com.mperezt.rick.ui.theme.UnknownStatus

object StatusColorMapper {
    private fun mapToColor(status: StatusUi): Color {
        return when (status) {
            StatusUi.Alive -> AliveGreen
            StatusUi.Dead -> DeadRed
            StatusUi.Unknown -> UnknownStatus
        }
    }

    fun mapToColor(statusString: String): Color {
        val status = StatusUi.entries.find { it.value == statusString.lowercase() } ?: StatusUi.Unknown
        return mapToColor(status)
    }
}