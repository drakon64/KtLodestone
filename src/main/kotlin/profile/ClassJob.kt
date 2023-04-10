package cloud.drakon.ktlodestone.profile

import cloud.drakon.ktlodestone.profile.classjob.ProfileClassJob
import cloud.drakon.ktlodestone.profile.classjob.bozja.Bozja
import cloud.drakon.ktlodestone.profile.classjob.eureka.Eureka

data class ClassJob(
    val bozja: Bozja?,
    val eureka: Eureka?,
    val paladin: ProfileClassJob?,
    val warrior: ProfileClassJob?,
    val darkKnight: ProfileClassJob?,
    val gunbreaker: ProfileClassJob?,
    val whiteMage: ProfileClassJob?,
    val scholar: ProfileClassJob?,
    val astrologian: ProfileClassJob?,
    val sage: ProfileClassJob?,
    val monk: ProfileClassJob?,
    val dragoon: ProfileClassJob?,
    val ninja: ProfileClassJob?,
    val samurai: ProfileClassJob?,
    val reaper: ProfileClassJob?,
    val bard: ProfileClassJob?,
    val machinist: ProfileClassJob?,
    val dancer: ProfileClassJob?,
    val blackMage: ProfileClassJob?,
    val summoner: ProfileClassJob?,
    val redMage: ProfileClassJob?,
    val blueMage: ProfileClassJob?,
    val carpenter: ProfileClassJob?,
    val blacksmith: ProfileClassJob?,
    val armorer: ProfileClassJob?,
    val goldsmith: ProfileClassJob?,
    val leatherworker: ProfileClassJob?,
    val weaver: ProfileClassJob?,
    val alchemist: ProfileClassJob?,
    val culinarian: ProfileClassJob?,
    val miner: ProfileClassJob?,
    val botanist: ProfileClassJob?,
    val fisher: ProfileClassJob?,
)
