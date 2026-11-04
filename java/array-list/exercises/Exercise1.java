import java.util.ArrayList;
import java.util.Scanner;

public class Exercise1 {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        Scanner in = new Scanner(System.in);

        System.out.println("Enter 10 distinct integer numbers:");

        // run till the list's size be = 10
        // a duplicate is rejected.
        while(list.size() < 10) {
            int val = in.nextInt();
            if(list.contains(val)) System.out.println(val + " Already exists");
            else list.add(val);
        }
        System.out.println(list);
    }
}

/* ** Input:

    ❯ javac Exercise1.java
    ❯ java Exercise1
    Enter 10 distinct integer numbers:
    1
    1
    1 Already exists
    2
    3
    4
    3
    3 Already exists
    5
    6
    5
    5 Already exists
    7
    11
    11
    11 Already exists
    1
    1 Already exists
    2
    2 Already exists
    14
    15
    [1, 2, 3, 4, 5, 6, 7, 11, 14, 15]

*/