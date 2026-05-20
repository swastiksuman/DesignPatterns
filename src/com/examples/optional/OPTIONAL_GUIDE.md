# Java Optional: A Complete Guide

## Introduction

`Optional` is a container object which may or may not contain a non-null value. Introduced in Java 8, it provides a way to handle null values more elegantly and avoid `NullPointerException` errors.

Instead of:
```java
if (value != null) {
    // use value
}
```

You can use:
```java
Optional.ofNullable(value).ifPresent(v -> /* use v */);
```

---

## What is Optional?

`Optional<T>` is a generic container that wraps a value of type `T`. It can be in one of two states:
- **Present**: Contains a non-null value
- **Empty**: Contains no value

### Key Points:
- `Optional` is immutable
- It's a wrapper, not a replacement for null
- It's designed to be **not nullable** itself (though technically it can be)
- It encourages more functional programming style

---

## Creating Optional Instances

### 1. `Optional.of(T value)`
Creates an Optional with a non-null value. Throws `NullPointerException` if value is null.

```java
Optional<String> opt = Optional.of("Hello");
```

**Use when:** You are certain the value is not null.

### 2. `Optional.ofNullable(T value)`
Creates an Optional that may contain a null value.

```java
Optional<String> opt = Optional.ofNullable(null);      // Empty
Optional<String> opt = Optional.ofNullable("Hello");   // Present
```

**Use when:** The value might be null.

### 3. `Optional.empty()`
Creates an empty Optional with no value.

```java
Optional<String> opt = Optional.empty();
```

**Use when:** You explicitly need an empty Optional.

---

## Common Methods and Operations

### Checking Presence

#### `isPresent()`
Returns `true` if a value is present, `false` otherwise.

```java
Optional<String> opt = Optional.of("Hello");
if (opt.isPresent()) {
    System.out.println(opt.get());  // Output: Hello
}
```

#### `isEmpty()` (Java 11+)
Returns `true` if no value is present, `false` otherwise.

```java
Optional<String> opt = Optional.empty();
if (opt.isEmpty()) {
    System.out.println("No value present");
}
```

### Retrieving Values

#### `get()`
Returns the value if present, throws `NoSuchElementException` if empty.

```java
Optional<String> opt = Optional.of("Hello");
String value = opt.get();  // Output: "Hello"

Optional<String> empty = Optional.empty();
String value = empty.get();  // Throws NoSuchElementException
```

**Warning:** Use sparingly! It defeats the purpose of Optional.

#### `orElse(T other)`
Returns the value if present, otherwise returns the provided default value.

```java
Optional<String> opt = Optional.empty();
String value = opt.orElse("Default");  // Output: "Default"
```

#### `orElseGet(Supplier<? extends T> supplier)`
Returns the value if present, otherwise invokes the supplier and returns its result.

```java
Optional<String> opt = Optional.empty();
String value = opt.orElseGet(() -> "Default from supplier");
```

**Difference from orElse():** The supplier is only called if the Optional is empty (lazy evaluation).

#### `orElseThrow(Supplier<? extends X> exceptionSupplier)`
Returns the value if present, otherwise throws the supplied exception.

```java
Optional<String> opt = Optional.empty();
String value = opt.orElseThrow(() -> new IllegalArgumentException("Value not found"));
```

### Transforming Values

#### `map(Function<? super T, ? extends U> mapper)`
Applies a function to the value if present and returns a new Optional with the result.

```java
Optional<String> opt = Optional.of("Hello");
Optional<Integer> length = opt.map(String::length);  // Optional[5]
```

#### `flatMap(Function<? super T, ? extends Optional<? extends U>> mapper)`
Like `map()`, but the mapper returns an Optional. Flattens nested Optionals.

```java
Function<String, Optional<Integer>> parse = s -> {
    try {
        return Optional.of(Integer.parseInt(s));
    } catch (NumberFormatException e) {
        return Optional.empty();
    }
};

Optional<String> opt = Optional.of("123");
Optional<Integer> result = opt.flatMap(parse);  // Optional[123]
```

