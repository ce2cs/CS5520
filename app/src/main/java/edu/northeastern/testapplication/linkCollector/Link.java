package edu.northeastern.testapplication.linkCollector;

public class Link {

    private final String url;
    private final String name;

    public Link(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
                          return name;
                                      }

    public String getUrl() {
        return url;
    }
}
