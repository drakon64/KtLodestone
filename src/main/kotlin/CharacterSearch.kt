@file:JvmName("KtLodestone")
@file:JvmMultifileClass
@file:OptIn(DelicateCoroutinesApi::class)

package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.character.classjob.ClassJob
import cloud.drakon.ktlodestone.character.profile.Clan
import cloud.drakon.ktlodestone.character.profile.Race
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompanyName
import cloud.drakon.ktlodestone.character.search.Language
import cloud.drakon.ktlodestone.character.search.scrapeCharacterSearch
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.World
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future

/**
 * Searches for a character on *The Lodestone*.
 *
 * @throws LodestoneException Thrown when *The Lodestone* returns an unknown error.
 */
suspend fun searchLodestoneCharacter(
    name: String? = null,
    world: World? = null,
    dataCenter: DataCenter? = null,
    classJob: ClassJob? = null,
    race: Race? = null,
    clan: Clan? = null,
    grandCompanies: List<GrandCompanyName>? = null,
    languages: List<Language>? = null,
    pages: Byte = 1,
) = if (pages == 1.toByte()) {
    searchLodestoneCharacterPaginated(
        name,
        world,
        dataCenter,
        classJob,
        race,
        clan,
        grandCompanies,
        languages
    )
} else {
    buildList {
        for (page in 1..pages) {
            add(
                searchLodestoneCharacterPaginated(
                    name,
                    world,
                    dataCenter,
                    classJob,
                    race,
                    clan,
                    grandCompanies,
                    languages,
                    page
                )
            )
        }
    }.flatten()
}

private suspend fun searchLodestoneCharacterPaginated(
    name: String?,
    world: World?,
    dataCenter: DataCenter?,
    classJob: ClassJob?,
    race: Race?,
    clan: Clan?,
    grandCompanies: List<GrandCompanyName>?,
    languages: List<Language>?,
    page: Int? = null,
) = ktorClient.get("character/") {
    url {
        if (name != null) parameters.append("q", name.replace(" ", "+"))

        // If both [world] and [dataCenter] or just [world] are provided, use [world] as it's more specific, otherwise use [dataCenter]
        if ((world != null && dataCenter != null) || world != null) {
            parameters.append("worldname", world.name)
        } else if (dataCenter != null) {
            parameters.append("worldname", dataCenter.name)
        }

        if (classJob != null) parameters.append("classjob", classJob.name)

        // If both [clan] and [race] or just [clan] are provided, use [clan] as it's more specific, otherwise use [race]
        if ((clan != null && race != null) || clan != null) {
            parameters.append("race_tribe", clan.name)
        } else if (race != null) {
            parameters.append("race_tribe", race.name)
        }

        grandCompanies?.forEach {
            when (it) {
                GrandCompanyName.MAELSTROM -> parameters.append("gcid", "1")
                GrandCompanyName.ORDER_OF_THE_TWIN_ADDER -> parameters.append("gcid", "2")
                GrandCompanyName.IMMORTAL_FLAMES -> parameters.append("gcid", "3")
                GrandCompanyName.NO_AFFILIATION -> parameters.append("gcid", "0")
            }
        }

        languages?.forEach {
            when (it) {
                Language.JAPANESE -> parameters.append("blog_lang", "ja")
                Language.ENGLISH -> parameters.append("blog_lang", "en")
                Language.GERMAN -> parameters.append("blog_lang", "de")
                Language.FRENCH -> parameters.append("blog_lang", "fr")
            }
        }

        if (page != null) parameters.append("page", "$page")
    }
}.let {
    when (it.status.value) {
        200 -> scrapeCharacterSearch(it.body())
        else -> throw LodestoneException()
    }
}

/**
 * Searches for a character on *The Lodestone*.
 *
 * @throws LodestoneException Thrown when *The Lodestone* returns an unknown error.
 */
@JvmName("searchLodestoneCharacter")
@JvmOverloads
@Throws(LodestoneException::class)
fun searchLodestoneCharacterAsync(
    name: String? = null,
    world: World? = null,
    dataCenter: DataCenter? = null,
    classJob: ClassJob? = null,
    race: Race? = null,
    clan: Clan? = null,
    grandCompanies: List<GrandCompanyName>? = null,
    languages: List<Language>? = null,
    pages: Byte = 1,
) = GlobalScope.future {
    searchLodestoneCharacter(
        name,
        world,
        dataCenter,
        classJob,
        race,
        clan,
        grandCompanies,
        languages,
        pages,
    )
}
