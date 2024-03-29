package cloud.drakon.ktlodestone.character

/** A Character's active Discipline (DoW/DoM/DoH/DoL). */
enum class Discipline {
    /** Disciple of War */
    DISCIPLE_OF_WAR {
        override fun toString() = "Disciple of War"
    },

    /** Disciple of Magic */
    DISCIPLE_OF_MAGIC {
        override fun toString() = "Disciple of Magic"
    },

    /** Disciple of the Land */
    DISCIPLE_OF_THE_LAND {
        override fun toString() = "Disciple of the Land"
    },

    /** Disciple of the Hand */
    DISCIPLE_OF_THE_HAND {
        override fun toString() = "Disciple of the Hand"
    },
}
