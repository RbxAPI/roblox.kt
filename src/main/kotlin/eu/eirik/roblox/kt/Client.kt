package eu.eirik.roblox.kt

import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import com.google.gson.JsonObject

const val loginUrl = "https://auth.roblox.com/v2/login"

/**
 * A client to interact with Roblox, starting point for every application
 *
 * @property user The [User] this client is logged in as (if [login] has been ran once)
 * @property token The authentication token cookie this client is using (if [login] has been ran once)
 *
 * Expires approximately every 24 hours
 */
class Client {
    lateinit var user: User
    lateinit var token: String

    /**
     * Logs in, and sets [user] and [token]
     * @return Authentication token cookie
     */
    fun login(username: String, password: String): String? {
        // Make POST to get CSRF token
        val (_, csrfResponse, csrfResult) = Fuel.post("https://auth.roblox.com/").response()

        // 403 Expected (ROBLOX requires CSRF token)
        val csrfToken: String?

        if (csrfResponse.statusCode == 403) csrfToken = csrfResponse.headers["X-CSRF-TOKEN"]?.get(0)
        else {
            val (_, error) = csrfResult
            if (error != null) throw error
            else throw Exception("Unexpected status code and no error when getting CSRF token")
        }

        if (csrfToken == null) throw Exception("Couldn't get CSRF token")

        val loginCredentials = """
            {
                "ctype": "Username",
                "cvalue": "$username",
                "password": "$password"
            }
        """.trimIndent()


        // Make POST to login
        val (_, loginResponse, loginResult) = Fuel.post(loginUrl).body(loginCredentials).header(mapOf("X-CSRF-TOKEN" to csrfToken, "Content-Type" to "application/json")).responseString()

        val (data, error) = loginResult
        if (error != null) throw error

        val cookie: String?
        val cookies = loginResponse.headers["Set-Cookie"]

        if (cookies != null) {
            val tokenCookieList = cookies.filter { it.contains("ROBLOSECURITY") }

            if (tokenCookieList.isEmpty()) throw Exception("No token cookie in response")
            else if (tokenCookieList.size > 1) throw Exception("Multiple cookies match \"ROBLOSECURITY\"")
            else {
                cookie = tokenCookieList.single()

                if (data != null) {
                    val gson = Gson()
                    val rootObject: JsonObject = gson.fromJson(data, JsonObject::class.java)
                    user = gson.fromJson(rootObject.get("user"), User::class.java)
                } else println("Cookie token received successfully but no user information in response. Fetch it manually")
            }
        } else throw Exception("No `Set-Cookie` header in response")

        token = cookie
        return cookie
    }
}
