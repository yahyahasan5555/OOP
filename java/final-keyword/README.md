# Final Keyword

Sometimes we need to **limit extensibility**: stop a value from changing, stop a method from being overridden, or stop a class from being inherited.

`final` is a non-access modifier. It works on: **variables, parameters, methods, classes**.

|Used on|Effect|
|---|---|
|Variable|No value change|
|Method|No overriding|
|Class|No inheritance|

---

## 1. Final variables

Also called **constants**. If you assign a value twice, you get a compiler error.

```java
final int STUDENT_ID = 123456;
STUDENT_ID = 789;   // ERROR: cannot assign a value to final variable
```

Naming convention: `UPPER_CASE_WITH_UNDERSCORES`.

### Two ways to give it a value

1. At the declaration (like above).
2. Declare it first, assign it later (in Java only). This is called a **blank final variable**.

A blank final variable can be assigned **once**, in a **block or a constructor** only.

Example: every student has a constant ID, but each student has a different one. So declare it without a value, and assign it in the constructor:

```java
private final int STUDENT_ID;

public Student(final int STUDENT_ID) {
    this.STUDENT_ID = STUDENT_ID;
}
```

### Static final

One constant shared by the whole class. If it is blank, assign it **only in a static block**:

```java
private static final float DEGREE_OF_SUCCESS;

static {
    DEGREE_OF_SUCCESS = 253;
}
```

### Local final

A final variable inside a constructor or a method:

```java
void print() {
    final int count = 5;
    // count = 6;   // ERROR
}
```

---

## 2. Final parameters

The method can't change the parameter.

```java
public Student(final int STUDENT_ID) {
    // STUDENT_ID = 1111;   // ERROR: final parameter may not be assigned
}
```

---

## 3. Final methods

A child class can't override a final method (same signature).

```java
final public String toString() { ... }
```

---

## 4. Final classes

A final class can't be inherited. Example: the `String` class.

If you create an object of a final class, you can still change its variables normally.

---

## 5. Final reference (object)

You can't assign it again with `new`, or to another object. But you can change its inner data normally (in Java).

```java
final Student s = new Student(1, "Ali", 2005);
s.name = "Ahmed";                      // OK
s = new Student(2, "Sara", 2004);      // ERROR
```

---

## Summary

- Variables --> no value change
- Methods --> no overriding
- Classes --> stop inheritance
---