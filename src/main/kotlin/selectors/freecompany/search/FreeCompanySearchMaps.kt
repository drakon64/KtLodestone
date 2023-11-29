package cloud.drakon.ktlodestone.selectors.freecompany.search

import cloud.drakon.ktlodestone.freecompany.search.Focus
import cloud.drakon.ktlodestone.freecompany.search.Seeking

internal object FreeCompanySearchMaps {
    val FOCUS_MAP = mapOf(
        "https://lds-img.finalfantasyxiv.com/h/9/2RIcg3Swu7asLE9w5hF11Gm1Sg.png" to Focus.ROLE_PLAYING,
        "https://lds-img.finalfantasyxiv.com/h/n/5Y0D3iH7ngHlRpv9-KJKalt3_o.png" to Focus.LEVELING,
        "https://lds-img.finalfantasyxiv.com/h/t/Se_50UHNcTNjytzG8olOSGq3MI.png" to Focus.CASUAL,
        "https://lds-img.finalfantasyxiv.com/h/i/1ZVA5nBRhczPW2kJWBJk5Jkz6o.png" to Focus.HARDCORE,
        "https://lds-img.finalfantasyxiv.com/h/7/jSYmuDYlpVzQ98RA3VhBQWY5PA.png" to Focus.DUNGEONS,
        "https://lds-img.finalfantasyxiv.com/h/C/wiboHfsKd8h_tmbJkNY38w2T8A.png" to Focus.GUILDHESTS,
        "https://lds-img.finalfantasyxiv.com/h/P/ydn7ovgdfDc3MocTJV0vP48l4E.png" to Focus.TRIALS,
        "https://lds-img.finalfantasyxiv.com/h/R/R7mn0-cTBIhT8CcxbtDB-YuPUI.png" to Focus.RAIDS,
        "https://lds-img.finalfantasyxiv.com/h/D/hXSGh69WuUbiC5NmlZhDyaO2g8.png" to Focus.PVP,
    )

    val SEEKING_MAP = mapOf(
        "https://lds-img.finalfantasyxiv.com/h/x/VUfyJ-2iArKs72p4putqJewSgU.png" to Seeking.TANK,
        "https://lds-img.finalfantasyxiv.com/h/A/a8JOc9-8I-ATxTnT3BAkI0MTsA.png" to Seeking.HEALER,
        "https://lds-img.finalfantasyxiv.com/h/d/qXomRVXuqEzMLXk2ncLeoFTFYs.png" to Seeking.DPS,
        "https://lds-img.finalfantasyxiv.com/h/w/h-PrIjVyD8Uy9ndk49alidJYSM.png" to Seeking.CRAFTER,
        "https://lds-img.finalfantasyxiv.com/h/1/7Fd00rEgUDpmWFVulcouYytj54.png" to Seeking.GATHERER,
    )
}
