import java.util.ArrayList;
import java.util.Scanner;

/*
 * Exercise Two:
 * Create a menu program with the following options:
 * 1. Add element
 * 2. Remove element
 * 3. Print all elements
 * 4. Exit.
 *
 * The program will remain running as long as
 * the user doesn't choose the fourth choice.
 */
public class Exercise2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Integer> list = new ArrayList<>();

        int option;
        do {
            System.out.println();
            System.out.println(
                    "1. Add element.\n" +
                    "2. Remove element.\n" +
                    "3. Print elements.\n" +
                    "4. Exit\n"
            );

            option = in.nextInt();
            if(option == 1) {
                System.out.print("Enter an integer: ");
                list.add(in.nextInt());
                System.out.println("Element added.\n");

            } else if(option == 2) {
                System.out.print("Enter a number to be deleted: ");
                int deleted = in.nextInt();

                if(list.contains(deleted)) {
                    list.remove((Integer) deleted);
                    System.out.println("Element removed\n");
                } else System.out.println("Element not found\n");

            } else if(option == 3) {
                System.out.println(list);
                System.out.println();

            } else if(option != 4) {
                System.out.println("Invalid option, choose from 1 to 4.");
            }
        } while(option != 4);
    }
}

/* **Input:

    ❯ open Exercise2.java
    ❯ javac Exercise2.java
    ❯ java Exercise2

    1. Add element.
    2. Remove element.
    3. Print elements.
    4. Exit

    1
    Enter an integer: 12
    Element added.


    1. Add element.
    2. Remove element.
    3. Print elements.
    4. Exit

    1
    Enter an integer: 12
    Element added.


    1. Add element.
    2. Remove element.
    3. Print elements.
    4. Exit

    3
    [12, 12]


    1. Add element.
    2. Remove element.
    3. Print elements.
    4. Exit

    2
    Enter a number to be deleted: 11
    Element not found


    1. Add element.
    2. Remove element.
    3. Print elements.
    4. Exit

    2
    Enter a number to be deleted: 13
    Element not found


    1. Add element.
    2. Remove element.
    3. Print elements.
    4. Exit

    2
    Enter a number to be deleted: 12
    Element removed


    1. Add element.
    2. Remove element.
    3. Print elements.
    4. Exit

    2
    Enter a number to be deleted: 12
    Element removed


    1. Add element.
    2. Remove element.
    3. Print elements.
    4. Exit

    2
    Enter a number to be deleted: 12
    Element not found


    1. Add element.
    2. Remove element.
    3. Print elements.
    4. Exit

    3
    []


    1. Add element.
    2. Remove element.
    3. Print elements.
    4. Exit

    4

*/