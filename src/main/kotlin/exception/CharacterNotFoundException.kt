package cloud.drakon.ktlodestone.exception

/**
 * Thrown when a character could not be found on The Lodestone.
 */
class CharacterNotFoundException(override val message: String): Throwable(message)
