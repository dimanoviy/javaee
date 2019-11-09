package mylinkedlist;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyLinkedList<E> implements List {
    int size;
    MyNode<E> first;
    MyNode<E> last;


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public boolean contains(Object object) {
        MyLinkedList linkList = (MyLinkedList) object;
        return linkList.isEmpty();
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public boolean remove(Object object) {
//        MyNode<E> nodeToRemove = new MyNode<>(null, (E) object, null);
        E element = (E) object;
        MyNode<E> node = getFirstNode();
        if (node != null) {
            do {
                if (node.getElement() == element) {
                    unlinkNode(node);
                    return true;
                }
                node=node.getNext();
            } while (node.hasNext());
        }
        return false;
    }

    private void unlinkNode(MyNode node) {
        if (!node.hasPrev()) {
            node.getNext().setPrev(null);
        } else {
            if (!node.hasNext()) {
                node.getPrev().setNext(null);
            } else {
                node.getPrev().setNext(node.getNext());
            }
        }
    }

    public Object remove(int index) {
        return false;
    }

    public boolean removeFirst() {
        return false;
    }

    public boolean removeLast() {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return false;
    }

    @Override
    public void clear() {

    }

    public MyNode<E> getLastNode() {
        return last;
    }

    public MyNode<E> getFirstNode() {
        return first;
    }

    private void setLastNode(MyNode<E> node) {
        this.last = node;
    }


    private void setFirstNode(MyNode<E> node) {
        this.first = node;
    }

    @Override
    public Object get(int index) {
        return null;
    }

    @Override
    public Object set(int index, Object element) {
        return null;
    }

    @Override
    public void add(int index, Object element) {

    }

    @Override
    public boolean add(Object object) {
        return addLast((E) object);
    }

    public boolean addLast(E element) {
        MyNode node = new MyNode(getLastNode(), element, null);
        if (getLastNode() == null) {
            setFirstNode(node);
        } else {
            getLastNode().setNext(node);
        }
        setLastNode(node);
        size++;
        return true;
    }

    public void addFirst() {
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator listIterator() {
        return null;
    }

    @Override
    public ListIterator listIterator(int index) {
        return null;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    public String toString() {
        String string = "| -> ";
        MyNode node = this.getFirstNode();
        if (node == null) {
            return "No elements in the list";
        }
        string += node.getElement() + " -> ";
        while (node.hasNext()) {
            node = node.getNext();
            string += node.getElement() + " -> ";
        }
        string += " |";
        return string;
    }

    private class MyNode<E> {
        E element;
        MyNode<E> prev;
        MyNode<E> next;

        MyNode(MyNode<E> prev, E element, MyNode<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }

        private void setNext(MyNode node) {
            this.next = node;
        }

        private void setPrev(MyNode node) {
            this.prev = node;
        }

        private MyNode<E> getNext() {
            return this.next;
        }

        private MyNode<E> getPrev() {
            return this.prev;
        }

        private E getElement() {
            return this.element;
        }

        private boolean hasNext() {
            return this.getNext() != null;
        }

        private boolean hasPrev() {
            return this.getPrev() != null;
        }
    }
}
