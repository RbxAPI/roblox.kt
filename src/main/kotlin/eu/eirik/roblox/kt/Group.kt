package eu.eirik.roblox.kt

import eu.eirik.roblox.kt.interfaces.Group

/**
 * A Roblox group
 */
open class Group(override val id: Int, override val name: String, override val description: String, override val memberCount: Int) : Group
