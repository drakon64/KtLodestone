package cloud.drakon.ktlodestone.character.profile.grandcompany

import org.jetbrains.annotations.ApiStatus.Internal

enum class GrandCompanyName {
    /** Immortal Flames */
    IMMORTAL_FLAMES {
        override fun toString() = "Immortal Flames"
    },

    /** Maelstrom */
    MAELSTROM {
        override fun toString() = "Maelstrom"
    },

    /** Order of the Twin Adder */
    ORDER_OF_THE_TWIN_ADDER {
        override fun toString() = "Order of the Twin Adder"
    },

    /** No Affiliation */
    @Internal
    NO_AFFILIATION {
        override fun toString() = "No Affiliation"
    },
}
