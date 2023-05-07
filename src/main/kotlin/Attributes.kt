package cloud.drakon.ktlodestone

import cloud.drakon.ktlodestone.exception.CharacterNotFoundException
import cloud.drakon.ktlodestone.exception.LodestoneException
import cloud.drakon.ktlodestone.profile.attributes.ProfileAttributes
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jsoup.nodes.Document

object Attributes {
    private val lodestoneCssSelectors = Json.parseToJsonElement(
        this::class.java.classLoader.getResource("lodestone-css-selectors/profile/attributes.json") !!
            .readText()
    )

    /**
     * Gets the attributes of character from The Lodestone. This is equivalent to what is returned by The Lodestone's `/attributes` endpoint for a character.
     * @param id The Lodestone character ID.
     * @throws CharacterNotFoundException Thrown when a character could not be found on The Lodestone.
     * @throws LodestoneException Thrown when The Lodestone returns an unknown error.
     */
    suspend fun getAttributes(id: Int) = coroutineScope {
        val character = getLodestoneProfile(id)

        val strength = async {
            getAttributeCss(character, "STRENGTH").toShort()
        }
        val dexterity = async {
            getAttributeCss(character, "DEXTERITY").toShort()
        }
        val vitality = async {
            getAttributeCss(character, "VITALITY").toShort()
        }
        val intelligence = async {
            getAttributeCss(character, "INTELLIGENCE").toShort()
        }
        val mind = async {
            getAttributeCss(character, "MIND").toShort()
        }

        val criticalHitRate = async {
            getAttributeCss(character, "CRITICAL_HIT_RATE").toShort()
        }
        val determination = async {
            getAttributeCss(character, "DETERMINATION").toShort()
        }
        val directHitRate = async {
            getAttributeCss(character, "DIRECT_HIT_RATE").toShort()
        }

        val defense = async {
            getAttributeCss(character, "DEFENSE").toShort()
        }
        val magicDefense = async {
            getAttributeCss(character, "MAGIC_DEFENSE").toShort()
        }

        val attackPower = async {
            getAttributeCss(character, "ATTACK_POWER").toShort()
        }
        val skillSpeed = async {
            getAttributeCss(character, "SKILL_SPEED").toShort()
        }

        val attackMagicPotency = async {
            getAttributeCss(character, "ATTACK_MAGIC_POTENCY").toShort()
        }
        val healingMagicPotency = async {
            getAttributeCss(character, "HEALING_MAGIC_POTENCY").toShort()
        }
        val spellSpeed = async {
            getAttributeCss(character, "SPELL_SPEED").toShort()
        }

        val tenacity = async {
            getAttributeCss(character, "TENACITY").toShort()
        }
        val piety = async {
            getAttributeCss(character, "PIETY").toShort()
        }

        val hp = async {
            getAttributeCss(character, "HP").toInt()
        }
        val mp = async {
            getAttributeCss(character, "MP_GP_CP").toInt()
        }

        ProfileAttributes(
            strength.await(),
            dexterity.await(),
            vitality.await(),
            intelligence.await(),
            mind.await(),
            criticalHitRate.await(),
            determination.await(),
            directHitRate.await(),
            defense.await(),
            magicDefense.await(),
            attackPower.await(),
            skillSpeed.await(),
            attackMagicPotency.await(),
            healingMagicPotency.await(),
            spellSpeed.await(),
            tenacity.await(),
            piety.await(),
            hp.await(),
            mp.await()
        )
    }

    private suspend fun getAttributeCss(
        character: Document,
        lodestoneProperty: String,
    ) = coroutineScope {
        character.select(lodestoneCssSelectors.jsonObject[lodestoneProperty] !!.jsonObject["selector"] !!.jsonPrimitive.content)
            .first() !!
            .text()
    }
}
