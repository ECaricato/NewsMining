import java.util.LinkedList;

import java.util.TreeSet;

public class Page {

    private String title;
    private TreeSet<String> keywords;
    private String description;
    //TODO image
    private String author;
    private Type type;
    private Date lastUpdated;
    private Date publication;
    private LinkedList<String> topics;
    private LinkedList<String> categories;
    private boolean article;
    private String body;
    private LinkedList<String> subpages;

    public Page() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TreeSet<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(TreeSet<String> keywords) {
        this.keywords = keywords;
    }

    public void addKeyword(String s) {
        this.keywords.add(s);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getPublication() {
        return publication;
    }

    public void setPublication(Date publication) {
        this.publication = publication;
    }

    public LinkedList<String> getTopics() {
        return topics;
    }

    public void setTopics(LinkedList<String> topics) {
        this.topics = topics;
    }

    public void addTopic(String s) {
        this.topics.add(s);
    }

    public LinkedList<String> getCategories() {
        return categories;
    }

    public void setCategories(LinkedList<String> categories) {
        this.categories = categories;
    }

    public void addCategory(String s) {
        this.categories.add(s);
    }

    public boolean isArticle() {
        return article;
    }

    public void setArticle(boolean article) {
        this.article = article;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LinkedList<String> getSubpages() {
        return subpages;
    }

    public void setSubpages(LinkedList<String> subpages) {
        this.subpages = subpages;
    }

    public void addSubpage(String s) {
        this.subpages.add(s);
    }
}
