package cloud.drakon.ktlodestone.exception

/**
 * Thrown when The Lodestone returns an unknown error.
 */
class LodestoneException(override val message: String = "The Lodestone returned an unknown error."):
    Throwable(message)
