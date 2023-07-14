package cloud.drakon.ktlodestone.profile

import cloud.drakon.ktlodestone.profile.gearset.Gear
import cloud.drakon.ktlodestone.profile.gearset.Glamour
import cloud.drakon.ktlodestone.profile.gearset.ProfileGearSet
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jsoup.nodes.Document

internal object GearSet {
    private val lodestoneCssSelectors = Json.parseToJsonElement(
        this::class.java.classLoader.getResource("lodestone-css-selectors/profile/gearset.json") !!
            .readText()
    )

    suspend fun getGearSet(id: Int) = coroutineScope {
        val character = Profile.getLodestoneProfile(id)

        val mainHand = async {
            getGearSetCss(character, "MAINHAND") !! // Cannot be null
        }
        val offHand = async { getGearSetCss(character, "OFFHAND") }
        val head = async { getGearSetCss(character, "HEAD") }
        val body = async { getGearSetCss(character, "BODY") }
        val hands = async { getGearSetCss(character, "HANDS") }
        val legs = async { getGearSetCss(character, "LEGS") }
        val feet = async { getGearSetCss(character, "FEET") }
        val earrings = async { getGearSetCss(character, "EARRINGS") }
        val necklace = async { getGearSetCss(character, "NECKLACE") }
        val bracelets = async { getGearSetCss(character, "BRACELETS") }
        val ring1 = async { getGearSetCss(character, "RING1") }
        val ring2 = async { getGearSetCss(character, "RING2") }
        val ring = async {
            if (ring1.await() != null || ring2.await() != null) {
                listOf(ring1.await(), ring2.await())
            } else {
                null
            }
        }
        val soulCrystal = async { getSoulCrystalCss(character) }

        return@coroutineScope ProfileGearSet(
            mainHand.await(),
            offHand.await(),
            head.await(),
            body.await(),
            hands.await(),
            legs.await(),
            feet.await(),
            earrings.await(),
            necklace.await(),
            bracelets.await(),
            ring.await(),
            soulCrystal.await()
        )
    }

    private suspend fun getGearSetCss(character: Document, gear: String) =
        coroutineScope {
            val css = lodestoneCssSelectors.jsonObject[gear] !!

            val name = async {
                character.select(css.jsonObject["NAME"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                    .first()
                    ?.text()
            }
            val dbLink = async {
                val link =
                    character.select(css.jsonObject["DB_LINK"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                        .first()
                        ?.attr("href")

                if (link != null) {
                    "https://eu.finalfantasyxiv.com${link}"
                } else {
                    null
                }
            }

            val glamour = async { getGlamour(character, css) }

            val stain = async {
                val stain =
                    character.select(css.jsonObject["STAIN"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                        .first()
                        ?.text()

                if (stain.isNullOrBlank()) {
                    null
                } else {
                    stain
                }
            }

            val materia = async { getMateriaCss(character, css) }

            val creatorName = async {
                character.select(css.jsonObject["CREATOR_NAME"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                    .first()
                    ?.text()
            }
            val hq = async {
                if (name.await() != null) {
                    name.await() !!.endsWith("\uE03C") // HQ symbol
                } else {
                    null
                }
            }

            return@coroutineScope if (name.await() != null) {
                Gear(
                    name.await() !!.replace("\uE03C", ""), // Strip the HQ symbol
                    dbLink.await() !!,
                    glamour.await(),
                    stain.await(),
                    materia.await(),
                    creatorName.await(),
                    hq.await() !!
                )
            } else {
                null
            }
        }

    private suspend fun getGlamour(character: Document, css: JsonElement) =
        coroutineScope {
            val mirageName = async {
                character.select(css.jsonObject["MIRAGE_NAME"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                    .first()
                    ?.text()
            }
            val mirageDbLink = async {
                val link =
                    character.select(css.jsonObject["MIRAGE_DB_LINK"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
                        .first()
                        ?.attr("href")

                if (link != null) {
                    "https://eu.finalfantasyxiv.com${link}"
                } else {
                    null
                }
            }

            if (mirageName.await() != null && mirageDbLink.await() != null) {
                Glamour(
                    mirageName.await() !!, mirageDbLink.await() !!
                )
            } else {
                null
            }
        }

    private val materiaRegex = """.*(?=<br>)""".toRegex()

    private suspend fun getMateriaCss(character: Document, css: JsonElement) =
        coroutineScope {
            val materiaList = mutableListOf<String>()

            for (i in 1 .. 5) {
                val materia = character.select(
                    css.jsonObject["MATERIA_${i}"] !!.jsonObject["selector"] !!.jsonPrimitive.content
                ).first()?.html()

                if (materia != null) {
                    materiaList.add(materiaRegex.find(materia) !!.value)
                }
            }

            if (materiaList.isNotEmpty()) {
                materiaList.toList()
            } else {
                null
            }
        }

    private suspend fun getSoulCrystalCss(character: Document) = coroutineScope {
        return@coroutineScope character.select(lodestoneCssSelectors.jsonObject["SOULCRYSTAL"] !!.jsonObject["NAME"] !!.jsonObject["selector"] !!.jsonPrimitive.content)
            .first()
            ?.text()
    }
}
