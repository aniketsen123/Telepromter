package com.seventhelement.teleprompter.widget

import java.util.Calendar

object TimeUtil {
    fun timeAgo(pastDate: Long): String? {
        val current = Calendar.getInstance()
        val diff = current.timeInMillis - pastDate
        val diffSeconds = diff / 1000
        val diffMinutes = diff / (60 * 1000) % 60
        val diffHours = diff / (60 * 60 * 1000) % 24
        val diffDays = diff / (24 * 60 * 60 * 1000)
        var time: String? = null
        if (diffDays > 0) {
            time = if (diffDays == 1L) {
                diffDays.toString() + "day ago "
            } else {
                diffDays.toString() + "days ago "
            }
        } else {
            if (diffHours > 0) {
                time = if (diffHours == 1L) {
                    diffHours.toString() + "hr ago"
                } else {
                    diffHours.toString() + "hrs ago"
                }
            } else {
                if (diffMinutes > 0) {
                    time = if (diffMinutes == 1L) {
                        diffMinutes.toString() + "min ago"
                    } else {
                        diffMinutes.toString() + "mins ago"
                    }
                } else {
                    if (diffSeconds > 0) {
                        time = diffSeconds.toString() + "secs ago"
                    }
                }
            }
        }
        return time
    }
}
