import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node head;
    private Node tail;

    private class Node {
        Item item;
        Node next;
        Node prev;

        private Node(Item item) {
            this.item = item;
            this.next = null;
            this.prev = null;
        }
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item tmp = current.item;
            current = current.next;
            return tmp;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    public Deque() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot be null");
        }
        if (isEmpty()) {
            head = tail = new Node(item);
            head.prev = null;
            tail.next = null;
        } else {
            Node oldhead = head;
            head = new Node(item);
            head.prev = null;
            head.next = oldhead;
            oldhead.prev = head;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot be null");
        }
        if (isEmpty()) {
            head = tail = new Node(item);
            head.prev = null;
            tail.next = null;
        } else {
            Node oldtail = tail;
            tail = new Node(item);
            tail.next = null;
            tail.prev = oldtail;
            oldtail.next = tail;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item tmp = head.item;
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
        size--;
        return tmp;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item tmp = tail.item;
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }
        size--;
        return tmp;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }


//    public static void main(String[] args) {
//        Deque<Integer> deq1 = new Deque<Integer>();
//
//        System.out.println("size: " + deq1.size());
//
//        deq1.addFirst(1);
//        deq1.addFirst(2);
//        deq1.addFirst(3);
//        deq1.addFirst(4);
//        deq1.addFirst(5);
//
//        System.out.println("size: " + deq1.size());
//
//        System.out.println(deq1.removeFirst());
//        deq1.removeFirst();
//
//        System.out.println("size: " + deq1.size());
//
//        deq1.addLast(1);
//        deq1.addLast(2);
//        deq1.addLast(3);
//        deq1.addLast(4);
//        deq1.addLast(5);
//
//        System.out.println(deq1.removeLast());
//        deq1.removeLast();
//
//        System.out.println("size: " + deq1.size());
//
//        Iterator<Integer> itr = deq1.iterator();
//
//        System.out.println(itr.next());
//        System.out.println(itr.next());
//
//        System.out.println(itr.hasNext());
//    }





}
