import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public final static String FILE_URL = "https://www.faz.net/aktuell/gesellschaft/gesundheit/welche-ernaehrung-bei-chronischen-erkrankungen-hilft-16396863.html";
    public final static String FILE_NAME = "raw.txt";
    public final static String TITLE_REGEX = "<title>(.*?)</title>";
    public final static String KEYWORDS_REGEX = "<meta name=\"keywords\" content=\"(.*?)ZdK\"/>";
    public final static String DESCRIPTION_REGEX = "<meta property=\"og:description\" content=\"(.*?)\" />";
    public final static String AUTHOR_REGEX = "\"author\":\"(.*?)\",\"characterCount";
    public final static String TYPE_REGEX = ",\"type\":\"(Bezahlartikel|kostenloser Artikel)\",\"pagination\"";
    public final static String LASTUPDATE_REGEX = "\"publishedLast\":\"(.*?)\",\"containsComments";
    public final static String PUBLICATION_REGEX = "\"publishedFirst\":\"(.*?)\",\"publishedLast\"";
    public final static String TOPICS_REGEX = ",\"name\":\"(?![A-Z][A-Z])(?![A-Z][a-z]*\\s[A-Z])([A-Z][a-z]+)\"}},";
    public final static String ARTICLE_REGEX = ",\"@type\":\"(.*?)\",\"mainEntityOfPage";
    public final static String BODY_REGEX = "\"articleBody\":\"(.*?)\",\"datePublished\"";
    public final static String PERMALINK_REGEX = "lay-Sharing_PermalinkUrl\">(.*?)</span>";
    public final static String SUBPAGE_REGEX = "href=\"https://www.(?!faz.net/aktuell/reise/routenplaner/)(?!faz.net/ueber-uns/)(?!faz.net/hilfe/)(?!faz.net/faz-net-services/)(?!faz.net/datenschutzerklaerung)(?!faz.net/asv/vor-denker/)(?!faz.net/allgemeine-nutzungsbedingungen)(.*?).html\"";

    public static void main (String[] args) {
        Download.fromPath(FILE_URL);
        Page p = new Page();
        String line, buffer;
        try {
            BufferedReader in = new BufferedReader(new FileReader("raw.txt"));
            while((line = in.readLine()) != null) {
                if ((buffer = extractString(TITLE_REGEX, line)) != null) {
                    p.setTitle(buffer);
                    System.out.println(p.getTitle());
                }
                if ((buffer = extractString(KEYWORDS_REGEX, line)) != null) {
                    String[] kwds = buffer.split(", ");
                    p.setKeywords(new TreeSet(Arrays.asList(kwds)));
                    System.out.println(p.getKeywords());
                }
                if ((buffer = extractString(DESCRIPTION_REGEX, line)) != null) {
                    p.setDescription(buffer);
                    System.out.println(p.getDescription());
                }
                if ((buffer = extractString(AUTHOR_REGEX, line)) != null) {
                    p.setAuthor(buffer);
                    System.out.println(p.getAuthor());
                }
                if ((buffer = extractString(TYPE_REGEX, line)) != null) {
                    p.setType(buffer);
                    System.out.println(p.getType().name());
                }
                if ((buffer = extractString(LASTUPDATE_REGEX, line)) != null) {
                    Date lastUpdate = new Date();
                    lastUpdate.setDate(buffer.split(" ")[0]);
                    p.setLastUpdated(lastUpdate);
                    System.out.println(p.getLastUpdated().getDate());
                }
                if ((buffer = extractString(PUBLICATION_REGEX, line)) != null) {
                    Date publication = new Date();
                    publication.setDate(buffer.split(" ")[0]);
                    p.setPublication(publication);
                    System.out.println(p.getPublication().getDate());
                }
                if ((buffer = extractString(TOPICS_REGEX, line)) != null) {
                    if (p.getTopics() == null) {
                        p.setTopics(new LinkedList<String>());
                        p.getTopics().add(buffer);
                    } else {
                        p.getTopics().add(buffer);
                    }
                    p.getTopics().forEach(String -> System.out.println(String));
                }
                if ((buffer = extractString(ARTICLE_REGEX, line)) != null) {
                    if (buffer.equals("Article")) p.setArticle(true);
                    else p.setArticle(false);
                    System.out.println(p.isArticle());
                }
                if ((buffer = extractString(BODY_REGEX, line)) != null) {
                    p.setBody(buffer);
                    System.out.println(buffer);
                }
                if ((buffer = extractString(PERMALINK_REGEX, line)) != null) {
                    p.setPermalink(buffer);
                    System.out.println(p.getPermalink());
                }
                if ((buffer = extractString(SUBPAGE_REGEX, line)) != null) {
                    if (p.getSubpages() == null) {
                        p.setSubpages(new TreeSet<>());
                        p.addSubpage("https://www." + buffer + ".html");
                    } else {
                        p.addSubpage("https://www." + buffer + ".html");
                    }
                }
            }
            p.getSubpages().forEach(String -> System.out.println(String));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("Error reading file.");
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

}
