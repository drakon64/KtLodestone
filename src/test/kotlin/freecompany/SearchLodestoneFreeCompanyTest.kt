package freecompany

import cloud.drakon.ktlodestone.searchLodestoneFreeCompany
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class SearchLodestoneFreeCompanyTest {
    @Test
    fun searchLodestoneFreeCompanyTest() = assertDoesNotThrow {
        runBlocking {
            println(searchLodestoneFreeCompany())
        }
    }
}
