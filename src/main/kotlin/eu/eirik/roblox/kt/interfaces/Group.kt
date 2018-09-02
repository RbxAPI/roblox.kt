package eu.eirik.roblox.kt.interfaces

/**
 * A Roblox group
 *
 * @property id The group ID
 * @property name The group name
 * @property emblemId The group emblem/logo asset ID
 * @property emblemUrl The group emblem/logo asset URL
 */
interface Group {
    val id: Int
    val name: String
    val emblemId: Int
    val emblemUrl: String
}
