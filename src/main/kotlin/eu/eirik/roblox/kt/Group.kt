package eu.eirik.roblox.kt

import com.beust.klaxon.Json
import eu.eirik.roblox.kt.interfaces.Group

/**
 * A Roblox group
 */
open class Group(@Json(name = "Id") override val id: Int, @Json(name = "Name") override val name: String, @Json(name = "EmblemId") override val emblemId: Int, @Json(name = "EmblemUrl") override val emblemUrl: String) : Group
