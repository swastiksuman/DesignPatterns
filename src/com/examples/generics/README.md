# Java Generics Guide

## What are Generics?

Generics let you write **classes, interfaces, and methods that work with types as parameters**.
Instead of hard-coding a single type, you can write code that works for `String`, `Integer`, custom objects, and more.

The main benefit is **type safety**:
- fewer casts
- fewer runtime `ClassCastException`s
- clearer, reusable APIs

---

## Why use Generics?

Without generics, you often have to work with `Object`:

```java
Object value = "Hello";
String text = (String) value;
```

With generics, the compiler knows the type for you:

```java
GenericsType<String> value = new GenericsType<>();
value.set("Hello");
String text = value.get();
```

This is safer and easier to read.

---

## Files in this package

### `GenericsType.java`
Shows a **generic class**:
- `GenericsType<T>`
- how to store and retrieve a type-safe value
- why raw types should be avoided
- common type parameter naming conventions like `T`, `E`, `K`, and `V`

### `GenericsMethods.java`
Shows a **generic method**:
- `public static <T> boolean isEqual(...)`
- compiler **type inference**
- how generic methods can work without manually specifying type arguments every time

### `GenericsWildCards.java`
Shows **wildcards and bounds**:
- `List<? extends Number>`
- reading from a bounded collection
- why `List<Long>` is not a subtype of `List<Number>`
- the subtyping limitation of generics

---

## 1. Generic Class

A generic class stores a value whose type is decided when the class is used.

Example from `GenericsType.java`:

```java
public class GenericsType<T> {
    private T t;

    public T get() {
        return this.t;
    }

    public void set(T t1) {
        this.t = t1;
    }
}
```

Usage:

```java
GenericsType<String> type = new GenericsType<>();
type.set("Swastik");
String value = type.get();
```

### Why it helps
The compiler ensures you only put `String` values into `GenericsType<String>`.

---

## 2. Generic Methods

A generic method declares its own type parameter.

Example from `GenericsMethods.java`:

```java
public static <T> boolean isEqual(GenericsType<T> g1, GenericsType<T> g2) {
    return g1.get().equals(g2.get());
}
```

Usage:

```java
GenericsType<String> g1 = new GenericsType<>();
GenericsType<String> g2 = new GenericsType<>();

boolean equal = GenericsMethods.isEqual(g1, g2);
```

### Type inference
Java often infers the type automatically, so you do not always need to write:

```java
GenericsMethods.<String>isEqual(g1, g2);
```

The compiler can usually figure it out from the arguments.

---

## 3. Wildcards

Wildcards help when you want flexibility with generic types.

Example from `GenericsWildCards.java`:

```java
public static double sum(List<? extends Number> list) {
    double sum = 0;
    for (Number n : list) {
        sum += n.doubleValue();
    }
    return sum;
}
```

This method accepts:
- `List<Integer>`
- `List<Double>`
- `List<Long>`
- any list of a type that extends `Number`

### Why `? extends Number`?
It means:
- “I only need to read numbers from this list”
- “I do not intend to add new values to it”

---

## 4. Why `List<Long>` is not a `List<Number>`

This is a very common question.

Even though `Long` extends `Number`, this does **not** mean:

```java
List<Long> = List<Number>
```

That would be unsafe because then you could add a `Double` into a list that was originally meant for `Long` values.

That is why generics are **invariant**.

---

## 5. Raw Types

Raw types are the old pre-generic style.

Example from `GenericsType.java`:

```java
GenericsType type1 = new GenericsType();
```

This is allowed, but not recommended.

### Why avoid raw types?
- they remove compile-time type safety
- they can lead to warnings
- they can cause runtime bugs

Prefer:

```java
GenericsType<String> type = new GenericsType<>();
```

---

## Common Type Parameter Naming Conventions

These names are commonly used:

- `T` → Type
- `E` → Element
- `K` → Key
- `V` → Value
- `N` → Number
- `S`, `U`, `V` → additional type parameters

These names make generic code easier to read.

---

## When to use Generics

Use generics when you want to:
- write reusable code
- keep types safe at compile time
- avoid repeated casts
- work with collections and utility methods

Examples:
- collection classes
- helper methods
- reusable wrappers
- data structures like stacks, queues, and pairs

---

## When not to overuse Generics

Generics are useful, but do not make code overly complex.

Avoid unnecessary generics when:
- a method or class only works with one specific type
- the generic type makes the API harder to understand
- the code is simpler without it

Keep the design simple and readable.

---

## Practical rules

- Prefer `GenericsType<String>` over raw `GenericsType`
- Use generic methods when the type only matters inside the method
- Use wildcards when you want flexible read-only behavior
- Remember that `List<Integer>` is not a subtype of `List<Number>`
- Avoid raw types unless you are working with legacy code

---

## Quick examples to try

Run the examples from the project root:

```bash
javac -d out src/com/examples/generics/*.java
java -cp out com.examples.generics.GenericsType
java -cp out com.examples.generics.GenericsMethods
java -cp out com.examples.generics.GenericsWildCards
```

---

## Summary

Java generics help you write code that is:
- safer
- reusable
- easier to read
- less error-prone

Use:
- **generic classes** when a class stores a type
- **generic methods** when a method works with different types
- **wildcards** when you need flexible type bounds

Generics are one of the most important features for writing clean modern Java code.

