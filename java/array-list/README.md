# ArrayList

`ArrayList` is a **dynamic, resizable (growable) array**. It lives in `java.util`, and it is the Java version of the C++ `vector`.

```java
import java.util.ArrayList;
```

---

## Declaration

```java
ArrayList list = new ArrayList();                        // raw type: avoid it
ArrayList<Integer> list = new ArrayList();               // works, but gives an "unchecked" warning
ArrayList<Integer> list = new ArrayList<>();             // diamond <>: the compiler infers the type
ArrayList<Integer> list = new ArrayList<Integer>();      // explicit type
```

It can also be declared from its parent interface (this is the better practice):

```java
List<Integer> list = new ArrayList<>();
```

### Primitive types

An ArrayList is a list of **objects**, so it can't hold primitive types:

```java
ArrayList<int> list = new ArrayList<>();   // ERROR: unexpected type (required: reference, found: int)
```

Use the **wrapper class** instead:

|Primitive|Wrapper|
|---|---|
|`int`|`Integer`|
|`double`|`Double`|
|`char`|`Character`|
|`boolean`|`Boolean`|

```java
ArrayList<Integer> list = new ArrayList<>();
list.add(10);          // the int is boxed to Integer automatically
int x = list.get(0);   // and unboxed back to int
```

---

## Capacity and growth

Inside, an ArrayList keeps a normal array. The **capacity** is that array's size, and the **size** is how many elements are really in it.

- `new ArrayList<>()` starts **empty**. The capacity becomes **10** when the first element is added.
    
- When the internal array is **full**, the next `add` stops and creates a **new, bigger array**, copies all the elements, and then continues.
    
- New capacity = `n + (n >> 1)`, which is about **1.5 × n**:
    
    `10 → 15 → 22 → 33 → 49 → ...`
    

> Careful with the parentheses: in Java `n + n >> 1` means `(n + n) >> 1`, which is just `n`. Write `n + (n >> 1)`.

- `new ArrayList<>(1234)` sets the **initial capacity** to 1234.
- There is **no method to read the capacity**. You can only control it:
    - `ensureCapacity(n)`: grow the internal array once, in advance (avoids many resizes).
    - `trimToSize()`: shrink the internal array to the current size (saves memory).

---

## Copying

```java
ArrayList<Integer> copy = new ArrayList<>(original);                // copy constructor
ArrayList<Integer> cloned = (ArrayList<Integer>) original.clone();  // clone + cast
```

Both make a **shallow copy**: only the references are copied, so the two lists share the same objects inside. For `Integer` that doesn't matter, but with objects like `StringBuilder`, changing an object through one list changes it in the other.

---

## Methods

|Method|What it does|Time|
|---|---|---|
|`add(value)`|add at the end|O(1) on average|
|`add(index, value)`|insert at an index (the others keep their order and shift right)|O(n)|
|`addFirst(value)`|insert at the front (Java 21+)|O(n)|
|`addAll(list)` / `addAll(index, list)`|add many elements|O(m)|
|`get(index)`|read an element|O(1)|
|`set(index, value)`|overwrite an element|O(1)|
|`size()` / `isEmpty()`|number of elements|O(1)|
|`remove(index)`|remove by **index**|O(n)|
|`remove(Object)`|remove the first element with this **value**|O(n)|
|`removeAll(list)`|remove all elements found in the other list|O(n·m)|
|`removeIf(condition)`|remove all elements that match|O(n)|
|`retainAll(list)`|keep only the elements found in the other list|O(n·m)|
|`contains(value)` / `indexOf(value)`|search (`indexOf` gives -1 if not found)|O(n)|
|`subList(from, to)`|a **view** of a part (`to` is excluded)|O(1)|
|`clear()`|remove everything|O(n)|
|`forEach(action)`|do something for each element|O(n)|

**Careful with `remove`:** a plain `int` is treated as an **index**. To remove a **value**, use a cast or `valueOf`:

```java
list.remove(5);                    // removes the element at index 5
list.remove((Integer) 5);          // removes the value 5
list.remove(Integer.valueOf(5));   // removes the value 5
```

**Sorting, reverse, min and max** (from the `Collections` class):

```java
Collections.sort(list);                      // ascending
list.sort(Comparator.reverseOrder());        // descending
Collections.reverse(list);
Collections.min(list);   Collections.max(list);
```

**A list of different types:** `ArrayList<Object>` works like an `Object[]` array, so it can hold a String, an Integer, a char, and so on. The cost is that you lose type safety, and you need a cast to use the values.

---

## Class hierarchy

```
Iterable → Collection → SequencedCollection (Java 21) → List        (each one extends the previous)

Object → AbstractCollection → AbstractList → ArrayList               (classes)
```

`ArrayList` implements `List`, `RandomAccess`, `Cloneable` and `Serializable`.

---

## Is it the best data structure?

**Good:**

- Random access in O(1), like an array.
- Adding at the end is O(1) on average.
- The elements sit together in memory, so it's fast to go through.

**Not so good:**

- Inserting or deleting at the front or in the middle is **O(n)**, because the elements shift.
- Searching a value is O(n).
- It holds wrapper **objects** instead of plain `int`s, so it uses more memory.
- Some capacity is wasted (the internal array can be up to 50% empty), and each resize copies everything.

---

## The code

| File                                                                                                          | What it has                                            |
| ------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------ |
| [`Main.java`](https://github.com/yahyahasan5555/OOP/blob/main/java/array-list/Main.java))                     | all the methods above, with the output in the comments |
| [`Exercise1.java`](https://github.com/yahyahasan5555/OOP/blob/main/java/array-list/exercises/Exercise1.java)) | read 10 **distinct** integers from the user            |
| [`Exercise2.java`](https://github.com/yahyahasan5555/OOP/blob/main/java/array-list/exercises/Exercise2.java)) | menu: add, remove, print, exit                         |
