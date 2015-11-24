import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import java.lang.NullPointerException;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item>
{

    private Item [] items;
    private int itemCount;

    /*
     * construct an empty randomized queue 
     */
    public RandomizedQueue(){
        items=(Item[])new Object[1];
        itemCount=0;        
    } 

    /*
     * is the queue empty?    
     */
    public boolean isEmpty(){
        return itemCount==0;
    }

    /*
     * return the number of items on the queue
     */
    public int size(){
        return itemCount;
    } 

    /*
     * add the item
     */
    public void enqueue(Item item){
        if(item==null){
            throw new NullPointerException("Item to add is NULL");
        }
        if(items.length==itemCount){
            resize(items.length*2);
            items[itemCount]=item;
            itemCount++;
        } else {
            items[itemCount]=item;
            itemCount++;
        }
    }



    /*
     * delete and return a random item
     */
    public Item dequeue(){
        if(isEmpty()){
            throw new NoSuchElementException("Queue is empty");
        }
        int indexToReturn=StdRandom.uniform(itemCount);        
        Item itemToReturn=items[indexToReturn];
        if(itemCount-1==indexToReturn){
            items[indexToReturn]=null;                    
        } else {
            items[indexToReturn]=items[itemCount-1];
            items[itemCount-1]=null;
        }        
        if (itemCount==items.length/4){
            resize(items.length/2);
        }
        itemCount--;
        return itemToReturn;
    } 
    /*
     * return (but do not delete) a random item
     */
    public Item sample(){
        if(isEmpty()){
            throw new NoSuchElementException("Queue is empty");
        }
        return items[StdRandom.uniform(itemCount)];
    } 
    /*
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator(){
        return new RandomizedQueueIterator();
    }

    private void resize (int capacity){        
        Item[] copy=(Item[])new Object[capacity];
        for(int i=0;i<itemCount;i++){
            copy[i]=items[i];
        }
        items=copy;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {  
        int n=0;
        int [] indices;  

        public RandomizedQueueIterator(){
            indices=new int[itemCount];
            for(int i=0;i<indices.length;i++){
                indices[i]=i;
            }
            StdRandom.shuffle(indices);
        }

        public boolean hasNext()  { 
            return n<itemCount; 

        }
        public void remove() {
            throw new UnsupportedOperationException(); 
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }             
            return items[indices[n++]];

        }
    }
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomQueue = new RandomizedQueue<Integer>();

        randomQueue.enqueue(Integer.valueOf(1));
        randomQueue.enqueue(Integer.valueOf(2));
        randomQueue.enqueue(Integer.valueOf(3));
        randomQueue.enqueue(Integer.valueOf(4));
        randomQueue.enqueue(Integer.valueOf(5));
        randomQueue.enqueue(Integer.valueOf(6));
        randomQueue.enqueue(Integer.valueOf(7));
        randomQueue.enqueue(Integer.valueOf(8));
        randomQueue.enqueue(Integer.valueOf(9));
        randomQueue.enqueue(Integer.valueOf(10));
        randomQueue.enqueue(Integer.valueOf(11));


        for (Integer x: randomQueue) {
            StdOut.println(x);
        }

    }
}


