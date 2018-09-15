package eu.eirik.roblox.kt

import com.google.gson.annotations.SerializedName
import eu.eirik.roblox.kt.interfaces.Group

/**
 * A Roblox user group, i. e. a group with additional properties like user's rank
 *
 * @property rank The [User]'s rank in the group
 * @property name The [User]'s role in the group
 * @property inClan Whether or not the [User] is a member of the group clan
 * @property primary Whether or not the [Group] is the [User]'s primary group
 */
class UserGroup(override val id: Int, override val name: String, override val emblemId: Int, override val emblemUrl: String, val rank: Int, val role: String, @SerializedName("IsInClan") val inClan: Boolean, @SerializedName("IsPrimary") val primary: Boolean) : Group
