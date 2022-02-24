## Goals

Following this style guide should:

* Make it easier to read and begin understanding unfamiliar code.
* Make code easier to maintain.
* Reduce simple programmer errors.
* Reduce cognitive load while coding.
* Keep discussions on diffs focused on the code's logic rather than its style.

Note that brevity is not a primary goal. Code should be made more concise only if other good code qualities (such as readability, simplicity, and clarity) remain equal or are improved.

## Guiding Tenets

* The [official Android API design guidelines](https://android.googlesource.com/platform/developers/docs/+/refs/heads/master/api-guidelines/index.md) are all unilaterally accepted for any public API and generally have good information for private or internal APIs. If you are not making a public API we do not require the same level of documentation, but we still recommend that you adhere to the naming conventions and general design guidelines.
* These rules should not fight Android Studio's <kbd>⌥</kbd> + <kbd>⌘</kbd> + <kbd>L</kbd> indentation behavior.

## How to read the guides
Each guide is broken into a few sections. Sections contain a list of guidelines. Each guideline starts with one of these words:

- **DO** guidelines describe practices that should always be followed. There will almost never be a valid reason to stray from them.
- **DON’T** guidelines are the converse: things that are almost never a good idea. 
- **PREFER** guidelines are practices that you should follow. However, there may be circumstances where it makes sense to do otherwise. Just make sure you understand the full implications of ignoring the guideline when you do.
- **AVOID** guidelines are the dual to “prefer”: stuff you shouldn’t do but where there may be good reasons to on rare occasions.
- **CONSIDER** guidelines are practices that you might or might not want to follow, depending on circumstances, precedents, and your own preference.

## Table of Contents

- [Naming](#naming)
  + [Packages](#packages)
  + [Classes & Interfaces](#classes--interfaces)
  + [Methods](#methods)
  + [Fields](#fields)
  + [Variables & Parameters](#variables--parameters)
  + [Misc](#misc)
- [Declarations](#declarations)
  + [Visibility Modifiers](#visibility-modifiers)
  + [Fields & Variables](#fields--variables)
  + [Classes](#classes)
  + [Data Type Objects](#data-type-objects)
  + [Enum Classes](#enum-classes)
- [Spacing](#spacing)
  + [Indentation](#indentation)
  + [Line Length](#line-length)
  + [Vertical Spacing](#vertical-spacing)
- [Getters & Setters](#getters--setters)
- [Brace Style](#brace-style)
- [When Statements](#when-statements)
- [Types](#types)
  + [Type Inference](#type-inference)
  + [Constants vs. Variables](#constants-vs-variables)
  + [Companion Objects](#companion-objects)
  + [Optionals](#optionals)
- [Language](#language)


## Naming

On the whole, naming should follow Java standards, as Kotlin is a JVM-compatible language.

### Packages

Package names are similar to Java: all __lower-case__, multiple words concatenated together, without hypens or underscores:

**DON'T**

```kotlin
com.WWT.jet-flow
```

**DO**

```kotlin
com.wwt.jetflow
```

### Classes & Interfaces

Written in __UpperCamelCase__. For example `JetflowApp`. 

### Methods

Written in __lowerCamelCase__. For example `setValue`.

### Fields

Generally, written in __lowerCamelCase__. Fields should **not** be named with Hungarian notation, as Hungarian notation is [erroneously thought](http://jakewharton.com/just-say-no-to-hungarian-notation/) to be recommended by Google.

Example field names:

```kotlin
class MyClass {
  var publicField: Int = 0
  val person = Person()
  private var privateField: Int?
}
```

Constant values in the companion object should be written in __uppercase__, with an underscore separating words:

```kotlin
companion object {
  const val THE_ANSWER = 42
}
```

### Variables & Parameters

Written in __lowerCamelCase__.

**AVOID** Single character values, except for temporary looping variables.

### Misc

In code, acronyms should be treated as words. For example:

**DON'T**

```kotlin
XMLHTTPRequest
URL: String? 
findPostByID
```
**DO**

```kotlin
XmlHttpRequest
url: String
findPostById
```

## Declarations

### Visibility Modifiers

Only include visibility modifiers if you need something other than the default of public.

**DON'T**

```kotlin
public val wideOpenProperty = 1
private val myOwnPrivateProperty = "private"
```

**DO**

```kotlin
val wideOpenProperty = 1
private val myOwnPrivateProperty = "private"
```

### Access Level Modifiers

Access level modifiers should be explicitly defined for classes, methods and member variables.

### Fields & Variables

Prefer single declaration per line.

**DO**

```kotlin
username: String
twitterHandle: String
```

### Classes

Exactly one class per source file, although inner classes are encouraged where scoping appropriate.

### Data Type Objects

Prefer data classes for simple data holding objects.

**DON'T**

```kotlin
class Person(val name: String) {
  override fun toString() : String {
    return "Person(name=$name)"
  }
}
```

**DO**

```kotlin
data class Person(val name: String)
```

### Enum Classes

Enum classes without methods may be formatted without line-breaks, as follows:

```kotlin
private enum CompassDirection { EAST, NORTH, WEST, SOUTH }
```
## Spacing

### Indentation

Indentation is using spaces - never tabs.

### Line Length

**PREFER** Lines should be no longer than 100 characters.

### Vertical Spacing

There should be exactly one blank line between methods to aid in visual clarity and organization. Whitespace within methods should separate functionality, but having too many sections in a method often means you should refactor into several methods.

## Comments

When they are needed, use comments to explain **why** a particular piece of code does something. Comments must be kept up-to-date or deleted.

Avoid block comments inline with code, as the code should be as self-documenting as possible. *Exception: This does not apply to those comments used to generate documentation.*

## Getters & Setters

Unlike Java, direct access to fields in Kotlin is preferred. 

If custom getters and setters are required, they should be declared [following Kotlin conventions](https://kotlinlang.org/docs/reference/properties.html) rather than as separate methods.

## Brace Style

Only trailing closing-braces are awarded their own line. All others appear the same line as preceding code:

**DON'T**

```kotlin
class MyClass
{
  fun doSomething()
  {
    if (someTest)
    {
      // ...
    }
    else
    {
      // ...
    }
  }
}
```

**DO**

```kotlin
class MyClass {
  fun doSomething() {
    if (someTest) {
      // ...
    } else {
      // ...
    }
  }
}
```

Conditional statements are always required to be enclosed with braces, irrespective of the number of lines required.

**DON'T**

```kotlin
if (someTest)
  doSomething()
if (someTest) doSomethingElse()
```

**DO**

```kotlin
if (someTest) {
  doSomething()
}
if (someTest) { doSomethingElse() }
```

## When Statements

Unlike `switch` statements in Java, `when` statements do not fall through. Separate cases using commas if they should be handled the same way. Always include the else case.

**DON'T**

```kotlin
when (anInput) {
  1 -> doSomethingForCaseOneOrTwo()
  2 -> doSomethingForCaseOneOrTwo()
  3 -> doSomethingForCaseThree()
}
```

**DO**

```kotlin
when (anInput) {
  1, 2 -> doSomethingForCaseOneOrTwo()
  3 -> doSomethingForCaseThree()
  else -> println("No case satisfied")
}
```


## Types 

Always use Kotlin's native types when available. In Kotlin, everything is an object, which means we can call member function and properties on any variable. **[Read more about Kotin Types](https://kotlinlang.org/docs/basic-types.html)**

### Type Inference

Type inference should be preferred where possible to explicitly declared types. 

**DON'T**

```kotlin
val something: MyType = MyType()
val meaningOfLife: Int = 42
```

**DO**

```kotlin
val something = MyType()
val meaningOfLife = 42
```

### Constants vs. Variables 

Constants are defined using the `val` keyword, and variables with the `var` keyword. Always use `val` instead of `var` if the value of the variable will not change.

**CONSIDER** defining everything using `val` and only change it to `var` if the compiler complains!

### Companion Objects

Kotlin doesn’t have static members or member functions. Companion objects are singleton objects whose properties and functions are tied to a class but not to the instance of that class — basically like the “static” keyword in Java but with a twist.

Let’s look at some simple example:
class YourClass {
    companion object { // Equivalent to "companion object Companion"
    }
}
Companion objects allow their members to be accessed from inside the companion class without specifying the name.

At the same time, visible members can be accessed from outside the class when prefixed by the class name:

class YourClass {
    companion object {
        val message = "This is companion object"
    }
}

to access message value just call YourClass.message

Read more about [companion objects](https://kotlinlang.org/docs/object-declarations.html#companion-objects)

### Nullable Types

Declare variables and function return types as nullable with `?` where a `null` value is acceptable.

Use implicitly unwrapped types declared with `!!` only for instance variables that you know will be initialized before use, such as subviews that will be set up in `onCreate` for an Activity or `onCreateView` for a Fragment.

When naming nullable variables and parameters, avoid naming them like `nullableString` or `maybeView` since their nullability is already in the type declaration.

When accessing a nullable value, use the safe call operator if the value is only accessed once or if there are many nullables in the chain:

```kotlin
editText?.setText("foo")
```

## Language

Use `en-US` English spelling. 🇺🇸

**DON'T**

```kotlin
val colourName = "red"
```

**DO**

```kotlin
val colorName = "red"
```
**[⬆ back to top](#table-of-contents)**

## Attribution:
- Parts of the styleguide also inspired by [Android Kotlin style guide](https://android.github.io/kotlin-guides/style.html), Thanks Google!
- Parts of the styleguide also inspired by [The Official raywenderlich.com Kotlin Style Guide](https://github.com/raywenderlich/kotlin-style-guide), Thanks Ray Wenderlich!
