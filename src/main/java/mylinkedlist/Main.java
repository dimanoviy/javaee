package mylinkedlist;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<String> myLinkedList = new MyLinkedList<>();
        for (int i = 0; i < 9; i++) {
            myLinkedList.add(i);
        }
        System.out.println(myLinkedList.toString());
//        myLinkedList.remove("2");
//        System.out.println(myLinkedList.toString());
//        myLinkedList.addFirst("5");
//        System.out.println(myLinkedList.toString());
//        myLinkedList.addLast("7");
//        System.out.println(myLinkedList.toString());
        myLinkedList.set(2, "99");
        System.out.println(myLinkedList.toString());

    }
}
