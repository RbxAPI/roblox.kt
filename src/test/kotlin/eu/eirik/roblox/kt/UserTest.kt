package eu.eirik.roblox.kt

import org.junit.Test

class UserTest {
    @Test fun successfulGroupFetch() {
        client.user.fetchGroups()

        client.user.groups
    }
}
