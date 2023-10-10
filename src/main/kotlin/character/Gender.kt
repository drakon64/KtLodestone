package cloud.drakon.ktlodestone.character

/** A [Character]'s Gender. */
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