#### `filter(Predicate<? super T> predicate)`
Returns the same Optional if the value matches the predicate, otherwise returns an empty Optional.

```java
Optional<String> opt = Optional.of("Hello");
Optional<String> filtered = opt.filter(s -> s.length() > 3);  // Optional["Hello"]

Optional<String> filtered2 = opt.filter(s -> s.length() > 10);  // Optional.empty
```

### Side Effects

#### `ifPresent(Consumer<? super T> action)`
If a value is present, performs the given action with the value.

```java
Optional<String> opt = Optional.of("Hello");
opt.ifPresent(System.out::println);  // Output: Hello

Optional<String> empty = Optional.empty();
empty.ifPresent(System.out::println);  // No output
```

#### `ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction)` (Java 9+)
If a value is present, performs the first action with the value, otherwise performs the empty action.

```java
Optional<String> opt = Optional.of("Hello");
opt.ifPresentOrElse(
    System.out::println,
    () -> System.out.println("No value")
);  // Output: Hello
```

---

## When to Use Optional

### ✅ USE Optional When:

1. **Representing Optional Method Results**
   ```java
   public Optional<User> findUserById(int id) {
       // Return Optional instead of null
   }
   ```

2. **Functional Programming Chains**
   ```java
   Optional<String> result = Optional.ofNullable(data)
       .map(String::toUpperCase)
       .filter(s -> s.length() > 3)
       .ifPresent(System.out::println);
   ```

3. **Cleaner Null Checks**
   ```java
   // Instead of:
   if (user != null && user.getAddress() != null) {
       String city = user.getAddress().getCity();
   }
   
   // Use:
   Optional<String> city = Optional.ofNullable(user)
       .flatMap(u -> Optional.ofNullable(u.getAddress()))
       .map(Address::getCity);
   ```

4. **Default Values**
   ```java
   String result = Optional.ofNullable(input)
       .orElse("default value");
   ```

5. **Throwing Exceptions when Values are Missing**
   ```java
   User user = Optional.ofNullable(findUser(id))
       .orElseThrow(() -> new EntityNotFoundException("User not found"));
   ```

---

### ❌ DON'T USE Optional When:

1. **Field Variables in Classes**
   ```java
   // Bad
   public class Person {
       private Optional<String> name;  // Don't use Optional for fields
   }
   
   // Good
   public class Person {
       private String name;  // Use null or provide a default
   }
   ```

2. **Constructor/Method Parameters**
   ```java
   // Bad
   public void setUser(Optional<User> user) { ... }
   
   // Good - Overload methods or use builder pattern
   public void setUser(User user) { ... }
   ```

3. **Collections (Use Empty Collections Instead)**
   ```java
   // Bad
   public Optional<List<String>> getNames() { ... }
   
   // Good
   public List<String> getNames() {
       return Collections.emptyList();  // or new ArrayList<>()
   }
   ```

4. **When You're Sure Value Won't Be Null**
   ```java
   // Unnecessary
   Optional<String> opt = Optional.of(UUID.randomUUID().toString());
   ```

5. **Complex Logic with Multiple OrElse Checks**
   ```java
   // Getting too complex - use if-else instead
   Optional<String> opt = Optional.ofNullable(value1)
       .or(() -> Optional.ofNullable(value2))
       .or(() -> Optional.ofNullable(value3));
   ```

---

## Quick checklist: When NOT to use Optional

- Do NOT use Optional for fields in your domain objects; expose a plain getter and (if useful) a separate `getXOptional()`.
- Do NOT accept Optional as method/constructor parameters; prefer overloads or nullable parameters with clear javadoc.
- Do NOT return Optional inside collections (e.g., `List<Optional<T>>`); return an empty collection instead.
- Avoid Optional in hot loops or performance-sensitive code paths; it creates small allocations.
- Avoid `get()` without checks — prefer `map`/`flatMap`/`ifPresent`/`orElse*`.

---

