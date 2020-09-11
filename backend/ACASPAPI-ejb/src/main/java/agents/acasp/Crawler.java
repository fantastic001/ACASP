package agents.acasp;

import java.util.ArrayList;
import java.util.Collection;

public class Crawler implements Runnable, FoundPageListener {

    private String url; 
    private String word; 

    private Collection<FoundPageListener> listeners;

    public Crawler(String url, String word) {
        this.url = url; 
        this.word = word;
        listeners = new ArrayList<>();
    }

    @Override
    public void run() {
        Spider spider = new Spider();
        spider.addListener(this);
        spider.search(this.url, this.word);

    }

    public void addListener(FoundPageListener listener) {
        listeners.add(listener);
    }

    @Override
    public void onPage(String text) {
        for (FoundPageListener listener : listeners) {
            listener.onPage(text);
        }
    }

    
    
}