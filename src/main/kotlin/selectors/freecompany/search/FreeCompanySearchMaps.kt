package cloud.drakon.ktlodestone.selectors.freecompany.search

import cloud.drakon.ktlodestone.freecompany.search.Focus
import cloud.drakon.ktlodestone.freecompany.search.Seeking

internal object FreeCompanySearchMaps {
    val FOCUS_MAP = mapOf(
        "https://img.finalfantasyxiv.com/lds/h/9/2RIcg3Swu7asLE9w5hF11Gm1Sg.png" to Focus.ROLE_PLAYING,
        "https://img.finalfantasyxiv.com/lds/h/n/5Y0D3iH7ngHlRpv9-KJKalt3_o.png" to Focus.LEVELING,
        "https://img.finalfantasyxiv.com/lds/h/t/Se_50UHNcTNjytzG8olOSGq3MI.png" to Focus.CASUAL,
        "https://img.finalfantasyxiv.com/lds/h/i/1ZVA5nBRhczPW2kJWBJk5Jkz6o.png" to Focus.HARDCORE,
        "https://img.finalfantasyxiv.com/lds/h/7/jSYmuDYlpVzQ98RA3VhBQWY5PA.png" to Focus.DUNGEONS,
        "https://img.finalfantasyxiv.com/lds/h/C/wiboHfsKd8h_tmbJkNY38w2T8A.png" to Focus.GUILDHESTS,
        "https://img.finalfantasyxiv.com/lds/h/P/ydn7ovgdfDc3MocTJV0vP48l4E.png" to Focus.TRIALS,
        "https://img.finalfantasyxiv.com/lds/h/R/R7mn0-cTBIhT8CcxbtDB-YuPUI.png" to Focus.RAIDS,
        "https://img.finalfantasyxiv.com/lds/h/D/hXSGh69WuUbiC5NmlZhDyaO2g8.png" to Focus.PVP,
    )

    val SEEKING_MAP = mapOf(
        "https://img.finalfantasyxiv.com/lds/h/x/VUfyJ-2iArKs72p4putqJewSgU.png" to Seeking.TANK,
        "https://img.finalfantasyxiv.com/lds/h/A/a8JOc9-8I-ATxTnT3BAkI0MTsA.png" to Seeking.HEALER,
        "https://img.finalfantasyxiv.com/lds/h/d/qXomRVXuqEzMLXk2ncLeoFTFYs.png" to Seeking.DPS,
        "https://img.finalfantasyxiv.com/lds/h/w/h-PrIjVyD8Uy9ndk49alidJYSM.png" to Seeking.CRAFTER,
        "https://img.finalfantasyxiv.com/lds/h/1/7Fd00rEgUDpmWFVulcouYytj54.png" to Seeking.GATHERER,
    )
}
