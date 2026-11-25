// An ABSTRACT class with a constructor and an initializer block
// (used for: extending an abstract class, parent constructor, initializer block order)
public abstract class Animal {
    protected String name;

    { // the parent's initializer block
        System.out.println("  [Animal] initializer block");
    }

    public Animal(String name) {
        this.name = name;
        System.out.println("  [Animal] constructor: " + name);
    }

    public abstract void sound();
}
