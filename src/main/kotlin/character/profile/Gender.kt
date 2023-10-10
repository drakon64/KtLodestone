package cloud.drakon.ktlodestone.character.profile

/** A [CharacterProfile]'s Gender. */
enum class Gender {
    /** ♀ */
    FEMALE {
        override fun toString() = "♀"
    },

    /** ♂ */
    MALE {
        override fun toString() = "♂"
    },
}
