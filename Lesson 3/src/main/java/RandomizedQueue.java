import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Item[] elements;
    private int numOfElements;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 1;
        elements = (Item[]) new Object[size];
        numOfElements = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }


    // return the number of items on the randomized queue
    public int size() {
        return numOfElements;
    }

    private boolean isFull() {
        return size == numOfElements;
    }

    // add the item
    public void enqueue(Item item) {
        if (isFull()) {
            duplicateSize();
        }
        if (item == null) {
            throw new IllegalArgumentException();
        }

        elements[numOfElements] = item;
        numOfElements++;
    }

    private void resize(int capacity) {
        if (isEmpty()) {
            throw new IllegalArgumentException("The array is empty.");
        }

        size = capacity;
        Item[] copy = (Item[]) new Object[size];

        int maxSize = size();
        for (int i = 0; i < maxSize; i++) copy[i] = elements[i];

        elements = copy;
    }

    private void duplicateSize() {
        resize(2 * size);
    }

    private void halfSize() {
        resize(size / 2);
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new IllegalArgumentException("The queue is empty.");
        }

        Item item = removeRandomItem();

        if (numOfElements == size / 4) {
            halfSize();
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        return elements[StdRandom.uniformInt(numOfElements)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private Item removeRandomItem() {
        int index = StdRandom.uniformInt(size());
        int lastIndex = size() - 1;

        Item item = elements[index];
        elements[index] = elements[lastIndex];
        elements[lastIndex] = null;

        numOfElements--;

        return item;
    }

    // unit testing (required)
    public static void main(String[] args) {
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private final RandomizedQueue<Item> copy;

        RandomizedQueueIterator() {
            copy = new RandomizedQueue<Item>();

            for (int i = 0; i < size(); i++) {
                copy.enqueue(elements[i]);
            }
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException("There are no more elements");

            return copy.dequeue();
        }

        public boolean hasNext() {
            return copy.size() > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException("This operation is not supported by this class");
        }
    }
}
