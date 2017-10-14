
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SharedQueue {

    private int count;
    private final int MAXSIZE = 100;
    private LinkedList<String> queue;
    private int occurances;
    private int var4;
    private boolean endOfFile = false;
    private boolean isEmpty = false;
    private int numberOfLines;
    private PriorityQueue<String> realQueue;

    public SharedQueue(){
        queue = new LinkedList<String>();
        occurances = 0;
        var4 = 0;
        numberOfLines = 0;
        realQueue = new PriorityQueue<String>();
    }

    public synchronized void enqueue(String line) {

        while(count == MAXSIZE){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //if(!line.isEmpty() && !line.equals(" ")){
            //queue.add(line);
            realQueue.add(line);
            count++;
            numberOfLines++;
            isEmpty = false;
        //}
        notify();
    }

    public synchronized void dequeue(String regex){
        while (count == 0 && !this.getEndOfFile()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //This check is in the case where the reader finishes and the searcher is sleeping and wakes up
        //to do work
        if(count > 0){

            //String line = queue.removeLast();
            String line = realQueue.remove();
            count--;
            if(count == 0){
                isEmpty = true;
            }
            Pattern pattern = Pattern.compile(regex);
            Matcher  matcher = pattern.matcher(line);
            while (matcher.find()) {
                occurances++;
            }
            notify();
        }
    }

    public int getOccurances(){
        return this.occurances;
    }

    public synchronized void setEndOfFile(){
        endOfFile = true;
        notifyAll();
    }

    public synchronized boolean getEndOfFile(){
        return this.endOfFile;
    }

    public synchronized boolean isEmpty(){
        return isEmpty;
    }

    public int getLines(){
        return this.numberOfLines;
    }

}
