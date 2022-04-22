
/** a queue class that uses a one-dimensional array */

public class MyArrayQueue
{
   // data members
   int front;          // one counterclockwise from first element
   int rear;           // position of rear element of queue
   Object [] queue;    // element array

   // constructors
   /** create a queue with the given initial capacity */
   public MyArrayQueue(int initialCapacity)
   {
      if (initialCapacity < 1)
         throw new IllegalArgumentException
                 ("initialCapacity must be >= 1");
      queue = new Object [initialCapacity + 1];
      // default front = rear = 0
   }

   /** create a queue with initial capacity 5 */

   public MyArrayQueue()
   {// use default capacity of 5
      this(5);
   }

   // methods
   /** @return true iff queue is empty */
   public boolean isEmpty()
   {return front == rear;}

   public boolean isFull(){
      return ( (rear + 1) % queue.length) == front;
   } //Returns true if full, else false

   /** @return front element of queue
    * @return null if queue is empty */
   public Object getFrontElement()
   {
      if (isEmpty())
         return null;
      else
         return queue[(front + 1) % queue.length];
   }

   /** @return rear element of queue
    * @return null if the queue is empty */
   public Object getRearElement()
   {
      if (isEmpty())
         return null;
      else
         return queue[rear];
   }

   /** insert theElement at the rear of the queue */
   public void enqueue(Object theElement)
   {
      if (isFull()) {
         Object [] queueNew = new Object[(queue.length * 2)+1]; //if queue is full, create a new queue with double the size + 1
         int indexNew = 1;
         while (!isEmpty()) {
            queueNew[indexNew] = dequeue();
            indexNew++; //while isEmpty == False, append (by de-queueing) each item from the old queue to the new queue and increment indexNew by 1
         }
         queue = queueNew;
         front = 0;
         rear = indexNew - 1; //re-set the new queue with larger storage space as the new queue and reset front pointer to 0 and rear to the indexNew -1 (since arrays are zero-based)
      }
      rear = (rear + 1) % queue.length;
      queue[rear] = theElement;
   } //else if the queue is not full, set the rear to the true modulus of rear & queue.length (rear + 1 because Java treats the modulus operator differently) & then set the rear of the queue to the inputted element



   /** remove an element from the front of the queue
    * @return removed element */
   public Object dequeue()
   {
      if (isEmpty()){
         return null;
      } //if the queue is empty, return null
      front = (front + 1) % queue.length;
      Object oldFront = queue[front];
      queue[front] = null;
      return oldFront;
   } //else set the item currently stored at the front as object "oldFront" (most recent element that was de-queued) and set the front as null
   //then return "oldFront"
   /** test program */
   public static void main(String [] args)
   {
      MyArrayQueue q = new MyArrayQueue(3);
      // add a few elements
      q.enqueue("element1");	//q.put(new Integer(1));
      q.enqueue("element2");	//q.put(new Integer(2));
      q.enqueue("element3");	//q.put(new Integer(3));
      q.enqueue("element4");	//q.put(new Integer(4));

      // remove and add to test wraparound array doubling
      q.dequeue();
      q.dequeue();
      q.enqueue("element5");	//q.put(new Integer(5));
      q.enqueue("element6");	//q.put(new Integer(6));
      q.enqueue("element7");	//q.put(new Integer(7));
      q.enqueue("element8");	//q.put(new Integer(8));
      q.enqueue("element9");	//q.put(new Integer(9));
      q.enqueue("element10");	//q.put(new Integer(10));
      q.enqueue("element11");	//q.put(new Integer(11));
      q.enqueue("element12");	//q.put(new Integer(12));

      // delete all elements
      while (!q.isEmpty())
      {
         System.out.println("Rear element   : " + q.getRearElement());
         System.out.println("Front element  : " + q.getFrontElement());
         System.out.println("Removed element: " + q.dequeue() + "\n");
      }
      if (q.isEmpty()) System.out.println("empty queue");
   }
}