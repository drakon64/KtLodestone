package cloud.drakon.ktlodestone.character.classjob

import cloud.drakon.ktlodestone.character.ClassJob
import cloud.drakon.ktlodestone.selectors.character.classjob.ClassJobSelectors
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

internal fun scrapeCharacterClassJob(response: String) = Jsoup.parse(response).let {
    val paladin = scrapeClassJob(it, ClassJob.PALADIN)
    val warrior = scrapeClassJob(it, ClassJob.WARRIOR)
    val darkKnight = scrapeClassJob(it, ClassJob.DARK_KNIGHT)
    val gunbreaker = scrapeClassJob(it, ClassJob.GUNBREAKER)

    val whiteMage = scrapeClassJob(it, ClassJob.WHITE_MAGE)
    val scholar = scrapeClassJob(it, ClassJob.SCHOLAR)
    val astrologian = scrapeClassJob(it, ClassJob.ASTROLOGIAN)
    val sage = scrapeClassJob(it, ClassJob.SAGE)

    val monk = scrapeClassJob(it, ClassJob.MONK)
    val dragoon = scrapeClassJob(it, ClassJob.DRAGOON)
    val ninja = scrapeClassJob(it, ClassJob.NINJA)
    val samurai = scrapeClassJob(it, ClassJob.SAMURAI)
    val reaper = scrapeClassJob(it, ClassJob.REAPER)

    val bard = scrapeClassJob(it, ClassJob.BARD)
    val machinist = scrapeClassJob(it, ClassJob.MACHINIST)
    val dancer = scrapeClassJob(it, ClassJob.DANCER)

    val blackMage = scrapeClassJob(it, ClassJob.BLACK_MAGE)
    val summoner = scrapeClassJob(it, ClassJob.SUMMONER)
    val redMage = scrapeClassJob(it, ClassJob.RED_MAGE)
    val blueMage = scrapeClassJob(it, ClassJob.BLUE_MAGE)

    val carpenter = scrapeClassJob(it, ClassJob.CARPENTER)
    val blacksmith = scrapeClassJob(it, ClassJob.BLACKSMITH)
    val armorer = scrapeClassJob(it, ClassJob.ARMORER)
    val goldsmith = scrapeClassJob(it, ClassJob.GOLDSMITH)
    val leatherworker = scrapeClassJob(it, ClassJob.LEATHERWORKER)
    val weaver = scrapeClassJob(it, ClassJob.WEAVER)
    val alchemist = scrapeClassJob(it, ClassJob.ALCHEMIST)
    val culinarian = scrapeClassJob(it, ClassJob.CULINARIAN)

    val miner = scrapeClassJob(it, ClassJob.MINER)
    val botanist = scrapeClassJob(it, ClassJob.BOTANIST)
    val fisher = scrapeClassJob(it, ClassJob.FISHER)

    buildMap {
        arrayOf(
            paladin, warrior, darkKnight, gunbreaker,
            whiteMage, scholar, astrologian, sage,
            monk, dragoon, ninja, samurai, reaper,
            bard, machinist, dancer,
            blackMage, summoner, redMage, blueMage,
            carpenter, blacksmith, armorer, goldsmith, leatherworker, weaver,
            alchemist, culinarian,
            miner, botanist, fisher
        ).forEach {
            it?.let {
                put(it.unlockState, ClassJobLevel(it.level, it.experience))
            }
        }
    }
}

