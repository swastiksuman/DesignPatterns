# Liskov Substitution Principle (LSP) — explained by example

This document explains the Liskov Substitution Principle (LSP) using the `LSP.java` example in this package.

Checklist
- Explain what LSP means in plain words
- Walk through the `LSP.java` example and identify the violation
- Explain why the violation happens (behavioral subtyping)
- Show simple fixes / alternative designs with short code snippets
- Give practical guidelines for avoiding LSP violations

What is the Liskov Substitution Principle (short)

The Liskov Substitution Principle (LSP) is one of the SOLID principles. It states:

"If S is a subtype of T, then objects of type T may be replaced with objects of type S without altering any of the desirable properties of the program (correctness, task performed, etc.)."

In practice this means a subclass should behave in a way that does not surprise clients that expect the base type. Subtypes should preserve the observable behavior and contracts of their supertypes.

The example (what to look at)

Open `LSP.java` in this package. Key elements:

- `Rectangle` class with `width`, `height`, getters and setters, and `getArea()`.
- `Square` extends `Rectangle` and overrides `setWidth` / `setHeight` so that setting one side updates the other (to keep it square).
- `userIt(Rectangle r)` takes a `Rectangle`, reads the width, sets the height to 10, and prints the expected area (width * 10) vs the actual `r.getArea()`.

Why this is a violation

Consider the code flow in `userIt`:

1. Read `width = r.getWidth()` — the client expects width to remain the same unless explicitly changed.
2. `r.setHeight(10)` — the client intends to change only the height.
3. Expect the area to be `width * 10`.

When `r` is actually an instance of `Square`, calling `setHeight(10)` sets both height and width to 10. That changes the earlier read `width` unexpectedly, so the actual area does not match the client's expectation (`width * 10`).

This breaks behavioral substitutability: a `Square` cannot be substituted for a `Rectangle` without changing observable behavior. Therefore `Square` is not a correct subtype of `Rectangle` under LSP.

Why it matters (behavioral subtyping)

- Clients rely on the documented/implicit contract of the base type (`Rectangle`) — e.g., setters only change the property they name.
- A subtype overriding behavior in a way that invalidates those assumptions will produce surprising results and bugs.
- LSP is about contracts and expectations, not just about method signatures.

Fixes and alternative designs

There are multiple ways to resolve this design problem depending on intent.

1) Separate hierarchies (preferred when square and rectangle are conceptually different):

Make a common `Shape` interface (or abstract class) that exposes behaviors both shapes can provide (e.g., `getArea()`), and implement `Rectangle` and `Square` separately without inheriting setters that don't make sense.

Example:

```java
public interface Shape {
    int getArea();
}

public class Rectangle implements Shape {
    private final int width;
    private final int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getArea() { return width * height; }
}

public class Square implements Shape {
    private final int size;

    public Square(int size) { this.size = size; }

    public int getArea() { return size * size; }
}
```

Now both `Rectangle` and `Square` are `Shape`s but there is no confusing setter contract to violate.

2) Make `Rectangle` immutable or remove setters that cause the problem

If a `Rectangle` does not expose independent setters for width and height, the client cannot perform the problematic sequence of operations that led to the violation. For example, use constructors to set dimensions and avoid public `setWidth`/`setHeight`.

3) Keep inheritance but change the contract (rarely recommended)

You could document that `Rectangle#setHeight` may affect width in subclasses — but that is fragile. Changing a base type's documented behavior to accommodate a subtype generally makes the API worse for other users. This approach is discouraged.

4) Use specialized interfaces for mutability

Split responsibilities into small interfaces, e.g., `HasWidth`, `HasHeight`, `Resizable`. Only types that truly support independent resizing implement them. Client code should depend on the smallest interface it needs.

Example:

```java
public interface Resizable {
    void setWidth(int w);
    void setHeight(int h);
}

// Rectangle implements Resizable, Square does not; Square can provide its own API.
```

How to spot similar LSP violations

- Look for overrides that change state in ways the superclass didn't promise (e.g., setters that affect unrelated fields).
- Look for changes in method side effects or return value ranges (e.g., a subclass method throwing more exceptions or returning values outside the documented range).
- Tests that pass with base-class instances but fail with subclass instances are a strong sign.

Practical guidelines

- Favor composition over inheritance when subclassing would change the base class contract.
- Keep base-class contracts narrow and well-documented.
- When a subtype must break a superclass contract, do not inherit — create a separate class or adjust the design.
- Use interfaces to express behavior (e.g., `Shape`, `Resizable`) rather than forcing dramatic behavioral changes on subclasses.

Summary (applied to the example)

- Problem: `Square` inherits from `Rectangle` but overrides setters so that changing one dimension changes the other. That invalidates client assumptions and breaks LSP.
- Fix: Model `Square` and `Rectangle` as sibling implementations of a common `Shape` interface, or remove conflicting setters and use immutable objects.

References / further reading

- Barbara Liskov's original definition and later refinements
- "Design Patterns" and SOLID principle references for composition and interface segregation

---
File: `src/com/examples/liskovsubstitutionprinciple/LSP.java` shows the concrete example used above.

