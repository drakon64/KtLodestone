package cloud.drakon.ktlodestone.character.profile

/** A [CharacterProfile]'s starting Town. */
enum class Town {
    /** Gridania */
    GRIDANIA {
        override fun toString() = "Gridania"
    },

    /** Limsa Lominsa */
    LIMSA_LOMINSA {
        override fun toString() = "Limsa Lominsa"
    },

    /** Ul'dah */
    ULDAH {
        override fun toString() = "Ul'dah"
    },
}
