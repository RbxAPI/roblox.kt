package eu.eirik.roblox.kt

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.Fuel

/**
 * A Roblox user/player
 *
 * @property id The user ID
 * @property name The user name
 * @property groups The user's groups (if [fetchGroups] has been ran)
 */
data class User(val id: Int, val name: String) {
    @Json(ignored = true) lateinit var groups: List<UserGroup>
        private set

    /**
     * Fetches all the user's Roblox groups and sets [groups]
     * @return List of [UserGroup]
     */
    fun fetchGroups(): List<UserGroup> {
        val userGroupsUrl = "https://api.roblox.com/users/$id/groups"

        val (_, _, result) = Fuel.get(userGroupsUrl).responseString()
        val (data, error) = result

        if (error != null) throw error

        if (data != null) {
            val parsedData = Klaxon().parseArray<UserGroup>(data) ?: throw Exception("No groups")
            groups = parsedData
            return parsedData
        } else throw Exception("No data in user groups response")
    }
}
