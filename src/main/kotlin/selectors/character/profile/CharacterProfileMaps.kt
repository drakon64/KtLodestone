package cloud.drakon.ktlodestone.selectors.character.profile

import cloud.drakon.ktlodestone.character.ClassJob
import cloud.drakon.ktlodestone.character.Discipline
import cloud.drakon.ktlodestone.character.profile.Gender
import cloud.drakon.ktlodestone.character.profile.Guardian
import cloud.drakon.ktlodestone.character.profile.Race
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.Region

internal object CharacterProfileMaps {
    // The currently displayed class/job of a character is actually an image, so we map the image URL to the [ClassJob] enum entry
    val CLASS_JOB_MAP = mapOf(
        "https://lds-img.finalfantasyxiv.com/h/U/F5JzG9RPIKFSogtaKNBk455aYA.png" to ClassJob.GLADIATOR,
        "https://lds-img.finalfantasyxiv.com/h/E/d0Tx-vhnsMYfYpGe9MvslemEfg.png" to ClassJob.PALADIN,
        "https://lds-img.finalfantasyxiv.com/h/N/St9rjDJB3xNKGYg-vwooZ4j6CM.png" to ClassJob.MARAUDER,
        "https://lds-img.finalfantasyxiv.com/h/y/A3UhbjZvDeN3tf_6nJ85VP0RY0.png" to ClassJob.WARRIOR,
        "https://lds-img.finalfantasyxiv.com/h/l/5CZEvDOMYMyVn2td9LZigsgw9s.png" to ClassJob.DARK_KNIGHT,
        "https://lds-img.finalfantasyxiv.com/h/8/hg8ofSSOKzqng290No55trV4mI.png" to ClassJob.GUNBREAKER,

        "https://lds-img.finalfantasyxiv.com/h/s/gl62VOTBJrm7D_BmAZITngUEM8.png" to ClassJob.CONJURER,
        "https://lds-img.finalfantasyxiv.com/h/7/i20QvSPcSQTybykLZDbQCgPwMw.png" to ClassJob.WHITE_MAGE,
        "https://lds-img.finalfantasyxiv.com/h/7/WdFey0jyHn9Nnt1Qnm-J3yTg5s.png" to ClassJob.SCHOLAR,
        "https://lds-img.finalfantasyxiv.com/h/1/erCgjnMSiab4LiHpWxVc-tXAqk.png" to ClassJob.ASTROLOGIAN,
        "https://lds-img.finalfantasyxiv.com/h/g/_oYApASVVReLLmsokuCJGkEpk0.png" to ClassJob.SAGE,

        "https://lds-img.finalfantasyxiv.com/h/V/iW7IBKQ7oglB9jmbn6LwdZXkWw.png" to ClassJob.PUGILIST,
        "https://lds-img.finalfantasyxiv.com/h/K/HW6tKOg4SOJbL8Z20GnsAWNjjM.png" to ClassJob.MONK,
        "https://lds-img.finalfantasyxiv.com/h/k/tYTpoSwFLuGYGDJMff8GEFuDQs.png" to ClassJob.LANCER,
        "https://lds-img.finalfantasyxiv.com/h/m/gX4OgBIHw68UcMU79P7LYCpldA.png" to ClassJob.DRAGOON,
        "https://lds-img.finalfantasyxiv.com/h/y/wdwVVcptybfgSruoh8R344y_GA.png" to ClassJob.ROGUE,
        "https://lds-img.finalfantasyxiv.com/h/0/Fso5hanZVEEAaZ7OGWJsXpf3jw.png" to ClassJob.NINJA,
        "https://lds-img.finalfantasyxiv.com/h/m/KndG72XtCFwaq1I1iqwcmO_0zc.png" to ClassJob.SAMURAI,
        "https://lds-img.finalfantasyxiv.com/h/7/cLlXUaeMPJDM2nBhIeM-uDmPzM.png" to ClassJob.REAPER,

        "https://lds-img.finalfantasyxiv.com/h/Q/ZpqEJWYHj9SvHGuV9cIyRNnIkk.png" to ClassJob.ARCHER,
        "https://lds-img.finalfantasyxiv.com/h/F/KWI-9P3RX_Ojjn_mwCS2N0-3TI.png" to ClassJob.BARD,
        "https://lds-img.finalfantasyxiv.com/h/E/vmtbIlf6Uv8rVp2YFCWA25X0dc.png" to ClassJob.MACHINIST,
        "https://lds-img.finalfantasyxiv.com/h/t/HK0jQ1y7YV9qm30cxGOVev6Cck.png" to ClassJob.DANCER,

        "https://lds-img.finalfantasyxiv.com/h/4/IM3PoP6p06GqEyReygdhZNh7fU.png" to ClassJob.THAUMATURGE,
        "https://lds-img.finalfantasyxiv.com/h/P/V01m8YRBYcIs5vgbRtpDiqltSE.png" to ClassJob.BLACK_MAGE,
        "https://lds-img.finalfantasyxiv.com/h/e/VYP1LKTDpt8uJVvUT7OKrXNL9E.png" to ClassJob.ARCANIST,
        "https://lds-img.finalfantasyxiv.com/h/h/4ghjpyyuNelzw1Bl0sM_PBA_FE.png" to ClassJob.SUMMONER,
        "https://lds-img.finalfantasyxiv.com/h/q/s3MlLUKmRAHy0pH57PnFStHmIw.png" to ClassJob.RED_MAGE,
        "https://lds-img.finalfantasyxiv.com/h/p/jdV3RRKtWzgo226CC09vjen5sk.png" to ClassJob.BLUE_MAGE,

        "https://lds-img.finalfantasyxiv.com/h/v/YCN6F-xiXf03Ts3pXoBihh2OBk.png" to ClassJob.CARPENTER,
        "https://lds-img.finalfantasyxiv.com/h/5/EEHVV5cIPkOZ6v5ALaoN5XSVRU.png" to ClassJob.BLACKSMITH,
        "https://lds-img.finalfantasyxiv.com/h/G/Rq5wcK3IPEaAB8N-T9l6tBPxCY.png" to ClassJob.ARMORER,
        "https://lds-img.finalfantasyxiv.com/h/L/LbEjgw0cwO_2gQSmhta9z03pjM.png" to ClassJob.GOLDSMITH,
        "https://lds-img.finalfantasyxiv.com/h/b/ACAcQe3hWFxbWRVPqxKj_MzDiY.png" to ClassJob.LEATHERWORKER,
        "https://lds-img.finalfantasyxiv.com/h/X/E69jrsOMGFvFpCX87F5wqgT_Vo.png" to ClassJob.WEAVER,
        "https://lds-img.finalfantasyxiv.com/h/C/bBVQ9IFeXqjEdpuIxmKvSkqalE.png" to ClassJob.ALCHEMIST,
        "https://lds-img.finalfantasyxiv.com/h/m/1kMI2v_KEVgo30RFvdFCyySkFo.png" to ClassJob.CULINARIAN,

        "https://lds-img.finalfantasyxiv.com/h/A/aM2Dd6Vo4HW_UGasK7tLuZ6fu4.png" to ClassJob.MINER,
        "https://lds-img.finalfantasyxiv.com/h/I/jGRnjIlwWridqM-mIPNew6bhHM.png" to ClassJob.BOTANIST,
        "https://lds-img.finalfantasyxiv.com/h/x/B4Azydbn7Prubxt7OL9p1LZXZ0.png" to ClassJob.FISHER,
    )