private fun scrapeClassJob(document: Document, job: ClassJob): ClassLevel? {
    val levelSelector: String
    val unlockStateSelector: String
    val expSelector: String

    when (job) {
        ClassJob.GLADIATOR, ClassJob.PALADIN -> {
            levelSelector = ClassJobSelectors.PALADIN_LEVEL
            unlockStateSelector = ClassJobSelectors.PALADIN_UNLOCK_STATE
            expSelector = ClassJobSelectors.PALADIN_EXP
        }

        ClassJob.MARAUDER, ClassJob.WARRIOR -> {
            levelSelector = ClassJobSelectors.WARRIOR_LEVEL
            unlockStateSelector = ClassJobSelectors.WARRIOR_UNLOCK_STATE
            expSelector = ClassJobSelectors.WARRIOR_EXP
        }

        ClassJob.DARK_KNIGHT -> {
            levelSelector = ClassJobSelectors.DARK_KNIGHT_LEVEL
            unlockStateSelector = ClassJobSelectors.DARK_KNIGHT_UNLOCK_STATE
            expSelector = ClassJobSelectors.DARK_KNIGHT_EXP
        }

        ClassJob.GUNBREAKER -> {
            levelSelector = ClassJobSelectors.GUNBREAKER_LEVEL
            unlockStateSelector = ClassJobSelectors.GUNBREAKER_UNLOCK_STATE
            expSelector = ClassJobSelectors.GUNBREAKER_EXP
        }

        ClassJob.CONJURER, ClassJob.WHITE_MAGE -> {
            levelSelector = ClassJobSelectors.WHITE_MAGE_LEVEL
            unlockStateSelector = ClassJobSelectors.WHITE_MAGE_UNLOCK_STATE
            expSelector = ClassJobSelectors.WHITE_MAGE_EXP
        }

        ClassJob.SCHOLAR -> {
            levelSelector = ClassJobSelectors.SCHOLAR_LEVEL
            unlockStateSelector = ClassJobSelectors.SCHOLAR_UNLOCK_STATE
            expSelector = ClassJobSelectors.SCHOLAR_EXP
        }

        ClassJob.ASTROLOGIAN -> {
            levelSelector = ClassJobSelectors.ASTROLOGIAN_LEVEL
            unlockStateSelector = ClassJobSelectors.ASTROLOGIAN_UNLOCK_STATE
            expSelector = ClassJobSelectors.ASTROLOGIAN_EXP
        }

        ClassJob.SAGE -> {
            levelSelector = ClassJobSelectors.SAGE_LEVEL
            unlockStateSelector = ClassJobSelectors.SAGE_UNLOCK_STATE
            expSelector = ClassJobSelectors.SAGE_EXP
        }

        ClassJob.PUGILIST, ClassJob.MONK -> {
            levelSelector = ClassJobSelectors.MONK_LEVEL
            unlockStateSelector = ClassJobSelectors.MONK_UNLOCK_STATE
            expSelector = ClassJobSelectors.MONK_EXP
        }

        ClassJob.LANCER, ClassJob.DRAGOON -> {
            levelSelector = ClassJobSelectors.DRAGOON_LEVEL
            unlockStateSelector = ClassJobSelectors.DRAGOON_UNLOCK_STATE
            expSelector = ClassJobSelectors.DRAGOON_EXP
        }

        ClassJob.ROGUE, ClassJob.NINJA -> {
            levelSelector = ClassJobSelectors.NINJA_LEVEL
            unlockStateSelector = ClassJobSelectors.NINJA_UNLOCK_STATE
            expSelector = ClassJobSelectors.NINJA_EXP
        }

        ClassJob.SAMURAI -> {
            levelSelector = ClassJobSelectors.SAMURAI_LEVEL
            unlockStateSelector = ClassJobSelectors.SAMURAI_UNLOCK_STATE
            expSelector = ClassJobSelectors.SAMURAI_EXP
        }

        ClassJob.REAPER -> {
            levelSelector = ClassJobSelectors.REAPER_LEVEL
            unlockStateSelector = ClassJobSelectors.REAPER_UNLOCK_STATE
            expSelector = ClassJobSelectors.REAPER_EXP
        }

        ClassJob.ARCHER, ClassJob.BARD -> {
            levelSelector = ClassJobSelectors.BARD_LEVEL
            unlockStateSelector = ClassJobSelectors.BARD_UNLOCK_STATE
            expSelector = ClassJobSelectors.BARD_EXP
        }

        ClassJob.MACHINIST -> {
            levelSelector = ClassJobSelectors.MACHINIST_LEVEL
            unlockStateSelector = ClassJobSelectors.MACHINIST_UNLOCK_STATE
            expSelector = ClassJobSelectors.MACHINIST_EXP
        }

        ClassJob.DANCER -> {
            levelSelector = ClassJobSelectors.DANCER_LEVEL
            unlockStateSelector = ClassJobSelectors.DANCER_UNLOCK_STATE
            expSelector = ClassJobSelectors.DANCER_EXP
        }

        ClassJob.THAUMATURGE, ClassJob.BLACK_MAGE -> {
            levelSelector = ClassJobSelectors.BLACK_MAGE_LEVEL
            unlockStateSelector = ClassJobSelectors.BLACK_MAGE_UNLOCK_STATE
            expSelector = ClassJobSelectors.BLACK_MAGE_EXP
        }

        ClassJob.ARCANIST, ClassJob.SUMMONER -> {
            levelSelector = ClassJobSelectors.SUMMONER_LEVEL
            unlockStateSelector = ClassJobSelectors.SUMMONER_UNLOCK_STATE
            expSelector = ClassJobSelectors.SUMMONER_EXP
        }

        ClassJob.RED_MAGE -> {
            levelSelector = ClassJobSelectors.RED_MAGE_LEVEL
            unlockStateSelector = ClassJobSelectors.RED_MAGE_UNLOCK_STATE
            expSelector = ClassJobSelectors.RED_MAGE_EXP
        }

        ClassJob.BLUE_MAGE -> {
            levelSelector = ClassJobSelectors.BLUE_MAGE_LEVEL
            unlockStateSelector = ClassJobSelectors.BLUE_MAGE_UNLOCK_STATE
            expSelector = ClassJobSelectors.BLUE_MAGE_EXP
        }

        ClassJob.CARPENTER -> {
            levelSelector = ClassJobSelectors.CARPENTER_LEVEL
            unlockStateSelector = ClassJobSelectors.CARPENTER_UNLOCK_STATE
            expSelector = ClassJobSelectors.CARPENTER_EXP
        }

        ClassJob.BLACKSMITH -> {
            levelSelector = ClassJobSelectors.BLACKSMITH_LEVEL
            unlockStateSelector = ClassJobSelectors.BLACKSMITH_UNLOCK_STATE
            expSelector = ClassJobSelectors.BLACKSMITH_EXP
        }

        ClassJob.ARMORER -> {
            levelSelector = ClassJobSelectors.ARMORER_LEVEL
            unlockStateSelector = ClassJobSelectors.ARMORER_UNLOCK_STATE
            expSelector = ClassJobSelectors.ARMORER_EXP
        }

        ClassJob.GOLDSMITH -> {
            levelSelector = ClassJobSelectors.GOLDSMITH_LEVEL
            unlockStateSelector = ClassJobSelectors.GOLDSMITH_UNLOCK_STATE
            expSelector = ClassJobSelectors.GOLDSMITH_EXP
        }

        ClassJob.LEATHERWORKER -> {
            levelSelector = ClassJobSelectors.LEATHERWORKER_LEVEL
            unlockStateSelector = ClassJobSelectors.LEATHERWORKER_UNLOCK_STATE
            expSelector = ClassJobSelectors.LEATHERWORKER_EXP
        }

        ClassJob.WEAVER -> {
            levelSelector = ClassJobSelectors.WEAVER_LEVEL
            unlockStateSelector = ClassJobSelectors.WEAVER_UNLOCK_STATE
            expSelector = ClassJobSelectors.WEAVER_EXP
        }

        ClassJob.ALCHEMIST -> {
            levelSelector = ClassJobSelectors.ALCHEMIST_LEVEL
            unlockStateSelector = ClassJobSelectors.ALCHEMIST_UNLOCK_STATE
            expSelector = ClassJobSelectors.ALCHEMIST_EXP
        }

        ClassJob.CULINARIAN -> {
            levelSelector = ClassJobSelectors.CULINARIAN_LEVEL
            unlockStateSelector = ClassJobSelectors.CULINARIAN_UNLOCK_STATE
            expSelector = ClassJobSelectors.CULINARIAN_EXP
        }

        ClassJob.MINER -> {
            levelSelector = ClassJobSelectors.MINER_LEVEL
            unlockStateSelector = ClassJobSelectors.MINER_UNLOCK_STATE
            expSelector = ClassJobSelectors.MINER_EXP
        }

        ClassJob.BOTANIST -> {
            levelSelector = ClassJobSelectors.BOTANIST_LEVEL
            unlockStateSelector = ClassJobSelectors.BOTANIST_UNLOCK_STATE
            expSelector = ClassJobSelectors.BOTANIST_EXP
        }

        ClassJob.FISHER -> {
            levelSelector = ClassJobSelectors.FISHER_LEVEL
            unlockStateSelector = ClassJobSelectors.FISHER_UNLOCK_STATE
            expSelector = ClassJobSelectors.FISHER_EXP
        }
    }

    val level = document.select(levelSelector).text().let {
        if (it == "-") null else it.toByte()
    }

    return if (level != null) {
        val unlockState = ClassJob.valueOf(
            document.select(unlockStateSelector)
                .text()
                .replace(" ", "_")
                .uppercase()
        )

        val experience = document.select(expSelector).text().let {
            if (it == "-- / --") null else it.split('/')
        }?.let {
            Experience(
                it[0].trim().replace(",", "").toInt(),
                it[1].trim().replace(",", "").toInt()
            )
        }

        ClassLevel(unlockState, level, experience)
    } else null
}

private class ClassLevel(
    val unlockState: ClassJob,
    val level: Byte,
    val experience: Experience?,
)
