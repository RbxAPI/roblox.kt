package eu.eirik.roblox.kt

import com.github.kittinunf.fuel.Fuel
import com.google.gson.JsonParser

/**
 * A Roblox user/player
 *
 * @property id The user ID
 * @property name The user name
 * @property groups The user's groups (if [fetchGroups] has been ran)
 */
class User(val id: Int, val name: String) {
    lateinit var groups: List<UserGroup>
        private set

    /**
     * Fetches all the user's Roblox groups and sets [groups]
     * @return List of [UserGroup]
     */
    fun fetchGroups(): List<UserGroup> {
        val userGroupsUrl = "https://groups.roblox.com/v1/users/$id/groups/roles"

        val (_, _, result) = Fuel.get(userGroupsUrl).responseString()
        val (data, error) = result

        if (error != null) throw error

        if (data != null) {
            val parser = JsonParser()
            val root = parser.parse(data).asJsonObject
            val rolesData = root.getAsJsonArray("data")

            val userGroups: MutableList<UserGroup> = mutableListOf()

            for (userGroupData in rolesData.iterator()) {
                val groupData = userGroupData.asJsonObject.get("group").asJsonObject
                val roleData = userGroupData.asJsonObject.get("role").asJsonObject

                userGroups.add(UserGroup(groupData.get("id").asInt, groupData.get("name").asString, groupData.get("description").asString, groupData.get("memberCount").asInt, roleData.get("rank").asInt, roleData.get("name").asString))
            }

            groups = userGroups
            return userGroups
        } else throw Exception("No data in user groups response")
    }
}
