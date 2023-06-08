//import edu.princeton.cs.algs4.StdRandom;
//
//import java.util.Iterator;
//import java.util.NoSuchElementException;
//
//public class RandomizedQueue<Item> implements Iterable<Item> {
//    private int size;
//    private Item[] arr;
//
//    public RandomizedQueue() {
//        this.size = 0;
//        this.arr = (Item[]) new Object[1];
//    }
//
//    private class RandomIterator implements Iterator<Item> {
//        private int copySize = size;
//        private Item[] tmp = (Item[]) new Object[arr.length];
//
//        public RandomIterator() {
//            for (int i = 0; i < arr.length; i ++) {
//                tmp[i] = arr[i];
//            }
//        }
//        @Override
//        public boolean hasNext() {
//            return copySize > 0;
//        }
//
//        @Override
//        public Item next() {
//            if (!hasNext()) {
//                throw new NoSuchElementException();
//
//            }
//            int n = StdRandom.uniformInt(copySize);
//            Item op = tmp[n];
//            tmp[n] = tmp[copySize - 1];
//            copySize--;
//            return op;
//        }
//
//        @Override
//        public void remove() {
//            throw new UnsupportedOperationException();
//        }
//    }
//    public boolean isEmpty() {
//        return size == 0;
//    }
//
//    public int size() {
//        return size;
//    }
//
//    public void enqueue(Item item) {
//        if (item == null) {
//            throw new IllegalArgumentException();
//        }
//        if (size == arr.length) {
//            resize(2 * size);
//        }
//        arr[size++] = item;
//    }
//
//    public Item dequeue() {
//        if (isEmpty()) {
//            throw new NoSuchElementException();
//        }
//        int tmp = StdRandom.uniformInt(size);
//        Item op = arr[tmp];
//        if (tmp != size - 1) {
//            arr[tmp] = arr[size - 1];
//        }
//        arr[size - 1] = null;
//        return op;
//    }
//
//    public Item sample() {
//        if (isEmpty())
//            throw new NoSuchElementException();
//        int tmp = StdRandom.uniformInt(size);
//        return arr[tmp];
//    }
//
//    private void resize(int cap) {
//        Item[] copy = (Item[]) new Object[cap];
//        for (int i = 0; i < size; i++) {
//            copy[i] = arr[i];
//        }
//        arr = copy;
//    }
//
//    public Iterator<Item> iterator() {
//        return new RandomIterator();
//    }
//
//    public static void main(String[] args) {
//        RandomizedQueue<Integer> rqq = new RandomizedQueue<>();
//        rqq.enqueue(4);
//        rqq.enqueue(3);
//        rqq.enqueue(2);
//        rqq.enqueue(1);
//
//        System.out.println(rqq.size());
//        System.out.println(rqq.sample());
//        System.out.println(rqq.dequeue());
//        System.out.println(rqq.isEmpty());
//    }
//}
