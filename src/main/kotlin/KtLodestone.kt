package cloud.drakon.ktlodestone

import io.ktor.client.HttpClient
import io.ktor.client.engine.java.Java

internal val ktorClient = HttpClient(Java)