## Practical Examples

### Example 1: Safe Navigation
```java
public class Person {
    private Address address;
    
    public Optional<Country> getCountry() {
        return Optional.ofNullable(address)
            .map(Address::getCountry);
    }
}

// Usage
Optional<Country> country = person.getCountry();
country.ifPresent(c -> System.out.println(c.getName()));
```

### Example 2: Parsing with Error Handling
```java
public Optional<Integer> parseInteger(String value) {
    try {
        return Optional.of(Integer.parseInt(value));
    } catch (NumberFormatException e) {
        return Optional.empty();
    }
}

// Usage
String input = "42";
parseInteger(input)
    .map(n -> n * 2)
    .filter(n -> n > 50)
    .ifPresentOrElse(
        System.out::println,
        () -> System.out.println("Invalid or filtered out")
    );
```

### Example 3: Chaining Operations (from OptionalMain.java)
```java
Function<String, String> getSecondWord = (String s) -> {
    return s.split(" ").length > 1 ? s.split(" ")[1] : null;
};

Function<String, Integer> getLetterCount = String::length;

// Using Optional to handle potential null
Optional.ofNullable(getSecondWord.apply("Swastik Suman"))
    .map(getLetterCount)
    .ifPresent(System.out::println);  // Output: 5
```

#### Refactored (recommended)
Use a helper that returns `Optional` directly to avoid nullable intermediates and make chains expressive and safe:

```java
static Optional<String> getSecondWordOptional(String s) {
    String[] parts = s.split(" ");
    return parts.length > 1 ? Optional.of(parts[1]) : Optional.empty();
}

// Usage
getSecondWordOptional("Swastik Suman")
    .map(String::length)
    .ifPresent(System.out::println); // Output: 5
```

### Example 4: Configuration with Defaults
```java
public class ConfigLoader {
    private Optional<Properties> props;
    
    public String getProperty(String key, String defaultValue) {
        return props.flatMap(p -> Optional.ofNullable(p.getProperty(key)))
                    .orElse(defaultValue);
    }
}
```

---

## Best Practices

1. **Use `orElse()` vs `orElseGet()`**
   - Use `orElse()` for simple constant values
   - Use `orElseGet()` for expensive computations (lazy evaluation)

2. **Avoid Chaining Multiple `get()` Calls**
   ```java
   // Bad
   if (opt.isPresent()) {
       String value = opt.get();
   }
   
   // Good
   opt.ifPresent(value -> System.out.println(value));
   ```

3. **Use `filter()` Before Accessing**
   ```java
   // Bad
   if (opt.isPresent() && opt.get().length() > 5) { ... }
   
   // Good
   opt.filter(s -> s.length() > 5).ifPresent(System.out::println);
   ```

4. **Don't Use Optional in Collections**
   ```java
   // Bad
   List<Optional<String>> list = new ArrayList<>();
   
   // Good
   List<String> list = new ArrayList<>();
   ```

5. **Consider Your Use Case**
   - Optional is best for return values, not internal state
   - Use it to make the API contract clear (this method may not return a value)

---

## Summary

| Method | Use Case |
|--------|----------|
| `of(T)` | Certain the value is not null |
| `ofNullable(T)` | Value might be null |
| `empty()` | Create empty Optional |
| `isPresent()` | Check if value exists |
| `isEmpty()` | Check if value is absent (Java 11+) |
| `get()` | Get value (risky, can throw exception) |
| `orElse(T)` | Provide default value |
| `orElseGet(Supplier)` | Provide default via supplier (lazy) |
| `orElseThrow(Supplier)` | Throw exception if empty |
| `map(Function)` | Transform value |
| `flatMap(Function)` | Transform and flatten Optional |
| `filter(Predicate)` | Keep value if condition matches |
| `ifPresent(Consumer)` | Execute action if present |
| `ifPresentOrElse(Consumer, Runnable)` | Execute action based on presence |

Optional is a powerful tool for writing more functional and null-safe Java code. Use it wisely and consistently!
