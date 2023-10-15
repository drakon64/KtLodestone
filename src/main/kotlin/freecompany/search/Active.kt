package cloud.drakon.ktlodestone.freecompany.search

enum class Active {
    /** Weekdays Only */
    WEEKDAYS_ONLY {
        override fun toString() = "Weekdays Only"
    },

    /** Weekends Only */
    WEEKENDS_ONLY {
        override fun toString() = "Weekends Only"
    },

    /** Always */
    ALWAYS {
        override fun toString() = "Always"
    },
}
