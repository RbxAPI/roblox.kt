package eu.eirik.roblox.kt

import kotlin.test.Test

val client = Client()

class ClientTest {
    init {
        client.login("YourTestAccountUsername", "YourTestAccountPassword")
    }

    @Test fun successfulLogin() {
        // UninitializedPropertyAccessException will be thrown
        client.token
    }

    @Test fun successfulUserFetchLogin() {
        client.user
    }
}
