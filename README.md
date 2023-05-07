# KtLodestone

[![Kotlin Alpha](https://kotl.in/badges/alpha.svg)](https://kotlinlang.org/docs/components-stability.html)
[![Kotlin](https://img.shields.io/badge/kotlin-1.8.21-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![License](https://img.shields.io/github/license/drakon64/KtLodestone)](hhttps://opensource.org/license/mit/)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=KtLodestone&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=KtLodestone)

KtLodestone is a parser for The Lodestone for the JVM platform (JDK 11+).

# Features

---

- Supports scraping the following information from The Lodestone:
  - Character
    - Profile
    - Attributes
    - Class/job
    - Equipped gear
    - Minions
    - Mounts
- Asynchronous
- Reasonably multi-threaded
- Compatible with Kotlin and Java
  - Kotlin functions are suspendable
  - Java-compatible functions are affixed with `Async` and return a `CompletableFuture`

# Installation

---

KtLodestone is available from Maven Central:
```kotlin
dependencies {
    implementation("cloud.drakon.ktlodestone:6.0.0")
}
```

# Documentation

---

Kotlin KDocs: [![kdoc](https://img.shields.io/badge/kdoc-6.0.0-brightgreen)](https://drakon64.github.io/KtLodestone/)<br>
Javadocs: [![javadoc](https://javadoc.io/badge2/cloud.drakon/ktlodestone/javadoc.svg)](https://javadoc.io/doc/cloud.drakon/ktlodestone)

# Examples

---

## Getting information about a Lodestone character

Kotlin:
```kotlin
val character: ProfileCharacter = KtLodestone.getCharacter(27545492) // Must be called from a coroutine or a suspendable function
val activeClassJobName: String = character.activeClassJob.name // returns "Red Mage"
val activeClassJobLevel: Byte = character.activeClassJob.level // returns `90`
val title: String = character.title // returns "Hope's Legacy"
```

Java:
```java
ProfileCharacter character = KtLodestone.getCharacterAsync(27545492).get(); // Async functions return a `CompletableFuture`
String activeClassJobName = character.getActiveClassJob().getName(); // returns "Red Mage"
Byte activeClassJobLevel = character.getActiveClassJob().getLevel(); // returns `90`
String title = character.getTitle(); // returns "Hope's Legacy"
```
