package mylinkedlist;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<String> myLinkedList = new MyLinkedList<>();
        myLinkedList.add("1");
        myLinkedList.add("2");
        myLinkedList.add("3");
        System.out.println(myLinkedList.toString());
        myLinkedList.remove("2");
        System.out.println(myLinkedList.toString());

    }
}
