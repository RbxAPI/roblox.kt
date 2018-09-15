package eu.eirik.roblox.kt

import com.github.kittinunf.fuel.Fuel
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

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
     * Fetches all the user's Roblox groups and sets [UserGroup]
     * @return List of [UserGroup]
     */
    fun fetchGroups(): List<UserGroup> {
        val userGroupsUrl = "https://api.roblox.com/users/$id/groups"

        val (_, _, result) = Fuel.get(userGroupsUrl).responseString()
        val (data, error) = result

        if (error != null) throw error

        if (data != null) {
            val gson = GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .create()

            val listType = object : TypeToken<List<UserGroup>>() { }.type
            groups = gson.fromJson(data, listType)

            return groups
        } else throw Exception("No data in user groups response")
    }
}
