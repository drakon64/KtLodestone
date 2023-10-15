package cloud.drakon.ktlodestone.cwls.search

enum class CharacterCount {
    /** 1-10 */
    ONE_TO_TEN {
        override fun toString() = "1-10"
    },

    /** 11-30 */
    ELEVEN_TO_THIRTY {
        override fun toString() = "11-30"
    },

    /** 31-50 */
    THIRTY_ONE_TO_FIFTY {
        override fun toString() = "31-50"
    },

    /** Over 51 */
    OVER_FIFTY_ONE {
        override fun toString() = "51-"
    },
}
