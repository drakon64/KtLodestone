package cloud.drakon.ktlodestone.freecompany.search

enum class Housing {
    /** Estate Built */
    ESTATE_BUILT {
        override fun toString() = "Estate Built"
    },

    /** Plot Only */
    PLOT_ONLY {
        override fun toString() = "Plot Only"
    },

    /** No Estate or Plot */
    NO_ESTATE_OR_PLOT {
        override fun toString() = "No Estate or Plot"
    },
}
