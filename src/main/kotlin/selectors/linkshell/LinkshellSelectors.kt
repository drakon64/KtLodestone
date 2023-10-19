package cloud.drakon.ktlodestone.selectors.linkshell

// https://github.com/xivapi/lodestone-css-selectors/blob/main/cwls/cwls.json
internal object LinkshellSelectors {
    const val NAME = ".heading__linkshell__name"

    const val FORMED = ".heading__cwls__formed > script"
    val FORMED_REGEX = """(?<=ldst_strftime\()\d+""".toRegex()

    const val DATACENTER = ".heading__cwls__dcname"
}
