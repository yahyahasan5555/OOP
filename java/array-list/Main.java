import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // ===================== 1. Declaration =====================

        // Raw type: it compiles, but the compiler can't check what goes inside. Avoid it.
        ArrayList rawList = new ArrayList();

        // ERROR: a primitive type can't be a type argument.
        // ArrayList is a list of OBJECTS, so use the wrapper class instead.
        // ArrayList<int> bad = new ArrayList<>();   // unexpected type; required: reference, found: int

        // Wrapper classes: int --> Integer, double --> Double, char --> Character, boolean --> Boolean
        // ArrayList<Integer> list3 = new ArrayList();           // works, but gives an "unchecked" warning
        // ArrayList<Integer> list3 = new ArrayList<>();         // diamond <>: the compiler infers Integer
        ArrayList<Integer> list3 = new ArrayList<Integer>();     // explicit type

        // Declare it from its parent interface.
        List<Integer> list4 = new ArrayList<>();

        // With a specific initial capacity (the size of the internal array, NOT the number of elements):
        ArrayList<Integer> list5 = new ArrayList<>(1234);


        // ===================== 2. Adding and reading =====================

        // add(value): adds at the end. int is boxed to Integer automatically.
        list5.add(10);
        list5.add(20);
        list5.add(30);
        list5.add(40);

        System.out.println(list5);                      // [10, 20, 30, 40]

        int sz = list5.size();                          // 4
        for(int i = 0; i < sz; ++i)
            // Random access O(1), but with the get method only (no [ ] like arrays).
            System.out.print(list5.get(i) + " ");       // 10 20 30 40
        System.out.println();

        for(int x : list5)                              // for-each (the value is unboxed to int)
            System.out.print(x + " ");                  // 10 20 30 40
        System.out.println();

        // Insert at the front (2 ways), O(n) because every element shifts right:
        list5.add(0, 50);
        list5.addFirst(60);
        System.out.println(list5);                      // [60, 50, 10, 20, 30, 40]

        // addAll(list): adds all elements of another collection at the end.
        ArrayList<Integer> list6 = new ArrayList<>();
        list6.addAll(list5);
        System.out.println(list6);                      // [60, 50, 10, 20, 30, 40]

        // To add several numbers at once, convert them to a list first:
        list6.addAll(Arrays.asList(70, 80, 90));
        System.out.println(list6);                      // [60, 50, 10, 20, 30, 40, 70, 80, 90]

        // addAll from a specific index:
        list6.addAll(2, Arrays.asList(100, 110, 120, 130));
        System.out.println(list6);                      // [60, 50, 100, 110, 120, 130, 10, 20, 30, 40, 70, 80, 90]

        // set(index, value): overwrites the element, O(1).
        list6.set(1, 55);
        System.out.println(list6);                      // [60, 55, 100, 110, 120, 130, 10, 20, 30, 40, 70, 80, 90]


        // ===================== 3. Copying =====================

        // Copy constructor: a NEW list with the same elements.
        ArrayList<Integer> copy = new ArrayList<>(list6);
        copy.add(999);
        System.out.println(list6.size() + " " + copy.size());   // 13 14  (the original is not affected)
                                                                // (Unlike copy reference)

        // clone() returns Object, so it needs a cast. It is a shallow copy.
        ArrayList<Integer> cloned = (ArrayList<Integer>) list6.clone();
        System.out.println(cloned.equals(list6));               // true

        // Shallow copy = only the REFERENCES are copied, so both lists share the same objects.

        // ===================== 4. Removing =====================

        // remove(index): O(n) because the elements after it shift left.
        list6.remove(0);                                // removes the element at index 0 (60)

        // remove(Object): removes the first element equal to the value.
        // A plain int is treated as an INDEX, so for a value use a cast or valueOf:
        list6.remove((Integer) 55);
        list6.remove(Integer.valueOf(100));
        System.out.println(list6);                      // [110, 120, 130, 10, 20, 30, 40, 70, 80, 90]

        // removeAll(collection): removes every element that is in the other collection.
        list6.removeAll(Arrays.asList(110, 120, 130));
        System.out.println(list6);                      // [10, 20, 30, 40, 70, 80, 90]

        // removeIf(condition): removes every element that matches.
        list6.removeIf(x -> x % 20 == 0);
        System.out.println(list6);                      // [10, 30, 70, 90]

        // contains / indexOf: both are O(n) (a linear search).
        System.out.println(list6.contains(30));         // true
        System.out.println(list6.indexOf(70));          // 2
        System.out.println(list6.indexOf(5));           // -1  (not found)

        // retainAll(collection): keeps only the elements that are also in the other collection.
        list6.retainAll(Arrays.asList(30, 70, 500));
        System.out.println(list6);                      // [30, 70]

        // clear(): removes everything.
        list6.clear();
        System.out.println(list6.size() + " " + list6.isEmpty());   // 0 true


        // ===================== 5. Other useful operations =====================

        ArrayList<Integer> nums = new ArrayList<>(Arrays.asList(5, 3, 8, 1, 9, 2));

        // subList(from, to): from is included, to is excluded.
        System.out.println(nums.subList(1, 4));                     // [3, 8, 1]
        ArrayList<Integer> part = new ArrayList<>(nums.subList(1, 4));

        // Sorting ascending / descending:
        Collections.sort(nums);
        System.out.println(nums);                                   // [1, 2, 3, 5, 8, 9]
        nums.sort(Comparator.reverseOrder());
        System.out.println(nums);                                   // [9, 8, 5, 3, 2, 1]

        // Reverse:
        Collections.reverse(nums);
        System.out.println(nums);                                   // [1, 2, 3, 5, 8, 9]

        // Min / max:
        System.out.println(Collections.min(nums) + " " + Collections.max(nums));   // 1 9

        // forEach(action):
        nums.forEach(x -> System.out.print(x + " "));               // 1 2 3 5 8 9
        System.out.println();


        // ===================== 6. Capacity =====================

        // There is no method to READ the capacity. You can only control it:
        //   new ArrayList<>(n)   initial capacity = n
        //   ensureCapacity(n)    grow the internal array once, in advance (avoids many resizes)
        //   trimToSize()         shrink the internal array to the current size (saves memory)
        ArrayList<Integer> big = new ArrayList<>();
        big.ensureCapacity(1000); // Init Cap = 1000
        big.add(1);
        big.trimToSize(); // Cap = size = 1


        // ===================== 7. A polymorphic list =====================

        // An array of Object can hold different types:
        Object[] objects = new Object[5];
        objects[0] = "AAA";
        objects[1] = (Integer) 5;
        objects[2] = 'C';
        objects[3] = true;
        objects[4] = 5.5;
        for(Object obj : objects)
            System.out.print(obj + " ");                // AAA 5 C true 5.5
        System.out.println();

        // The same works with ArrayList<Object>:
        ArrayList<Object> mixed = new ArrayList<>();
        mixed.add("AAA");
        mixed.add(5);
        mixed.add('C');
        mixed.add(true);
        mixed.add(5.5);
        System.out.println(mixed);                      // [AAA, 5, C, true, 5.5]
    }
}