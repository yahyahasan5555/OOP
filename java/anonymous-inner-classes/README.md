# Anonymous Inner Classes in Java

Lecture notes + Java code.

| File                                                                                                          | What it has                                                       |
| ------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------- |
| [`Main.java`](https://github.com/yahyahasan5555/OOP/blob/main/java/anonymous-inner-classes/Main.java)         | all the examples, with the expected output at the end             |
| [`Reward.java`](https://github.com/yahyahasan5555/OOP/blob/main/java/anonymous-inner-classes/Reward.java)     | a normal class (used to extend)                                   |
| [`Showable.java`](https://github.com/yahyahasan5555/OOP/blob/main/java/anonymous-inner-classes/Showable.java) | an interface (used to implement)                                  |
| [`Greeter.java`](https://github.com/yahyahasan5555/OOP/blob/main/java/anonymous-inner-classes/Greeter.java)   | an interface whose method takes a parameter (lambda example)      |
| [`Animal.java`](https://github.com/yahyahasan5555/OOP/blob/main/java/anonymous-inner-classes/Animal.java)     | an **abstract** class with a constructor and an initializer block |

---

## 1. What is an anonymous inner class?

A class **without a name**, declared and instantiated **in one expression**.

- It makes the code **more concise**.
- It lets you **declare and create** a class at the same time.
- It is like a **local class**, except that it **has no name**.
- It is an **inner class** (written inside another class) that is declared without a name, so it is called **nameless / anonymous**.

### The problem it solves

Every employee gets a `10$` reward, but the 4th employee should get `20$`.

```java
Reward emp4 = new Reward() {                 // "new Reward() { ... }" is NOT a Reward object:
    @Override                                // it is an object of a NEW nameless class that EXTENDS Reward
    public void rewardMethod() {
        System.out.println("Your reward is 20$");
    }
};                                           // <- the semicolon ends the variable declaration
emp4.rewardMethod();                         // Your reward is 20$
```

|Option|How|Cost|
|---|---|---|
|1|write another class `Reward20 extends Reward`|a whole new named class for one object|
|2|**an anonymous inner class**|just the changed part, in place|

---

## 2. The syntax

```java
new ParentType(constructor arguments) {
    // fields, initializer block, methods (override the parent's methods)
}
```

`ParentType` is a **class** (the anonymous class extends it) or an **interface** (the anonymous class implements it).

---

## 3. Three ways to use it

|#|Way|Example|
|---|---|---|
|1|**Extends** another class (concrete **or abstract**)|`new Reward() { ... }`, `new Animal("Rex") { ... }`|
|2|**Implements** an interface|`new Showable() { ... }`|
|3|Defined **as an argument** of a method|`print(new Showable() { ... });`|

### 3.1 Extends a class

```java
Reward emp4 = new Reward() { ... };         // concrete class
Animal dog  = new Animal("Rex") { ... };    // abstract class: we must implement its abstract methods
```

### 3.2 Implements an interface

```java
// Showable showable = new Showable();      // ERROR: Showable is abstract; cannot be instantiated
Showable showable = new Showable() {
    @Override
    public void print() {
        System.out.println("Showable interface");
    }
};
showable.print();
```

### 3.3 As a method argument

Instead of writing a whole class that implements `Showable` just to pass it to `print(Showable sh)`:

```java
print(new Showable() {
    @Override
    public void print() {
        System.out.println("Showable as an argument");
    }
});
```

### Use it only once

If you need the object only for one call, you don't even need a variable:

```java
new Reward() {
    @Override
    public void rewardMethod() {
        System.out.println("Your reward is 50$");
    }
}.rewardMethod();
```

---

## 4. What is its name?

```java
System.out.println(emp1.getClass());                    // class Reward
System.out.println(emp4.getClass());                    // class Main$1
System.out.println(emp4.getClass().getSuperclass());    // class Reward
System.out.println(emp4.getClass().isAnonymousClass()); // true
```

- `Main$1` = **`Main`** (the **enclosing** class where it is written) + **`$`** (it is an inner class) + **`1`** (the number of the first anonymous class in `Main`, then `Main$2`, ...).
- **`Main` is not its parent.** Its parent (superclass) is **`Reward`**.
- The compiler creates a separate file for it: `Main$1.class`.

---

## 5. Rules and limits

### One parent only

An anonymous class can **extend one class OR implement one interface**. Not both, and not two interfaces. (`new Reward() implements Showable { }` is a syntax error.)

### Constructors and initializer blocks

- It has **no name**, so it **can't have a constructor** of its own.
- But it **can call the parent's constructor** (any type: default, no-args, parametrized) with arguments: `new Animal("Rex") { ... }`.
- With an **interface** there is no constructor, so the parentheses must stay **empty** (`new Showable(5) { }` is an error: _anonymous class implements interface; cannot have arguments_).
- It **can have an initializer block** `{ ... }`.

```java
Animal dog = new Animal("Rex") {
    { System.out.println("  [anonymous] initializer block"); }

    @Override
    public void sound() { System.out.println(name + " says Woof"); }
};
```

Output:

```
  [Animal] initializer block
  [Animal] constructor: Rex
  [anonymous] initializer block
```

Order: **the parent first** (its initializer block, then its constructor), **then** the anonymous class's initializer block. The rule is the same for any class: the parent part is finished first, then the class's own initializer block.

### Extra fields and methods

You can add **extra fields and methods**, and use the fields to change what the overridden method prints. But you can't call an extra method **through the parent type**:

```java
Animal cat = new Animal("Tom") {
    int lives = 9;                                // extra field: OK
    @Override public void sound() { System.out.println(name + " says Meow (lives: " + lives + ")"); }
    void climb() { System.out.println(name + " is climbing"); }   // extra method: OK
};
cat.sound();     // OK
// cat.climb();  // ERROR: cannot find symbol (the type of `cat` is Animal, and Animal has no climb())
```

It works in two cases:

```java
new Animal("Leo") { public void sound() { } void roar() { ... } }.roar();   // the call is on the creation expression

var lion = new Animal("Simba") { public void sound() { } void roar() { ... } };
lion.roar();                                                                 // `var` keeps the anonymous class's own type
```

### Using local variables

It can use the local variables of the enclosing method if they are **final or effectively final** (never changed after the first assignment):

```java
int bonus = 5;
Reward emp5 = new Reward() {
    @Override public void rewardMethod() { System.out.println("Your reward is " + (10 + bonus) + "$"); }
};
// bonus++;   // ERROR: local variables referenced from an inner class must be final or effectively final
```

### Static members

|Java version|Static members in an anonymous class|
|---|---|
|**Before Java 16**|**not allowed**: no static methods, no static blocks, no static variables. **Except constants**: `static final` with a compile-time value (`static final int K = 5;`). A `static final List<Integer> K = new ArrayList<>();` is **not** a constant, so it is an error.|
|**Java 16 and later**|allowed|


---

## 6. The `var` keyword (Java 10+)

The compiler **infers the type** from the value on the right side.

```java
var a = 234;                        // int
var b = 'c';                        // char
var c = "Class type";               // String
var d = new Reward();               // Reward
var list = new ArrayList<String>(); // ArrayList<String>
```

Why it matters here: with `var`, the variable gets the **anonymous class's own type**, so you can call its extra methods (see `lion.roar()` above).

### Limitations (all are compile errors)

|Code|Why|
|---|---|
|`var x;`|it needs an initial value to infer the type|
|`var y = null;`|`null` has no type|
|`var arr = {1, 2, 3};`|an array initializer needs a type (`new int[]{1, 2, 3}`)|
|`var f = () -> {};`|a lambda needs a target type|
|`ArrayList<var> bad;`|not allowed as a generic type argument|
|`var` as a **field** (outside methods)|it is for **local variables only**|
|`var` as a **parameter type** or **return type**|not allowed|

(`var list = new ArrayList<String>();` is fine, because the type is clear on the right side.)

---

## 7. Lambda expressions (Java 8+)

A shorter way to write an anonymous class **for an interface that has a single abstract method** (a **functional interface**).

- **Interfaces only**, not abstract classes (`Animal a = n -> {};` is an error: _Animal is not a functional interface_).
- The interface must have **exactly one abstract method** (default and static methods are allowed).

### Syntax

```
(parameters) -> { body }
```

|Case|Code|
|---|---|
|No parameters, a block body|`Showable s = () -> { System.out.println("Showable Lambda"); };`|
|A **single statement**: no braces|`Showable s = () -> System.out.println("Showable lambda 2");`|
|One parameter: write **only its name** (no type)|`Greeter g = name -> System.out.println("Hello, " + name);`|
|As a method argument|`print(() -> System.out.println("Showable lambda as an argument"));`|

### Anonymous class vs lambda

||Anonymous class|Lambda|
|---|---|---|
|Works for|an interface **or** a class (concrete or abstract)|only a **functional interface**|
|Number of methods|any|one|
|Own fields / state|yes|no|
|Calls a parent constructor|yes|no|
|`this` means|the **anonymous object itself**|the **enclosing object**|
|Own `.class` file|yes (`Main$1.class`)|no|



---

## 8. When to use it

- You need **one object** of a class with a small change (or an implementation of an interface), **used once** and in one place.
- A **short** body. If it grows long or is needed in several places, write a **named class**.
- If the interface has a **single method**, prefer a **lambda**.

---

## 9. Is it in C++?

**No, C++ has no anonymous inner classes** in the Java sense. The closest things are:

```cpp
// 1) an UNNAMED class that derives from a base, declared inside the function
struct : Reward {
    void rewardMethod() override { cout << "Your reward is 20$\n"; }
} emp4;
emp4.rewardMethod();

// 2) a lambda (the compiler creates an unnamed class for it), the closest to the Java lambda
auto showable1 = []() { cout << "Showable lambda\n"; };
int bonus = 5;
auto emp5 = [bonus]() { cout << "Your reward is " << 10 + bonus << "$\n"; };   // [bonus]: capture by copy
```

(Both compile with `g++ -std=c++17`.) The lambda also **captures** variables like Java does, but in C++ you choose how: by copy `[x]` or by reference `[&x]`.

---

## Summary

- An **anonymous inner class** = a class **without a name**, declared and created in **one expression** (ends with `;`).
- **3 ways:** extends a class (concrete or abstract), implements an interface, as a method argument.
- Its name is `Outer$1`; its **parent** is the class or interface it was created from, **not** the enclosing class.
- **One parent only.** No own constructor, but it can call the parent's constructor and have an **initializer block** (it runs **after** the parent's constructor).
- Extra methods can't be called through the parent type, except with **`var`** or directly on the creation expression.
- Local variables used inside must be **effectively final**.
- Static members: **only constants before Java 16**, allowed from Java 16.
- **Lambda** = a shorter form for a **functional interface** (one abstract method).
- C++ has no anonymous inner classes; use an unnamed derived class or a **lambda**.