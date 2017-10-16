import java.io.*;


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

        String line;
        try {
            for(int i = 0; (line = read.readLine()) != null; ++i) {
                queue.enqueue(line);
                numberOfLines++;
            }
            queue.setEndOfFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
