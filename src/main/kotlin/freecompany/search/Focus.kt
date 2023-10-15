package cloud.drakon.ktlodestone.freecompany.search

enum class Focus {
    /** Role-playing */
    ROLE_PLAYING {
        override fun toString() = "Role-playing"
    },

    /** Leveling */
    LEVELING {
        override fun toString() = "Levelling"
    },

    /** Casual */
    CASUAL {
        override fun toString() = "Casual"
    },

    /** Hardcore */
    HARDCORE {
        override fun toString() = "Hardcore"
    },

    /** Dungeons */
    DUNGEONS {
        override fun toString() = "Dungeons"
    },

    /** Guildhests */
    GUILDHESTS {
        override fun toString() = "Guildhests"
    },

    /** Trials */
    TRIALS {
        override fun toString() = "Trials"
    },

    /** Raids */
    RAIDS {
        override fun toString() = "Raids"
    },

    /** PvP */
    PVP {
        override fun toString() = "PvP"
    },
}
