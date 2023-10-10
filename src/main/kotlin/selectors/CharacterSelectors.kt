package cloud.drakon.ktlodestone.selectors

import cloud.drakon.ktlodestone.character.Clan
import cloud.drakon.ktlodestone.character.Gender
import cloud.drakon.ktlodestone.character.Guardian
import cloud.drakon.ktlodestone.character.Race
import cloud.drakon.ktlodestone.character.Town
import cloud.drakon.ktlodestone.classjob.ClassJob

internal object CharacterSelectors {
    const val ACTIVE_CLASSJOB = ".character__class_icon > img:nth-child(1)"
    const val ACTIVE_CLASSJOB_ATTR = "src"

    const val ACTIVE_CLASSJOB_LEVEL = ".character__class__data > p:nth-child(1)"
    val ACTIVE_CLASSJOB_LEVEL_REGEX = """\d+""".toRegex()

    const val AVATAR = ".frame__chara__face > img:nth-child(1)"
    const val AVATAR_ATTR = "src"

    const val BIO = ".character__selfintroduction"

    const val FREE_COMPANY =
        ".character__freecompany__name > h4:nth-child(2) > a:nth-child(1)"
    const val FREE_COMPANY_ID_ATTR = "href"
    const val FREE_COMPANY_BOTTOM_ICON_LAYER =
        "div.character__freecompany__crest > div > img:nth-child(1)"
    const val FREE_COMPANY_MIDDLE_ICON_LAYER =
        "div.character__freecompany__crest > div > img:nth-child(2)"
    const val FREE_COMPANY_TOP_ICON_LAYER =
        "div.character__freecompany__crest > div > img:nth-child(3)"
    const val FREE_COMPANY_ICON_LAYER_ATTR = "src"

    const val GRAND_COMPANY =
        "div.character-block:nth-child(4) > div:nth-child(2) > p:nth-child(2)"

    const val GUARDIAN_NAME = "p.character-block__name:nth-child(4)"

    const val NAME = "div.frame__chara__box:nth-child(2) > .frame__chara__name"

    const val NAMEDAY = ".character-block__birth"

    const val PORTRAIT = ".js__image_popup > img:nth-child(1)"
    const val PORTRAIT_ATTR = "src"

    const val PVP_TEAM = ".character__pvpteam__name > h4:nth-child(2) > a:nth-child(1)"
    const val PVP_TEAM_ID_ATTR = "href"
    const val PVP_TEAM_BOTTOM_ICON_LAYER =
        ".character__pvpteam__crest__image img:nth-child(1)"
    const val PVP_TEAM_MIDDLE_ICON_LAYER =
        ".character__pvpteam__crest__image img:nth-child(2)"
    const val PVP_TEAM_TOP_ICON_LAYER =
        ".character__pvpteam__crest__image img:nth-child(3)"
    const val PVP_TEAM_ICON_LAYER_ATTR = "src"

    const val RACE_CLAN_GENDER =
        "div.character-block:nth-child(1) > div:nth-child(2) > p:nth-child(2)"
    val RACE_REGEX = """.*(?=<br>)""".toRegex()
    val CLAN_REGEX = """(?<=<br>\n)[^ /]+""".toRegex()
    val GENDER_REGEX = """[♀|♂]""".toRegex()

    const val WORLD = "p.frame__chara__world"

    const val TITLE = ".frame__chara__title"

    const val TOWN =
        "div.character-block:nth-child(3) > div:nth-child(2) > p:nth-child(2)"

