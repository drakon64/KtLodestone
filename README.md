# KtLodestone

![Kotlin Beta](https://kotl.in/badges/beta.svg)
[![Kotlin](https://img.shields.io/badge/kotlin-1.9-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![License](https://img.shields.io/github/license/drakon64/KtLodestone)](hhttps://opensource.org/license/mit/)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=KtLodestone&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=KtLodestone)

KtLodestone is a parser for The Lodestone for the JVM platform (JDK 8+).

# Features

---

- Supports scraping the following information from The Lodestone:
  - Character
    - Profile
    - Class/job
    - Minions
    - Mounts
    - Achievements
  - Linkshells
  - Search
    - Characters
    - Linkshells
    - Free Companies
- Compatible with Kotlin and Java
  - Kotlin functions are suspendable
  - Java-compatible functions return a `CompletableFuture`

# Installation

---

KtLodestone is available from Maven Central:
```kotlin
dependencies {
    implementation("cloud.drakon.ktlodestone:7.0.0-SNAPSHOT")
}
```

# Documentation

---

Kotlin KDocs: [![kdoc](https://img.shields.io/badge/kdoc-7.0.0-SNAPSHOT-brightgreen)](https://drakon64.github.io/KtLodestone/)<br>
Javadocs: [![javadoc](https://javadoc.io/badge2/cloud.drakon/ktlodestone/javadoc.svg)](https://javadoc.io/doc/cloud.drakon/ktlodestone)

# Examples

---

## Getting information about a Lodestone character

Kotlin:
```kotlin
val character: CharacterProfile = getLodestoneCharacter(27545492) // Must be called from a coroutine or a suspendable function
val activeClassJob: ClassJob = character.activeClassJob.classJob // returns `ClassJob.RED_MAGE`
val activeClassJob: String = character.activeClassJob.classJob.toString() // returns "Red Mage"
val activeClassJobLevel: Byte = character.activeClassJob.level // returns `90`
val title: String = character.title // returns "Hope's Legacy"
```

Java:
```java
CharacterProfile character = KtLodestone.getCharacter(27545492).get(); // Async functions return a `CompletableFuture`
ClassJob activeClassJob = character.getActiveClassJob().getClassJob(); // returns `ClassJob.RED_MAGE`
String activeClassJobName = character.getActiveClassJob().getClassJob().toString(); // returns "Red Mage"
Byte activeClassJobLevel = character.getActiveClassJob().getLevel(); // returns `90`
String title = character.getTitle(); // returns "Hope's Legacy"
```
