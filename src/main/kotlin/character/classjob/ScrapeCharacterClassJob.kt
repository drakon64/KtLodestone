package cloud.drakon.ktlodestone.character.classjob

import cloud.drakon.ktlodestone.character.ClassJob
import cloud.drakon.ktlodestone.selectors.character.classjob.ClassJobSelectors
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

internal suspend fun scrapeCharacterClassJob(response: String) = coroutineScope {
    Jsoup.parse(response).let {
        val paladin = async {
            scrapeClassJob(it, ClassJob.PALADIN)
        }

        val warrior = async {
            scrapeClassJob(it, ClassJob.WARRIOR)
        }

        val darkKnight = async {
            scrapeClassJob(it, ClassJob.DARK_KNIGHT)
        }

        val gunbreaker = async {
            scrapeClassJob(it, ClassJob.GUNBREAKER)
        }

        val whiteMage = async {
            scrapeClassJob(it, ClassJob.GUNBREAKER)
        }

        val scholar = async {
            scrapeClassJob(it, ClassJob.SCHOLAR)
        }

        val astrologian = async {
            scrapeClassJob(it, ClassJob.ASTROLOGIAN)
        }

        val sage = async {
            scrapeClassJob(it, ClassJob.SAGE)
        }

        val monk = async {
            scrapeClassJob(it, ClassJob.MONK)
        }

        val dragoon = async {
            scrapeClassJob(it, ClassJob.DRAGOON)
        }

        val ninja = async {
            scrapeClassJob(it, ClassJob.NINJA)
        }

        val samurai = async {
            scrapeClassJob(it, ClassJob.SAMURAI)
        }

        val reaper = async {
            scrapeClassJob(it, ClassJob.REAPER)
        }

        val bard = async {
            scrapeClassJob(it, ClassJob.BARD)
        }

        val machinist = async {
            scrapeClassJob(it, ClassJob.MACHINIST)
        }

        val dancer = async {
            scrapeClassJob(it, ClassJob.DANCER)
        }

        val blackMage = async {
            scrapeClassJob(it, ClassJob.BLACK_MAGE)
        }

        val summoner = async {
            scrapeClassJob(it, ClassJob.SUMMONER)
        }

        val redMage = async {
            scrapeClassJob(it, ClassJob.RED_MAGE)
        }

        val blueMage = async {
            scrapeClassJob(it, ClassJob.BLUE_MAGE)
        }

        val carpenter = async {
            scrapeClassJob(it, ClassJob.CARPENTER)
        }

        val blacksmith = async {
            scrapeClassJob(it, ClassJob.BLACKSMITH)
        }

        val armorer = async {
            scrapeClassJob(it, ClassJob.ARMORER)
        }

        val goldsmith = async {
            scrapeClassJob(it, ClassJob.GOLDSMITH)
        }

        val leatherworker = async {
            scrapeClassJob(it, ClassJob.LEATHERWORKER)
        }

        val weaver = async {
            scrapeClassJob(it, ClassJob.WEAVER)
        }

        val alchemist = async {
            scrapeClassJob(it, ClassJob.ALCHEMIST)
        }

        val culinarian = async {
            scrapeClassJob(it, ClassJob.CULINARIAN)
        }

        val miner = async {
            scrapeClassJob(it, ClassJob.MINER)
        }

        val botanist = async {
            scrapeClassJob(it, ClassJob.BOTANIST)
        }

        val fisher = async {
            scrapeClassJob(it, ClassJob.FISHER)
        }

        ClassJobLevels(
            paladin.await(),
            warrior.await(),
            darkKnight.await(),
            gunbreaker.await(),
            whiteMage.await(),
            scholar.await(),
            astrologian.await(),
            sage.await(),
            monk.await(),
            dragoon.await(),
            ninja.await(),
            samurai.await(),
            reaper.await(),
            bard.await(),
            machinist.await(),
            dancer.await(),
            blackMage.await(),
            summoner.await(),
            redMage.await(),
            blueMage.await(),
            carpenter.await(),
            blacksmith.await(),
            armorer.await(),
            goldsmith.await(),
            leatherworker.await(),
            weaver.await(),
            alchemist.await(),
            culinarian.await(),
            miner.await(),
            botanist.await(),
            fisher.await(),
        )
    }
}

private suspend fun scrapeClassJob(document: Document, job: ClassJob) = coroutineScope {
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

    if (level != null) {
        val unlockState = async {
            ClassJob.valueOf(
                document.select(unlockStateSelector)
                    .text()
                    .replace(" ", "_")
                    .uppercase()
            )
        }

        val exp = document.select(expSelector).text().let {
            if (it == "-- / --") null else it.split('/')
        }

        val currentExp = async {
            exp?.get(0)?.trim()?.replace(",", "")?.toInt()
        }

        val expToNextLevel = async {
            exp?.get(1)?.trim()?.replace(",", "")?.toInt()
        }

        ClassJobLevel(
            unlockState.await(),
            level,
            currentExp.await(),
            expToNextLevel.await()
        )
    } else null
}