    // The currently displayed class/job of a character is actually an image, so we map the image URL to the [ClassJob] enum entry
    val CLASS_JOB_MAP = mapOf(
        "https://img.finalfantasyxiv.com/lds/h/U/F5JzG9RPIKFSogtaKNBk455aYA.png" to ClassJob.GLADIATOR,
        "https://img.finalfantasyxiv.com/lds/h/E/d0Tx-vhnsMYfYpGe9MvslemEfg.png" to ClassJob.PALADIN,
        "https://img.finalfantasyxiv.com/lds/h/N/St9rjDJB3xNKGYg-vwooZ4j6CM.png" to ClassJob.MARAUDER,
        "https://img.finalfantasyxiv.com/lds/h/y/A3UhbjZvDeN3tf_6nJ85VP0RY0.png" to ClassJob.WARRIOR,
        "https://img.finalfantasyxiv.com/lds/h/l/5CZEvDOMYMyVn2td9LZigsgw9s.png" to ClassJob.DARK_KNIGHT,
        "https://img.finalfantasyxiv.com/lds/h/8/hg8ofSSOKzqng290No55trV4mI.png" to ClassJob.GUNBREAKER,

        "https://img.finalfantasyxiv.com/lds/h/s/gl62VOTBJrm7D_BmAZITngUEM8.png" to ClassJob.CONJURER,
        "https://img.finalfantasyxiv.com/lds/h/7/i20QvSPcSQTybykLZDbQCgPwMw.png" to ClassJob.WHITE_MAGE,
        "https://img.finalfantasyxiv.com/lds/h/7/WdFey0jyHn9Nnt1Qnm-J3yTg5s.png" to ClassJob.SCHOLAR,
        "https://img.finalfantasyxiv.com/lds/h/1/erCgjnMSiab4LiHpWxVc-tXAqk.png" to ClassJob.ASTROLOGIAN,
        "https://img.finalfantasyxiv.com/lds/h/g/_oYApASVVReLLmsokuCJGkEpk0.png" to ClassJob.SAGE,

        "https://img.finalfantasyxiv.com/lds/h/V/iW7IBKQ7oglB9jmbn6LwdZXkWw.png" to ClassJob.PUGILIST,
        "https://img.finalfantasyxiv.com/lds/h/K/HW6tKOg4SOJbL8Z20GnsAWNjjM.png" to ClassJob.MONK,
        "https://img.finalfantasyxiv.com/lds/h/k/tYTpoSwFLuGYGDJMff8GEFuDQs.png" to ClassJob.LANCER,
        "https://img.finalfantasyxiv.com/lds/h/m/gX4OgBIHw68UcMU79P7LYCpldA.png" to ClassJob.DRAGOON,
        "https://img.finalfantasyxiv.com/lds/h/y/wdwVVcptybfgSruoh8R344y_GA.png" to ClassJob.ROGUE,
        "https://img.finalfantasyxiv.com/lds/h/0/Fso5hanZVEEAaZ7OGWJsXpf3jw.png" to ClassJob.NINJA,
        "https://img.finalfantasyxiv.com/lds/h/m/KndG72XtCFwaq1I1iqwcmO_0zc.png" to ClassJob.SAMURAI,
        "https://img.finalfantasyxiv.com/lds/h/7/cLlXUaeMPJDM2nBhIeM-uDmPzM.png" to ClassJob.REAPER,

        "https://img.finalfantasyxiv.com/lds/h/Q/ZpqEJWYHj9SvHGuV9cIyRNnIkk.png" to ClassJob.ARCHER,
        "https://img.finalfantasyxiv.com/lds/h/F/KWI-9P3RX_Ojjn_mwCS2N0-3TI.png" to ClassJob.BARD,
        "https://img.finalfantasyxiv.com/lds/h/E/vmtbIlf6Uv8rVp2YFCWA25X0dc.png" to ClassJob.MACHINIST,
        "https://img.finalfantasyxiv.com/lds/h/t/HK0jQ1y7YV9qm30cxGOVev6Cck.png" to ClassJob.DANCER,

        "https://img.finalfantasyxiv.com/lds/h/t/HK0jQ1y7YV9qm30cxGOVev6Cck.png" to ClassJob.THAUMATURGE,
        "https://img.finalfantasyxiv.com/lds/h/P/V01m8YRBYcIs5vgbRtpDiqltSE.png" to ClassJob.BLACK_MAGE,
        "https://img.finalfantasyxiv.com/lds/h/e/VYP1LKTDpt8uJVvUT7OKrXNL9E.png" to ClassJob.ARCANIST,
        "https://img.finalfantasyxiv.com/lds/h/h/4ghjpyyuNelzw1Bl0sM_PBA_FE.png" to ClassJob.SUMMONER,
        "https://img.finalfantasyxiv.com/lds/h/q/s3MlLUKmRAHy0pH57PnFStHmIw.png" to ClassJob.RED_MAGE,
        "https://img.finalfantasyxiv.com/lds/h/p/jdV3RRKtWzgo226CC09vjen5sk.png" to ClassJob.BLUE_MAGE,

        "https://img.finalfantasyxiv.com/lds/h/v/YCN6F-xiXf03Ts3pXoBihh2OBk.png" to ClassJob.CARPENTER,
        "https://img.finalfantasyxiv.com/lds/h/5/EEHVV5cIPkOZ6v5ALaoN5XSVRU.png" to ClassJob.BLACKSMITH,
        "https://img.finalfantasyxiv.com/lds/h/G/Rq5wcK3IPEaAB8N-T9l6tBPxCY.png" to ClassJob.ARMORER,
        "https://img.finalfantasyxiv.com/lds/h/L/LbEjgw0cwO_2gQSmhta9z03pjM.png" to ClassJob.GOLDSMITH,
        "https://img.finalfantasyxiv.com/lds/h/b/ACAcQe3hWFxbWRVPqxKj_MzDiY.png" to ClassJob.LEATHERWORKER,
        "https://img.finalfantasyxiv.com/lds/h/X/E69jrsOMGFvFpCX87F5wqgT_Vo.png" to ClassJob.WEAVER,
        "https://img.finalfantasyxiv.com/lds/h/C/bBVQ9IFeXqjEdpuIxmKvSkqalE.png" to ClassJob.ALCHEMIST,
        "https://img.finalfantasyxiv.com/lds/h/m/1kMI2v_KEVgo30RFvdFCyySkFo.png" to ClassJob.CULINARIAN,

        "https://img.finalfantasyxiv.com/lds/h/A/aM2Dd6Vo4HW_UGasK7tLuZ6fu4.png" to ClassJob.MINER,
        "https://img.finalfantasyxiv.com/lds/h/I/jGRnjIlwWridqM-mIPNew6bhHM.png" to ClassJob.BOTANIST,
        "https://img.finalfantasyxiv.com/lds/h/I/jGRnjIlwWridqM-mIPNew6bhHM.png" to ClassJob.FISHER,
    )

    val GUARDIAN_MAP = mutableMapOf<String, Guardian>().let { map ->
        Guardian.entries.forEach { guardian ->
            map[guardian.toString()] = guardian
        }

        return@let map
    }.toMap()

    val RACE_MAP = mutableMapOf<String, Race>().let { map ->
        Race.entries.forEach { race ->
            map[race.toString()] = race
        }

        return@let map
    }.toMap()

    val GENDER_MAP = mapOf(
        '♀' to Gender.FEMALE,
        '♂' to Gender.MALE,
    )

    val TOWN_MAP = mapOf(
        "Gridania" to Town.GRIDANIA,
        "Limsa Lominsa" to Town.LIMSA_LOMINSA,
        "Ul'dah" to Town.ULDAH,
    )
}
