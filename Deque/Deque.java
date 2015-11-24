import java.util.Iterator;

public class Deque<Item> implements Iterable<Item>
{
    private DequeNode<Item> start;
    private DequeNode<Item> end;
    private int size;

    public Deque()
    {
        size = 0;
        start = new DequeNode<Item>();
        end = new DequeNode<Item>();
        start.next = end;        end.prev = start;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public void addFirst(Item item)
    {
        if (item == null)
        {
            throw new NullPointerException(); // attempts to add a null item
        }
        DequeNode<Item> first = start.next;
        DequeNode<Item> node = new DequeNode<Item>();
        node.item = item;
        node.next = first;
        node.prev = start;
        start.next = node;
        start.prev = node;
        size++;
    }

    public void addLast(Item item)
    {
        if (item == null)
        {
            throw new NullPointerException();
        }
        DequeNode<Item> last = end.prev;
        DequeNode<Item> node = new DequeNode<Item>();
        node.item = item;
        node.next = end;
        node.prev = last;
        end.prev = node;
        last.next = node;
        size++;
    }

    public Item removeFirst()
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException(); // to remove item from an empty deque
        }

        final DequeNode<Item> first = start.next;
        final DequeNode<Item> next = first.next;
        next.prev = start;
        start.next = next;
        size--;
        return first.item;
    }

    public Item removeLast()
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException();  // to remove item from an empty deque
        }

        final DequeNode<Item> last = end.prev;
        final DequeNode<Item> prev = last.prev;
        end.prev = prev;
        prev.next = end;
        size--;
        return last.item;
    }

    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {
        private DequeNode<Item> present = start.next;
        public boolean hasNext()
        {
            return (present != end);
        }
        public Item next()
        {
            if (!hasNext())
            {
                throw new java.util.NoSuchElementException();// to remove item from an empty deque
            }   //// samshayam undu….. throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return. This is what the specification says...
            Item item = present.item;
            present = present.next;
            return item;
        }
        public void remove()
        {
            throw new UnsupportedOperationException();//calls the remove() method in the iterator
        }
    }

    private static class DequeNode<Item>
    {
        private Item item;
        private DequeNode<Item> next;
        private DequeNode<Item> prev;
    }

    public static void main(String[] args)
    {
        Deque<String> dequeue = new Deque<String>();
        dequeue.addFirst("See how they run. ");
        dequeue.addFirst("See how they run. ");
        dequeue.addFirst("Three blind mice. ");
        dequeue.addFirst("Three blind mice. ");
        dequeue.addLast("They all ran after the farmer's wife. ");
        dequeue.addLast("Who cut off their tails with a carving knife. ");
        dequeue.addLast("Did you ever see such a sight in your life. ");
        dequeue.addLast("As three blind mice? ");
        Iterator<String> iterator = dequeue.iterator();
        while (iterator.hasNext())
        {
            String next = iterator.next().toString();
            System.out.println(next);
        }
    }
}
