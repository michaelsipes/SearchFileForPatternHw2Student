import java.io.*;
import java.util.Scanner;


public class Reader implements Runnable{

    private File theFile;
    private FileReader fin;
    private SharedQueue queue;
    private BufferedReader read;
    int numberOfLines;

    public Reader(String file, SharedQueue queue){
        this.theFile = new File(file);
        this.queue = queue;
        FileReader var3 = null;
        numberOfLines = 0;
        try {
            fin = new FileReader(theFile);
            read = new BufferedReader(fin);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        String var5;
        int var6 = 0;
        int var7;
        try {
            for(var7 = 0; (var5 = read.readLine()) != null; ++var6) {
                queue.enqueue(var5);
                numberOfLines++;
            }
            queue.setEndOfFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
