# DI

## Registering dependencies

You can build your dependency container using factory function as follows:

```kotlin
val di = di {
    // use `constant` function to register
    // dependencies that are already evaluated
    val port by constant(8080)
    // use `singleton` function to register
    // dependencies that need to be evaluated once
    val database by singleton { // this: DI
        val port: Int by getting
        Database.connect(port)
    }
    // use `factory` function to register
    // dependencies that need to be evaluated every time
    // you trying to receive them
    val secureRandom by factory { SecureRandom() }
}
```

## Retrieving dependencies

The first way is just to call get function:

```kotlin
val database = di.get<Database>()
```

If there is only one dependency with type `Database`, you are not 
required to pass the dependency name. If there are multiple dependencies
of some type (it's often constants of primitive types like String, Int, etc.),
you are required to specify the name or `get` will fail.

```kotlin
val port = di.get<String>(name = "port")
```

To avoid boilerplate, you can use delegates to retrieve dependencies which will
pass the name automatically:

```kotlin
val port: String by di.getting
```

There is also a way do make an extension using delegate like this:

```kotlin
val DI.port by Dependency
```

## Idiomatic DI

```kotlin
val di = di {
    // use extensions to initialize DI
    database()
    users()
}

// declare Typed Accessors among with dependency providers
val DI.usersRepository: UsersRepository by Dependency

fun DIBuilder.users() {
    val usersRepository by singleton { this: DI
        // Use Typed Accessors to access other dependencies
        UsersRepository(database)
    }
}

// Retrieve dependencies using Typed Accessors
fun main() {
    di.usersRepository.createUser()
}
```
