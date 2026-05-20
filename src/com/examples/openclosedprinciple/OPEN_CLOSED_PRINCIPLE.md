# Open/Closed Principle (OCP)

## Quick summary
The Open/Closed Principle (OCP) is one of the SOLID design principles. It states:

"Software entities (classes, modules, functions, etc.) should be open for extension, but closed for modification."

In practice this means you should be able to add new behavior to a component without changing its existing source code. Instead of modifying, you extend by adding new code that the original code can use (typically via abstraction, interfaces, composition, or delegation).

---

## Why OCP matters
- Prevents regressions: changing well-tested code risks introducing bugs. Keeping stable code unchanged reduces that risk.
- Improves maintainability: adding features should mean adding new classes, not changing many places.
- Enables better extensibility: new requirements can be implemented by adding modules that plug into existing abstractions.

---

## A simple violation (see `Problem.java`)
The `Problem` example in this package demonstrates a typical OCP violation: a concrete `ProductFilter` class that adds new methods whenever a new filtering requirement appears.

Excerpt (simplified):

```java
class ProductFilter{
    public Stream<Product> filterByColor(List<Product> products, Color color){
        return products.stream().filter(product -> product.color == color);
    }

    public Stream<Product> filterBySize(List<Product> products, Size size){
        return products.stream().filter(product -> product.size == size);
    }

    public Stream<Product> filterByColorSize(List<Product> products, Color color, Size size){
        return products.stream().filter(product -> product.size == size && product.color == color);
    }
}
```

Problem: When a new requirement arrives (filter by weight, price range, combined rules, etc.) you need to modify `ProductFilter` to add more methods. That violates OCP — the class is not closed for modification.

---

## A better approach: open for extension, closed for modification
The `Solution.java` in this package demonstrates a standard way to apply OCP: define abstractions (interfaces) and implement new behavior by adding new classes rather than editing the filter. The example uses a `Specification` interface and a `Filter` interface.

Key ideas from `Solution.java`:
- `Specification<T>` encapsulates a boolean predicate for an item.
- `Filter<T>` is a generic filter that accepts any `Specification<T>`.
- To add a new filtering rule, implement a new `Specification` class (e.g., `ColorSpecification`, `SizeSpecification`, `AndSpecification`) without changing `BetterFilter`.

Excerpt (simplified):

```java
interface Specification<T>{
    boolean isSatisfied(T item);
}

interface Filter<T>{
    Stream<T> filter(List<T> items, Specification<T> spec);
}

class BetterFilter implements Filter<Product>{
    @Override
    public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
        return items.stream().filter(product -> spec.isSatisfied(product));
    }
}
```

To extend behavior, add a new `Specification<Product>` implementation. No changes required to `BetterFilter`.

---

## Common techniques that help satisfy OCP
- Abstraction (interfaces, abstract classes): program to an interface so concrete classes can be swapped or added.
- Composition and delegation: compose objects that implement small behaviors and combine them to form complex behavior.
- Strategy pattern: encapsulate algorithms in classes that implement a common interface and switch them at runtime.
- Decorator pattern: extend behavior by wrapping objects rather than modifying them.
- Template Method: keep invariant parts in a base class and allow subclasses to override variable parts.
- Specification pattern: model business rules as objects (as in the example above).

---

## How to refactor a violation step-by-step
1. Identify the area that changes frequently (e.g., many `if` branches or multiple methods added for each case).
2. Extract a small abstraction (an interface) that represents the varying behavior.
3. Replace concrete code that performs the varying behavior with calls to the abstraction.
4. Implement existing behaviors as classes that implement the abstraction.
5. Add composition helpers (e.g., `AndSpecification`, `OrSpecification`) if you need to combine behaviors.
6. Remove duplicated code and tests that relied on the old behavior only after the new implementations are proven.

Concrete mapping to this package:
- `ProductFilter` (violating) -> extract `Specification<Product>` and `Filter<Product>`.
- `BetterFilter` (stable) -> depends only on `Specification` and does not need changes when new specifications are added.

---

## Benefits and trade-offs
Benefits:
- New behavior is added without modifying tested, stable classes.
- Fewer merge conflicts and safer evolution.
- Clear separation of concerns; business rules modelled as objects.

Trade-offs / costs:
- More classes and interfaces; extra indirection can increase complexity.
- Over-engineering risk: not every tiny change needs a full specification hierarchy.
- May require upfront design effort when requirements are not stable.

When to apply OCP strictly:
- Public APIs that many clients depend on.
- Core business logic where changes are frequent and risky.

When to be pragmatic:
- Small internal classes with limited scope; sometimes a simple conditional is acceptable.
- Prototypes and throwaway code.

---

## Practical tips and checklist
- Prefer small, well-named interfaces for the behaviors that change.
- Keep concrete classes simple and focused (single responsibility helps OCP).
- Use composition over inheritance: combine behaviors instead of creating deep hierarchies.
- Write tests for the abstraction contract and for new implementations — this keeps the base code safe.
- Avoid premature abstraction: only extract when you see actual need or clear expected variation.

Quick checklist before refactor:
- Is the code changing frequently? (yes -> consider refactor)
- Are changes localized to a single module? (no -> likely good candidate)
- Will extracting an interface reduce duplication or conditional logic? (yes -> proceed)

---

## Summary
The Open/Closed Principle encourages designing modules that can be extended without modifying existing code. The `Problem.java`/`Solution.java` examples in this package provide a compact demonstration: move from a concrete filter that keeps being modified, to an abstraction-based design (Specification + Filter) where new filtering behavior is added by creating new specification classes.

Follow OCP to reduce regression risk and improve extensibility — but balance it against added complexity and avoid premature abstraction.

---

## References & further reading
- "Design Patterns: Elements of Reusable Object-Oriented Software" — Strategy, Decorator, Template Method
- Martin Fowler — Refactoring
- Robert C. Martin — SOLID principles



