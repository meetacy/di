package scoped

import app.meetacy.di.DI
import app.meetacy.di.builder.di
import app.meetacy.di.dependency.Dependency

private val DI.container: UserContainer by Dependency
private val DI.user: User by Dependency

private class UserContainer(var userId: Int = 0)
private data class User(val name: String)

fun main() {

    val di = di {
        val container by singleton { UserContainer() }

        val user by scoped {
            keepWhile { retained(di.container.userId) }

            factory {
                when (this.container.userId) {
                    0 -> User("Alex Sokol")
                    1 -> User("Vadim Kotlinov")
                    else -> User("Unknown")
                }
            }
        }
    }

    // Check that one instance returned in one scope
    require(di.user === di.user)
    require(di.user.name == "Alex Sokol")

    di.container.userId = 1

    require(di.user === di.user)
    require(di.user.name == "Vadim Kotlinov")
}
