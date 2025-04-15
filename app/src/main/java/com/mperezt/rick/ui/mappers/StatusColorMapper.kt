package com.mperezt.rick.ui.mappers

import androidx.compose.ui.graphics.Color
import com.mperezt.rick.domain.models.Status
import com.mperezt.rick.ui.theme.AliveGreen
import com.mperezt.rick.ui.theme.DeadRed
import com.mperezt.rick.ui.theme.UnknownStatus

object StatusColorMapper {
    private fun mapToColor(status: Status): Color {
        return when (status) {
            Status.Alive -> AliveGreen
            Status.Dead -> DeadRed
            Status.Unknown -> UnknownStatus
        }
    }

    fun mapToColor(statusString: String): Color {
        val status = Status.entries.find { it.value == statusString.lowercase() } ?: Status.Unknown
        return mapToColor(status)
    }
}