package cloud.drakon.ktlodestone.character.profile

import cloud.drakon.ktlodestone.character.ActiveClassJob
import cloud.drakon.ktlodestone.character.ClassJob
import cloud.drakon.ktlodestone.character.Guild
import cloud.drakon.ktlodestone.character.grandcompany.GrandCompany
import cloud.drakon.ktlodestone.character.profile.gearset.GearSet
import cloud.drakon.ktlodestone.world.DataCenter
import cloud.drakon.ktlodestone.world.Region
import cloud.drakon.ktlodestone.world.World

/**
 * A Character returned by The Lodestone's `/character` endpoint.
 */
data class CharacterProfile(
    /** The Character's active class or job. */
    val activeClassJob: ActiveClassJob,
    /** A map of the Character's unlocked classes/jobs to their level. */
    val classJob: Map<ClassJob, Byte>,
    /** The Character's avatar. */
    val avatar: String,
    /** The Character's bio. */
    val bio: String,
    /** The Character's Free Company. */
    val freeCompany: Guild?,
    /** The Character's Grand Company. */
    val grandCompany: GrandCompany?,
    /** The Character's Guardian. */
    val guardian: Guardian,
    /** The Character's name. */
    val name: String,
    /** The Character's nameday. */
    val nameday: String,
    /** The Character's gear set */
    val gearSet: GearSet,
    /** The Character's portrait. */
    val portrait: String,
    /** The Character's PvP Team. */
    val pvpTeam: Guild?,
    /** The Character's race. */
    val race: Race,
    /** The Character's clan. */
    val clan: Clan,
    /** The Character's gender. */
    val gender: Gender,
    /** The Character's world. */
    val world: World,
    /** The Character's data center. */
    val dataCenter: DataCenter,
    /** The Character's region. */
    val region: Region,
    /** The Character's title. */
    val title: String?,
    /** The Character's starting town. */
    val town: Town,
    /** The Character's attributes. */
    val attributes: Attributes,
)
