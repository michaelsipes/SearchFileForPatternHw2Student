
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SharedQueue {

    private int count;
    private final int MAXSIZE = 100;
    private LinkedList<String> queue;
    private int occurrences;
    private boolean endOfFile = false;
    private boolean isEmpty = false;
    private int numberOfLines;
    private PriorityQueue<String> realQueue;
    public int numberOfLinesSearched;

    public SharedQueue(){
        queue = new LinkedList<String>();
        occurrences = 0;
        numberOfLines = 0;
        //realQueue = new PriorityQueue<String>();
        count = 0;
        numberOfLinesSearched = 0;
    }

    public synchronized void enqueue(String line) {

        while(count == MAXSIZE){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.addLast(line);
        count++;
        numberOfLines++;
        isEmpty = false;
        notifyAll();
    }

    public synchronized void dequeue(String regex){
        String line = "";
        synchronized (this){
            while (count == 0 && !this.getEndOfFile()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(count > 0){
                line = queue.removeFirst();
                count--;
            }
            notifyAll();
        }

        //This check is in the case where the reader finishes and the searcher is sleeping and wakes up
        //to do work
        if(count == 0){
            isEmpty = true;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher  matcher = pattern.matcher(line);
        while (matcher.find()) {
            occurrences++;
        }
        numberOfLinesSearched++;
    }

    public int getOccurrences(){
        return this.occurrences;
    }

    public synchronized void setEndOfFile(){
        endOfFile = true;
        notifyAll();
    }

    public boolean getEndOfFile(){
        return this.endOfFile;
    }

    public boolean isEmpty(){
        return isEmpty;
    }

    public int getLines(){
        return this.numberOfLines;
    }

}
