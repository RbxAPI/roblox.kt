package eu.eirik.roblox.kt.interfaces

/**
 * A Roblox group
 *
 * @property id The group ID
 * @property name The group name
 * @property description The group description
 * @property memberCount The group member count
 */
interface Group {
    val id: Int
    val name: String
    val description: String
    val memberCount: Int
}
