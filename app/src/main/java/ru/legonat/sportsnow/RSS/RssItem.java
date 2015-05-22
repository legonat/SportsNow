package ru.legonat.sportsnow.RSS;

/**
 * A representation of an rss item from the list.
 *
 * @author Veaceslav Grec
 *
 */
public class RssItem {

    private final String title;
    private final String link;
    private final String date;

    public RssItem(String title, String link, String date){
        this.title = title;
        this.link = link;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDate() {
        return date;
    }


}