    val DISCIPLINE_MAP = mapOf(
        ClassJob.GLADIATOR to Discipline.DISCIPLE_OF_WAR,
        ClassJob.PALADIN to Discipline.DISCIPLE_OF_WAR,
        ClassJob.MARAUDER to Discipline.DISCIPLE_OF_WAR,
        ClassJob.WARRIOR to Discipline.DISCIPLE_OF_WAR,
        ClassJob.DARK_KNIGHT to Discipline.DISCIPLE_OF_WAR,
        ClassJob.GUNBREAKER to Discipline.DISCIPLE_OF_WAR,

        ClassJob.CONJURER to Discipline.DISCIPLE_OF_MAGIC,
        ClassJob.WHITE_MAGE to Discipline.DISCIPLE_OF_MAGIC,
        ClassJob.SCHOLAR to Discipline.DISCIPLE_OF_MAGIC,
        ClassJob.ASTROLOGIAN to Discipline.DISCIPLE_OF_MAGIC,
        ClassJob.SAGE to Discipline.DISCIPLE_OF_MAGIC,

        ClassJob.PUGILIST to Discipline.DISCIPLE_OF_WAR,
        ClassJob.MONK to Discipline.DISCIPLE_OF_WAR,
        ClassJob.LANCER to Discipline.DISCIPLE_OF_WAR,
        ClassJob.DRAGOON to Discipline.DISCIPLE_OF_WAR,
        ClassJob.ROGUE to Discipline.DISCIPLE_OF_WAR,
        ClassJob.NINJA to Discipline.DISCIPLE_OF_WAR,
        ClassJob.SAMURAI to Discipline.DISCIPLE_OF_WAR,
        ClassJob.REAPER to Discipline.DISCIPLE_OF_WAR,

        ClassJob.ARCHER to Discipline.DISCIPLE_OF_WAR,
        ClassJob.BARD to Discipline.DISCIPLE_OF_WAR,
        ClassJob.MACHINIST to Discipline.DISCIPLE_OF_WAR,
        ClassJob.DANCER to Discipline.DISCIPLE_OF_WAR,

        ClassJob.THAUMATURGE to Discipline.DISCIPLE_OF_MAGIC,
        ClassJob.BLACK_MAGE to Discipline.DISCIPLE_OF_MAGIC,
        ClassJob.ARCANIST to Discipline.DISCIPLE_OF_MAGIC,
        ClassJob.SUMMONER to Discipline.DISCIPLE_OF_MAGIC,
        ClassJob.RED_MAGE to Discipline.DISCIPLE_OF_MAGIC,
        ClassJob.BLUE_MAGE to Discipline.DISCIPLE_OF_MAGIC,

        ClassJob.CARPENTER to Discipline.DISCIPLE_OF_THE_HAND,
        ClassJob.BLACKSMITH to Discipline.DISCIPLE_OF_THE_HAND,
        ClassJob.ARMORER to Discipline.DISCIPLE_OF_THE_HAND,
        ClassJob.GOLDSMITH to Discipline.DISCIPLE_OF_THE_HAND,
        ClassJob.LEATHERWORKER to Discipline.DISCIPLE_OF_THE_HAND,
        ClassJob.WEAVER to Discipline.DISCIPLE_OF_THE_HAND,
        ClassJob.ALCHEMIST to Discipline.DISCIPLE_OF_THE_HAND,
        ClassJob.CULINARIAN to Discipline.DISCIPLE_OF_THE_HAND,

        ClassJob.MINER to Discipline.DISCIPLE_OF_THE_LAND,
        ClassJob.BOTANIST to Discipline.DISCIPLE_OF_THE_LAND,
        ClassJob.FISHER to Discipline.DISCIPLE_OF_THE_LAND,
    )

