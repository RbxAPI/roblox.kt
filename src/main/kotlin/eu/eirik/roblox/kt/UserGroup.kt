package eu.eirik.roblox.kt

import com.beust.klaxon.Json

/**
 * A Roblox user group, i. e. a group with additional properties like user's rank
 *
 * @property rank The [User]'s rank in the group
 * @property name The [User]'s role in the group
 * @property inClan Whether or not the [User] is a member of the group clan
 * @property primary Whether or not the [Group] is the [User]'s primary group
 */
class UserGroup(@Json(ignored = false, name = "Id") override val id: Int, @Json(ignored = false, name = "Name") override val name: String, @Json(ignored = false, name = "EmblemId") override val emblemId: Int, @Json(ignored = false, name = "EmblemUrl") override val emblemUrl: String, @Json(name = "Rank") val rank: Int, @Json(name = "Role") val role: String, @Json(name = "IsInClan") val inClan: Boolean, @Json(name = "IsPrimary") val primary: Boolean) : Group(id, name, emblemId, emblemUrl)
