package Entities;

import org.jetbrains.annotations.NotNull;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
This class represents a page, which is equivalent to an article of the newspaper "Frankfurter Allgemeine Zeitung".
 */
public class Page {

    private final static String TITLE_REGEX = "<title>(.*?)</title>";
    private final static String KEYWORDS_REGEX = "<meta name=\"keywords\" content=\"(.*?)\"/>";
    private final static String DESCRIPTION_REGEX = "<meta property=\"og:description\" content=\"(.*?)(\" />|\\r\\n|\\r|\\n)";
    private final static String AUTHOR_REGEX = "\"author\":\"(.*?)\",\"characterCount";
    private final static String TYPE_REGEX = ",\"type\":\"(Bezahlartikel|kostenloser Artikel)\",\"pagination\"";
    private final static String LASTUPDATED_REGEX = "\"publishedLast\":\"(.*?)\",\"containsComments";
    private final static String PUBLICATION_REGEX = "\"publishedFirst\":\"(.*?)\",\"publishedLast\"";
    private final static String TOPICS_REGEX = ",\"name\":\"(?![A-Z][A-Z])(?![A-Z][a-z]*\\s[A-Z])([A-Z][a-z]+)\"}},";
    private final static String ARTICLE_REGEX = ",\"@type\":\"(.*?)\",\"mainEntityOfPage";
    private final static String BODY_REGEX = "\"articleBody\":\"(.*?)\",\"datePublished\"";
    private final static String PERMALINK_REGEX = "lay-Sharing_PermalinkUrl\">(.*?)</span>";
    final static String SUBPAGE_REGEX = "href=\"https://www.faz.net/aktuell(?!/news-des-tages-per-whatsapp-telegram)(?!/reise/routenplaner/)(?!ueber-uns/)(?!hilfe/)(?!faz-net-services/)(?!datenschutzerklaerung)(?!asv/vor-denker/)(?!allgemeine-nutzungsbedingungen)(.*?).html\"";

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
        if ((buffer = extractString(TITLE_REGEX, line)) != null) {
            this.setTitle(buffer);
        }
        if ((buffer = extractString(KEYWORDS_REGEX, line)) != null) {
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
        if ((buffer = extractString(DESCRIPTION_REGEX, line)) != null) {
            this.setDescription(buffer);
        }
        if ((buffer = extractString(AUTHOR_REGEX, line)) != null) {
            this.setAuthor(buffer);
        }
        if ((buffer = extractString(TYPE_REGEX, line)) != null) {
            this.setType(buffer);
        }
        if ((buffer = extractString(LASTUPDATED_REGEX, line)) != null) {
            Date lastUpdate = new Date();
            lastUpdate.setDate(buffer.split(" ")[0]);
            this.setLastUpdated(lastUpdate);
        }
        if ((buffer = extractString(PUBLICATION_REGEX, line)) != null) {
            Date publication = new Date();
            publication.setDate(buffer.split(" ")[0]);
            this.setPublication(publication);
        }
        if ((buffer = extractString(TOPICS_REGEX, line)) != null) {
            if (this.getTopics() == null) {
                this.setTopics(new LinkedList<String>());
                this.getTopics().add(buffer);
            } else {
                this.getTopics().add(buffer);
            }
        }
        if ((buffer = extractString(ARTICLE_REGEX, line)) != null) {
            if (buffer.equals("Article")) this.setArticle(true);
            else this.setArticle(false);
        }
        if ((buffer = extractString(BODY_REGEX, line)) != null) {
            this.setBody(buffer);
        }
        if ((buffer = extractString(PERMALINK_REGEX, line)) != null) {
            this.setPermalink(buffer);
        }
        if ((buffer = extractString(SUBPAGE_REGEX, line)) != null) {
            if (this.getSubpages() == null) {
                this.setSubpages(new TreeSet<>());
                this.addSubpage("https://www.faz.net/aktuell" + buffer + ".html");
            } else {
                this.addSubpage("https://www.faz.net/aktuell" + buffer + ".html");
            }
        }
    }

    public void printPage() {
        System.out.println(this.getTitle() + "\n" + this.getDescription() + "\n" + this.getAuthor() + "\n" + this.getType().name() + "\n" + this.getLastUpdated().getDate() + "\n" + this.getPublication().getDate()
                + "\n" + this.isArticle() + "\n" + this.getBody() + "\n" + this.getPermalink());
        if (this.getKeywords() != null) this.getKeywords().forEach(String -> System.out.println(String));
        else System.out.println("Test");
        if (this.getTopics() != null) this.getTopics().forEach(String -> System.out.println(String));
        //if (this.getSubpages() != null) this.getSubpages().forEach(String -> System.out.println(String));
    }

    public static String extractString(String regex, String line) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(1);
        }
        else return null;
    }

}
