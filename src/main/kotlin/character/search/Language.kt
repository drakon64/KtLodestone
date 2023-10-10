package cloud.drakon.ktlodestone.character.search

enum class Language {
    /** Japanese */
    JAPANESE {
        override fun toString() = "Japanese"
    },

    /** English */
    ENGLISH {
        override fun toString() = "English"
    },

    /** German */
    GERMAN {
        override fun toString() = "German"
    },

    /** French */
    FRENCH {
        override fun toString() = "French"
    },
}
