package cloud.drakon.ktlodestone.freecompany.search

enum class Recruitment {
    /** Open */
    OPEN {
        override fun toString() = "Open"
    },

    /** Closed */
    CLOSED {
        override fun toString() = "Closed"
    },
}
