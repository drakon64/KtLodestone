package cloud.drakon.ktlodestone.freecompany.search

enum class Seeking {
    /** Tank */
    TANK {
        override fun toString() = "Tank"
    },

    /** Healer */
    HEALER {
        override fun toString() = "Healer"
    },

    /** DPS */
    DPS,

    /** Crafter */
    CRAFTER {
        override fun toString() = "Crafter"
    },

    /** Gatherer */
    GATHERER {
        override fun toString() = "Gatherer"
    },
}
