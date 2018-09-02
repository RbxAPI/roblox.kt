package eu.eirik.roblox.kt

import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.Fuel

const val loginUrl = "https://auth.roblox.com/v2/login"

class LoginUserInfo(val user: User)

/**
 * A client to interact with Roblox, starting point for every application
 *
 * @property user The [User] this client is logged in as (if [login] has been ran once)
 * @property token The authentication token cookie this client is using (if [login] has been ran once)
 *
 * Expires approximately every 24 hours
 */
class Client {
    var user: User? = null
    var token: String? = null

    /**
     * Logs in, and sets [user] and [token]
     * @return Authentication token cookie
     */
    fun login(username: String, password: String): String? {
        // Make POST to get CSRF token
        val (_, csrfResponse, csrfResult) = Fuel.post("https://auth.roblox.com/").response()

        // 403 Expected (ROBLOX requires CSRF token)
        var csrfToken: String? = null

        if (csrfResponse.statusCode == 403) csrfToken = csrfResponse.headers["X-CSRF-TOKEN"]?.get(0)
        else {
            val (_, error) = csrfResult
            if (error != null) throw error
            else println("Unexpected status code and no error")
        }

        if (csrfToken == null) {
            println("Couldn't get CSRF token")
            return null
        }

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

        var cookie: String? = null
        val cookies = loginResponse.headers["Set-Cookie"]

        if (cookies != null) {
            val tokenCookieList = cookies.filter { it.contains("ROBLOSECURITY") }

            if (tokenCookieList.isEmpty()) println("No token cookie in response")
            else if (tokenCookieList.size > 1) println("Multiple cookies match \"ROBLOSECURITY\" ")
            else {
                cookie = tokenCookieList.single()

                if (data != null) {
                    user = Klaxon().parse<LoginUserInfo>(data)?.user
                } else println("Cookie token received successfully but no user information in response. Fetch it manually")
            }
        } else println("No `Set-Cookie` header in response")

        if (cookie == null) println("Couldn't get cookie")
        else token = cookie

        return cookie
    }
}
