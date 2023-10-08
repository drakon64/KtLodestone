package cloud.drakon.ktlodestone.character

/** A [Character]'s Gender. */
enum class Gender {
    /** Male */
    MALE {
        override fun toString() = "Male"
    },

    /** Female */
    FEMALE {
        override fun toString() = "Female"
    },
}
