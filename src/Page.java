import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
This class represents a page, which is equivalent to an article of the newspaper "Frankfurter Allgemeine Zeitung".
 */
public class Page implements Comparable {

    private String title;
    private TreeSet<String> keywords;
    private String description;
    //TODO image
    private String author;
    private Type type;
    private Date lastUpdated;
    private Date publication;
    private LinkedList<String> topics;
    //private LinkedList<String> categories;
    private boolean article;
    private String body;
    private TreeSet<String> subpages;    //3600
    private String permalink;

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

    public void setType(@NotNull String s) {
        if (s.equals("Bezahlartikel")) {
            this.type = Type.paid;
        } else {
            this.type = Type.free;
        }
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

    public void addTopic(@NotNull String s) {
        this.topics.add(s);
    }

    /*public LinkedList<String> getCategories() {
        return categories;
    }

    public void setCategories(LinkedList<String> categories) {
        this.categories = categories;
    }

    public void addCategory(String s) {
        this.categories.add(s);
    }*/

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

    public TreeSet<String> getSubpages() {
        return subpages;
    }

    public void setSubpages(TreeSet<String> subpages) {
        this.subpages = subpages;
    }

    public void addSubpage(String s) {
        this.subpages.add(s);
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public void createPage(String line) {
        String buffer;
        if ((buffer = extractString(Main.TITLE_REGEX, line)) != null) {
            this.setTitle(buffer);
        }
        if ((buffer = extractString(Main.KEYWORDS_REGEX, line)) != null) {
            String[] kwds = buffer.split(", ");
            Pattern pattern = Pattern.compile("[^A-Za-z]+");
            TreeSet<String> t = new TreeSet<>();
            for (int i = 0; i < kwds.length; i++) {
                Matcher matcher = pattern.matcher(kwds[i]);
                if (matcher.find()) continue;
                else t.add(kwds[i]);
            }
            this.setKeywords(t);
        }
        if ((buffer = extractString(Main.DESCRIPTION_REGEX, line)) != null) {
            this.setDescription(buffer);
        }
        if ((buffer = extractString(Main.AUTHOR_REGEX, line)) != null) {
            this.setAuthor(buffer);
        }
        if ((buffer = extractString(Main.TYPE_REGEX, line)) != null) {
            this.setType(buffer);
        }
        if ((buffer = extractString(Main.LASTUPDATED_REGEX, line)) != null) {
            Date lastUpdate = new Date();
            lastUpdate.setDate(buffer.split(" ")[0]);
            this.setLastUpdated(lastUpdate);
        }
        if ((buffer = extractString(Main.PUBLICATION_REGEX, line)) != null) {
            Date publication = new Date();
            publication.setDate(buffer.split(" ")[0]);
            this.setPublication(publication);
        }
        if ((buffer = extractString(Main.TOPICS_REGEX, line)) != null) {
            if (this.getTopics() == null) {
                this.setTopics(new LinkedList<String>());
                this.getTopics().add(buffer);
            } else {
                this.getTopics().add(buffer);
            }
        }
        if ((buffer = extractString(Main.ARTICLE_REGEX, line)) != null) {
            if (buffer.equals("Article")) this.setArticle(true);
            else this.setArticle(false);
        }
        if ((buffer = extractString(Main.BODY_REGEX, line)) != null) {
            this.setBody(buffer);
        }
        if ((buffer = extractString(Main.PERMALINK_REGEX, line)) != null) {
            this.setPermalink(buffer);
        }
        if ((buffer = extractString(Main.SUBPAGE_REGEX, line)) != null) {
            if (this.getSubpages() == null) {
                this.setSubpages(new TreeSet<>());
                this.addSubpage("https://www." + buffer + ".html");
            } else {
                this.addSubpage("https://www." + buffer + ".html");
            }
        }
    }

    public static String extractString(String regex, String line) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(1);
        }
        else return null;
    }

    public void printPage() {
        System.out.println(this.getTitle() + "\n" + this.getDescription() + "\n" + this.getAuthor() + "\n" + this.getType().name() + "\n" + this.getLastUpdated().getDate() + "\n" + this.getPublication().getDate()
                + "\n" + this.isArticle() + "\n" + this.getBody() + "\n" + this.getPermalink());
        if (this.getKeywords() != null) this.getKeywords().forEach(String -> System.out.println(String));
        else System.out.println("Test");
        if (this.getTopics() != null) this.getTopics().forEach(String -> System.out.println(String));
        if (this.getSubpages() != null) this.getSubpages().forEach(String -> System.out.println(String));
    }

    @Override
    public int compareTo(@NotNull Object o) {
        Page p = (Page) o;
        return this.getTitle().compareTo(p.getTitle());
    }
}
