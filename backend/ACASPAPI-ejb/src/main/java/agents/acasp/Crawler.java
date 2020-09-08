package agents.acasp;

public class Crawler implements Runnable {

    private String url; 
    private String word; 

    public Crawler(String url, String word) {
        this.url = url; 
        this.word = word;
    }

    @Override
    public void run() {
        Spider spider = new Spider();
        spider.search(this.url, this.word);

    }

    
    
}