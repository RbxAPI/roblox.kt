package eu.eirik.roblox.kt

import com.google.gson.annotations.SerializedName
import eu.eirik.roblox.kt.interfaces.Group

/**
 * A Roblox user group, i. e. a group with additional properties like user's rank
 *
 * @property rank The [User]'s rank in the group
 * @property name The [User]'s role in the group
 */
class UserGroup(override val id: Int, override val name: String, override val description: String, override val memberCount: Int, val rank: Int, val role: String) : Group
