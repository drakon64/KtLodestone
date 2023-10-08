package cloud.drakon.ktlodestone.character

/** A [Character]'s Guardian. */
enum class Guardian {
    /** Halone, the Fury */
    HALONE {
        override fun toString() = "Halone, the Fury"
    },

    /** Menphina, the Lover */
    MENPHINA {
        override fun toString() = "Menphina, the Lover"
    },

    /** Thaliak, the Scholar */
    THALIAK {
        override fun toString() = "Thaliak, the Scholar"
    },

    /** Nymeia, the Spinner */
    NYMEIA {
        override fun toString() = "Nymeia, the Spinner"
    },

    /** Llymlaen, the Navigator */
    LLYMLAEN {
        override fun toString() = "Llymlaen, the Navigator"
    },

    /** Oschon, the Wanderer */
    OSCHON {
        override fun toString() = "Oschon, the Wanderer"
    },

    /** Byregot, the Builder */
    BYREGOT {
        override fun toString() = "Byregot, the Builder"
    },

    /** Rhalgr, the Destroyer */
    RHALGR {
        override fun toString() = "Rhalgr, the Destroyer"
    },

    /** Azeyma, the Warden */
    AZEYMA {
        override fun toString() = "Azeyma, the Warden"
    },

    /** Nald'thal, the Traders */
    NALDTHAL {
        override fun toString() = "Nald'thal, the Traders"
    },

    /** Nophica, the Matron */
    NOPHICA {
        override fun toString() = "Nophica, the Matron"
    },

    /** Althyk, the Keeper */
    ALTHYK {
        override fun toString() = "Althyk, the Keeper"
    },
}
