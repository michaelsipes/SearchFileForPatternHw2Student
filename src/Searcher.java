
public class Searcher extends Thread{

    private SharedQueue queue;
    private String pattern;

    public Searcher(String s, SharedQueue queue){
        this.queue = queue;
        this.pattern = s;
    }

    public void run(){
        while(true){
            queue.dequeue(pattern);
            if (queue.isEmpty() && queue.getEndOfFile()) {
                break;
            }
        }
    }


}
