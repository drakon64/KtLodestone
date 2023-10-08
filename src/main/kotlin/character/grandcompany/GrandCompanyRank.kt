package cloud.drakon.ktlodestone.character.grandcompany

enum class GrandCompanyRank {
    /** Private Third Class */
    PRIVATE_THIRD_CLASS {
        override fun toString() = "Private Third Class"
    },

    /** Private Second Class */
    PRIVATE_SECOND_CLASS {
        override fun toString() = "Private Second Class"
    },

    /** Private First Class */
    PRIVATE_FIRST_CLASS {
        override fun toString() = "Private First Class"
    },

    /** Corporal */
    CORPORAL {
        override fun toString() = "Corporal"
    },

    /** Sergeant Third Class */
    SERGEANT_THIRD_CLASS {
        override fun toString() = "Sergeant Third Class"
    },

    /** Sergeant Second Class */
    SERGEANT_SECOND_CLASS {
        override fun toString() = "Sergeant Second Class"
    },

    /** Sergeant First Class */
    SERGEANT_FIRST_CLASS {
        override fun toString() = "Sergeant First Class"
    },

    /** Chief Sergeant */
    CHIEF_SERGEANT {
        override fun toString() = "Chief Sergeant"
    },

    /** Second Lieutenant */
    SECOND_LIEUTENANT {
        override fun toString() = "Second Lieutenant"
    },

    /** First Lieutenant */
    FIRST_LIEUTENANT {
        override fun toString() = "First Lieutenant"
    },

    /** Captain */
    CAPTAIN {
        override fun toString() = "Captain"
    },
}
