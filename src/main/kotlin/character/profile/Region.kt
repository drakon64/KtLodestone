package cloud.drakon.ktlodestone.character.profile

/** A [CharacterProfile]'s Region. */
enum class Region {
    /** Europe */
    EUROPE {
        override fun toString() = "Europe"
    },

    /** Oceania */
    OCEANIA {
        override fun toString() = "Oceania"
    },

    /** North America */
    NORTH_AMERICA {
        override fun toString() = "North America"
    },

    /** Japan */
    JAPAN {
        override fun toString() = "Japan"
    },
}