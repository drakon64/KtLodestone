package cloud.drakon.ktlodestone.character.profile

/** A [CharacterProfile]'s Race. */
enum class Race {
    /** Hyur */
    HYUR {
        override fun toString() = "Hyur"
    },

    /** Elezen */
    ELEZEN {
        override fun toString() = "Elezen"
    },

    /** Lalafell */
    LALAFELL {
        override fun toString() = "Lalafell"
    },

    /** Miqo'te */
    MIQOTE {
        override fun toString() = "Miqo'te"
    },

    /** Roegadyn */
    ROEGADYN {
        override fun toString() = "Roegadyn"
    },

    /** Au Ra */
    AU_RA {
        override fun toString() = "Au Ra"
    },

    /** Hrothgar */
    HROTHGAR {
        override fun toString() = "Hrothgar"
    },

    /** Viera */
    VIERA {
        override fun toString() = "Viera"
    },
}
