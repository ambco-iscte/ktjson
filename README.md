<br>

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="resources/header-light.png">
  <source media="(prefers-color-scheme: light)" srcset="resources/header-dark.png">
  <img alt="ktjson" src="resources/header-dark.png">
</picture>

# What is this?
**kt**json is a dynamic JSON generation and manipulation library for Kotlin. In short, it allows for the dynamic 
generation of JSON representations of Kotlin data class instances, or the instantiation of data classes
from JSON representations. 

This library was developed as the final project for an [Advanced Programming](https://fenix.iscte-iul.pt/disciplinas/m4310/2022-2023/2-semestre) course as part of the 
[Master's (MSc) in Computer Engineering](https://www.iscte-iul.pt/course/12/master-msc-in-computer-engineering) programme at [Iscte-IUL](https://www.iscte-iul.pt/).
<br>

# Features
## Phase 1 - Abstract Model, Reflection
- [x] Abstract JSON model using Kotlin data classes
- [x] Basic JSON model visitor functionality (Visitor pattern)
- [x] Serialization of Kotlin objects to abstract JSON models
- [x] **(Extra)** Parsing of JSON-formatted text files to abstract JSON models
- [x] **(Extra)** Deserialization of abstract JSON models to Kotlin object instances

## Phase 2
Coming soon! (Hopefully.)

<br>

# Installation
Coming soon!

<br>

# Examples
The following examples showcase the basic functionality of **kt**json's serialization and deserialization
capabilities.

## Serialization
```kotlin
import dynamic.*
import model.*

fun serializationExample() {
    // Some data classes we want to serialize
    data class Student(val name: String, @Stringify val number: Int, @DoNotSerialize val internalID: Int)
    data class Professor(val name: String, val areasOfResearch: List<String>)
    data class CurricularUnit(
        @DoNotSerialize val internalID: String,
        @SerializeAs("name") val identifier: String,
        val coordinator: Professor,
        @SerializeAs("professors") val faculty: Set<Professor>,
        @SerializeAs("students") val studentBody: Set<Student>,
        val program: List<String>
    )

    // A particular instance of one of our data classes
    // Note the inclusion of collection and nested object properties
    val advancedProgramming = CurricularUnit(
        "M4310",
        "PA",
        Professor("André Santos", listOf("Pedagogy", "Programming", "Kotlin")),
        setOf(Professor("Probably ChatGPT", listOf("Everything"))),
        setOf(Student("Afonso", 92494, 0)),
        listOf("Kotlin", "Reflection", "Visitors")
    )

    // Pretty-print (built-in!) of the serialized JSON model
    // Try it out yourself!
    println(advancedProgramming.serialize())
}
```

## Deserialization
```kotlin
import dynamic.deserialize
import dynamic.serialize

fun deserializationExample() {
    // Note the absence of annotations - if we want to deserialize later, we can't change or
    // lose any information during the serialization process
    data class Student(val name: String, val number: Int, val studentType: StudentType, val internalID: Int)
    data class Professor(val name: String, val areasOfResearch: List<String>)
    data class CurricularUnit(
        val internalID: String,
        val identifier: String,
        val coordinator: Professor,
        val faculty: Set<Professor>,
        val studentBody: Set<Student>,
        val program: List<String>
    )

    // A particular instance of one of our data classes (same as in the example above)
    val advancedProgramming = CurricularUnit(
        "M4310",
        "PA",
        Professor("André Santos", listOf("Pedagogy", "Programming", "Strudel", "Weird Kotlin Things")),
        setOf(Professor("Probably ChatGPT", listOf("Everything"))),
        setOf(Student("Afonso", 92494, StudentType.INTERNATIONAL, 0)),
        listOf("Kotlin", "Reflection", "Visitors")
    )

    // Check that calling deserialize(thing.serialize()) just returns the same thing
    // We expect this to print "true" to the console. Try it out yourself!
    println(uc == CurricularUnit::class.deserialize(advancedProgramming.serialize()))
}
```

<br>

# Credit

Full credit for the basic specification of the library's requirements goes to Professor 
[André L. Santos](https://andre-santos-pt.github.io/), Assistant Professor at Iscte-IUL and coordinator of the course
this library was developed for. The original specifications for each phase of the course's final project can be found here:
- [Phase 1 - Abstract Model, Reflection](https://andre-santos-pt.github.io/projetojson/primeirafase)
- More phases coming soon!

Credit for all the code present in this repository goes to 
[Afonso Caniço](https://ciencia.iscte-iul.pt/authors/afonso-canico/cv), author and sole contributor to the project
and this repository, unless otherwise explicitly stated. Code heavily inspired by or taken from the Advanced Programming
[course materials](https://andre-santos-pt.github.io/kotlin/) is properly and explicitly identified.