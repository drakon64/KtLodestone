package cloud.drakon.ktlodestone.character

/** A [Character]'s Clan. */
enum class Clan {
    /** Hyur, Midlander */
    MIDLANDER {
        override fun toString() = "Midlander"
    },

    /** Hyur, Highlander */
    HIGHLANDER {
        override fun toString() = "Highlander"
    },

    /** Elezen, Wildwood */
    WILDWOOD {
        override fun toString() = "Wildwood"
    },

    /** Elezen, Duskwight */
    DUSKWIGHT {
        override fun toString() = "Duskwight"
    },

    /** Lalafell, Plainsfolk */
    PLAINSFOLK {
        override fun toString() = "Plainsfolk"
    },

    /** Lalafell, Dunesfolk */
    DUNESFOLK {
        override fun toString() = "Dunesfolk"
    },

    /** Miqo'te, Seeker of the Sun */
    SEEKER_OF_THE_SUN {
        override fun toString() = "Seeker of the Sun"
    },

    /** Miqo'te, Keeper of the Moon */
    KEEPER_OF_THE_MOON {
        override fun toString() = "Keeper of the Moon"
    },

    /** Roegadyn, Sea Wolf */
    SEA_WOLF {
        override fun toString() = "Sea Wolf"
    },

    /** Roegadyn, Hellsguard */
    HELLSGUARD {
        override fun toString() = "Hellsguard"
    },

    /** Au Ra, Raen */
    RAEN {
        override fun toString() = "Raen"
    },

    /** Au Ra, Xaela */
    XAELA {
        override fun toString() = "Xaela"
    },

    /** Hrothgar, Helions */
    HELIONS {
        override fun toString() = "Helions"
    },

    /** Hrothgar, The Lost */
    THE_LOST {
        override fun toString() = "The Lost"
    },

    /** Viera, Rava */
    RAVA {
        override fun toString() = "Rava"
    },

    /** Viera, Veena */
    VEENA {
        override fun toString() = "Veena"
    },
}