    // For each entry in the [Guardian] enum, create a Map where the keys are the `.toString` values of the enum entries and the values are the enum entries themselves
    val GUARDIAN_MAP = Guardian.entries.associateBy {
        it.toString()
    }

    // For each entry in the [Race] enum, create a Map where the keys are the `.toString` values of the enum entries and the values are the enum entries themselves
    val RACE_MAP = Race.entries.associateBy {
        it.toString()
    }

    val GENDER_MAP = mapOf(
        '♀' to Gender.FEMALE,
        '♂' to Gender.MALE,
    )

    // The Lodestone doesn't return what Region a character is in but we can infer it from their data center
    val REGION_MAP = mapOf(
        DataCenter.Chaos to Region.EUROPE,
        DataCenter.Light to Region.EUROPE,

        DataCenter.Materia to Region.OCEANIA,

        DataCenter.Aether to Region.NORTH_AMERICA,
        DataCenter.Crystal to Region.NORTH_AMERICA,
        DataCenter.Dynamis to Region.NORTH_AMERICA,
        DataCenter.Primal to Region.NORTH_AMERICA,

        DataCenter.Elemental to Region.JAPAN,
        DataCenter.Gaia to Region.JAPAN,
        DataCenter.Mana to Region.JAPAN,
        DataCenter.Meteor to Region.JAPAN
    )
}
