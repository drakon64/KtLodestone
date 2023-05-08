package cloud.drakon.ktlodestone.exception

/**
 * Thrown when less than one page is requested with a `page` parameter.
 */
class PagesLessThanOneException(override val message: String): Throwable(message)
