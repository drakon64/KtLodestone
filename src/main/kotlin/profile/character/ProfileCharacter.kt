package cloud.drakon.ktlodestone.profile.character

import cloud.drakon.ktlodestone.profile.guild.FreeCompany
import cloud.drakon.ktlodestone.profile.guild.PvpTeam

/**
 * The Lodestone profile of a character.
 * @property activeClassJob The active class/job of the character.
 * @property avatar The URL of the avatar of the character.
 * @property bio The bio of the character.
 * @property freeCompany The Free Company that the character is a member of.
 * @property grandCompany The Grand Company that the character is a member of.
 * @property guardian The guardian of the character.
 * @property name The name of the character.
 * @property nameday The nameday of the character.
 * @property portrait The URL of the portrait of the character.
 * @property pvpTeam The PvP Team that the character is a member of.
 * @property race The race of the character.
 * @property clan The clan of the character.
 * @property gender The gender of the character.
 * @property server The world that the character is based in.
 * @property dc The datacenter that the character is based in.
 * @property title The title of the character.
 * @property town The starting town of the character.
 */
data class ProfileCharacter(
    val activeClassJob: ActiveClassJob,
    val avatar: String,
    val bio: String,
    val freeCompany: FreeCompany?,
    val grandCompany: GrandCompany?,
    val guardian: Guardian,
    val name: String,
    val nameday: String,
    val portrait: String,
    val pvpTeam: PvpTeam?,
    val race: String,
    val clan: String,
    val gender: String,
    val server: String,
    val dc: String,
    val title: String?,
    val town: Town,
)
