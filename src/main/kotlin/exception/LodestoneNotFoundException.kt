package cloud.drakon.ktlodestone.exception

/**
 * Thrown when a requested resource isn't found on *The Lodestone*.
 */
class LodestoneNotFoundException(
    selection: String,
    id: Int,
) : Throwable("The requested $selection `$id` was not found on The Lodestone.")
