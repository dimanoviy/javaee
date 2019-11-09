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
        MyLinkedList<E> linkList = (MyLinkedList<E>) object;
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
        E element = (E) object;
        MyNode<E> node = getFirstNode();
        if (node != null) {
            do {
                if (node.getElement() == element) {
                    return unlinkNode(node);
                }
                node=node.getNext();
            } while (node.hasNext());
        }
        return false;
    }

    private boolean unlinkNode(MyNode node) {
        if (!node.hasPrev()) {
            node.getNext().setPrev(null);
        } else {
            if (!node.hasNext()) {
                node.getPrev().setNext(null);
            } else {
                node.getPrev().setNext(node.getNext());
            }
        }
        this.size--;
        return true;
    }

    public Object remove(int index) {
        if (!checkIndexCorrect(index)) {
            throw new IllegalArgumentException("Incorrect index");
        }
        unlinkNode(getNodeByIndex(index));
        return true;
    }

    public MyNode<E> getNodeByIndex(int index) {
        if (!checkIndexCorrect(index)) {
            throw new IllegalArgumentException("Incorrect index");
        }
        MyNode node = getFirstNode();
        for (int i = 1; i <= index; i++) {
            node = node.getNext();
        }
        return node;
    }

    private boolean checkIndexCorrect(int index) {
        if (index > getSize() | index < 0) {
            return false;
        }
        return true;
    }

    public boolean removeFirst() {
        return remove(getFirstNode());
    }

    public boolean removeLast() {
        return remove(getLastNode());
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

    public int getSize() {
        return this.size;
    }

    private void setLastNode(MyNode<E> node) {
        this.last = node;
    }

    private void setFirstNode(MyNode<E> node) {
        this.first = node;
    }

    @Override
    public Object get(int index) {
        if (!checkIndexCorrect(index)) {
            throw new IllegalArgumentException("Incorrect index");
        }
        return getNodeByIndex(index);
    }

    @Override
    public Object set(int index, Object element) {
        if (!checkIndexCorrect(index)) {
            throw new IllegalArgumentException("Incorrect index");
        }
        getNodeByIndex(index).setElement((E) element);
        return null;
    }

    @Override
    public void add(int index, Object element) {
        if (!checkIndexCorrect(index)) {
            throw new IllegalArgumentException("Incorrect index");
        }
        MyNode<E> nodeReplaced = getNodeByIndex(index);
        MyNode<E> node = new MyNode<>(nodeReplaced.getPrev(), (E) element, nodeReplaced);
        nodeReplaced.getPrev().setNext(node);
        nodeReplaced.setPrev(node);
    }

    @Override
    public boolean add(Object object) {
        return addLast((E) object);
    }

    public boolean addLast(E element) {
        MyNode<E> node = new MyNode<E>(getLastNode(), element, null);
        if (getLastNode() == null) {
            setFirstNode(node);
        } else {
            getLastNode().setNext(node);
        }
        setLastNode(node);
        this.size++;
        return true;
    }

    public boolean addFirst(E element) {
        MyNode<E> node = new MyNode<>(null, element, getFirstNode());
        MyNode<E> tmpFirst = getFirstNode();
        tmpFirst.setPrev(node);
        setFirstNode(node);
        node.setNext(tmpFirst);
        this.size++;
        return true;
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
        if (!checkIndexCorrect(index)) {
            throw new IllegalArgumentException("Incorrect index");
        }
        return null;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        if (!checkIndexCorrect(fromIndex) | !checkIndexCorrect(toIndex)) {
            throw new IllegalArgumentException("Incorrect index");
        }
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
        string += node.getElement() + " <-> ";
        while (node.hasNext()) {
            node = node.getNext();
            string += node.getElement() + " <-> ";
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

        private void setNext(MyNode<E> node) {
            this.next = node;
        }

        private void setPrev(MyNode<E> node) {
            this.prev = node;
        }

        private void setElement(E element) {
            this.element = element;
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
