import java.util.ArrayList;

public class Main {
    // a method that takes an interface as a parameter (polymorphism)
    public static void print(Showable sh) {
        sh.print();
    }

    public static void main(String[] args) {
        // ============ 1) The problem ============
        Reward emp1 = new Reward();
        Reward emp2 = new Reward();
        Reward emp3 = new Reward();

        emp1.rewardMethod();
        emp2.rewardMethod();
        emp3.rewardMethod();

        // What if I want to give the 4th employee a reward of 20$ instead?
        // Option 1: create another class that extends Reward and override rewardMethod() to print 20$
        // Option 2: use today's topic: ANONYMOUS INNER CLASSES

        // ============ 2) Anonymous class: extends a class ============
        Reward emp4 = new Reward() {
            @Override
            public void rewardMethod() {
                System.out.println("Your reward is 20$");
            }
        }; // <-- note the semicolon: this is ONE statement (a variable declaration)
        emp4.rewardMethod();

        System.out.println(emp1.getClass()); // class Reward
        System.out.println(emp2.getClass()); // class Reward
        // The class name is "Main$1": it is written inside Main (the ENCLOSING class), the $ marks an
        // inner class, and 1 is the number of the first anonymous class in Main.
        // Main is NOT its parent. Its parent (superclass) is Reward:
        System.out.println(emp4.getClass());                   // class Main$1
        System.out.println(emp4.getClass().getSuperclass());   // class Reward
        System.out.println(emp4.getClass().isAnonymousClass()); // true

        // If I need the overridden method only ONCE, I don't even need a variable:
        new Reward() {
            @Override
            public void rewardMethod() {
                System.out.println("Your reward is 50$");
            }
        }.rewardMethod();

        // It can capture the local variables of the enclosing method, if they are final or EFFECTIVELY final
        // (never changed after the first assignment).
        int bonus = 5;
        Reward emp5 = new Reward() {
            @Override
            public void rewardMethod() {
                System.out.println("Your reward is " + (10 + bonus) + "$");
            }
        };
        emp5.rewardMethod();
        // bonus++;  // ERROR: local variables referenced from an inner class must be final or effectively final

        // ============ 3) Anonymous class: implements an interface ============
        // Showable showable = new Showable();  // ERROR: Showable is abstract; cannot be instantiated
        Showable showable = new Showable() {
            @Override
            public void print() {
                System.out.println("Showable interface");
            }
        };
        showable.print();

        // Same thing if I use the interface's method only once:
        new Showable() {
            @Override
            public void print() {
                System.out.println("Showable");
            }
        }.print();

        // ============ 4) Anonymous class as a method argument ============
        // Instead of writing a whole class that implements Showable just to pass it to print():
        print(new Showable() {
            @Override
            public void print() {
                System.out.println("Showable as an argument");
            }
        });

        // ============ 5) Lambda expression ============
        // - Only for interfaces that have a SINGLE abstract method (a "functional interface").
        // - Not for abstract classes.
        // Syntax:  (parameters) -> { body }
        Showable showable1;
        showable1 = () -> {
            System.out.println("Showable Lambda");
        };
        showable1.print();

        // If the body is a single statement, we can drop the braces (and the semicolon inside):
        Showable showable2 = () -> System.out.println("Showable lambda 2");
        showable2.print();

        // If the method takes a parameter, write only its NAME (the type is inferred):
        Greeter greeter = name -> System.out.println("Hello, " + name);   // (name) -> ... is the same
        greeter.greet("Yahya");

        // A lambda can also be an argument:
        print(() -> System.out.println("Showable lambda as an argument"));

        // ============ 6) Constructors and initializer blocks ============
        // An anonymous class has NO name, so it can't have a constructor of its own.
        // But it can call a constructor of its parent, with arguments: new Parent(args) { ... }
        // (with an interface there is no constructor, so the parentheses stay empty)
        // And it can have an INITIALIZER block:
        System.out.println("-- creating the dog:");
        Animal dog = new Animal("Rex") {
            { // the anonymous class's initializer block
                System.out.println("  [anonymous] initializer block");
            }

            @Override
            public void sound() {
                System.out.println(name + " says Woof");
            }
        };
        dog.sound();
        // Order: the parent's initializer block, then the parent's constructor (any type of constructor),
        // and ONLY THEN the anonymous class's initializer block.

        // ============ 7) Extra fields and methods ============
        Animal cat = new Animal("Tom") {
            int lives = 9;                       // extra field: OK

            @Override
            public void sound() {
                System.out.println(name + " says Meow (lives: " + lives + ")");
            }

            void climb() {                       // extra method: OK
                System.out.println(name + " is climbing");
            }
        };
        cat.sound();
        // cat.climb();  // ERROR: cannot find symbol. The TYPE of the variable `cat` is Animal, and Animal has no climb()

        // It works if the call is on the creation expression itself (its type is the anonymous class):
        new Animal("Leo") {
            @Override
            public void sound() { }

            void roar() {
                System.out.println(name + " roars");
            }
        }.roar();

        // ...or if I use `var`: the variable gets the anonymous class's own type
        var lion = new Animal("Simba") {
            @Override
            public void sound() { }

            void roar() {
                System.out.println(name + " roars loudly");
            }
        };
        lion.roar();

        // ============ 8) The `var` keyword (Java 10+) ============
        // The compiler infers the type from the value on the right side.
        var a = 234;                       // int
        var b = 'c';                       // char
        var c = "Class type";              // String
        var d = new Reward();              // Reward
        var list = new ArrayList<String>(); // ArrayList<String>
        list.add("var works");
        System.out.println(a + " " + b + " " + c + " " + d.getClass() + " " + list);

        // Limitations (each line below is a compile error):
        // var x;                         // ERROR: needs an initial value (so it can infer the type)
        // var y = null;                  // ERROR: null has no type
        // var arr = {1, 2, 3};           // ERROR: array initializer needs a type (use new int[]{1, 2, 3})
        // var f = () -> {};              // ERROR: a lambda needs a target type
        // ArrayList<var> bad;            // ERROR: can't be used as a generic type argument
        // As a field (outside methods), a parameter type or a return type: ERROR.
        // `var` is only for LOCAL variables.

        // ============ 9) Static members ============
        // Before Java 16 an anonymous (inner) class could NOT have static methods, static blocks or static
        // variables, except CONSTANTS (static final with a compile-time value).
        // From Java 16 it can have them.
        Reward emp6 = new Reward() {
            static final int EXTRA = 5;       // a constant: OK in every Java version
            // static int counter = 0;        // ERROR before Java 16, OK from Java 16
            // static { }                     // ERROR before Java 16, OK from Java 16

            @Override
            public void rewardMethod() {
                System.out.println("Your reward is " + (10 + EXTRA) + "$");
            }
        };
        emp6.rewardMethod();
    }
}

