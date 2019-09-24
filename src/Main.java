import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static TreeSet<Page> pageSet = new TreeSet<>();
    public final static String FILE_URL = "https://www.faz.net/aktuell/gesellschaft/gesundheit/welche-ernaehrung-bei-chronischen-erkrankungen-hilft-16396863.html";
    public final static String FILE_NAME = "raw.txt";
    public final static String TITLE_REGEX = "<title>(.*?)</title>";
    public final static String KEYWORDS_REGEX = "<meta name=\"keywords\" content=\"(.*?)\"/>";
    public final static String DESCRIPTION_REGEX = "<meta property=\"og:description\" content=\"(.*?)(\" />|\\r\\n|\\r|\\n|\\.)";
    public final static String AUTHOR_REGEX = "\"author\":\"(.*?)\",\"characterCount";
    public final static String TYPE_REGEX = ",\"type\":\"(Bezahlartikel|kostenloser Artikel)\",\"pagination\"";
    public final static String LASTUPDATED_REGEX = "\"publishedLast\":\"(.*?)\",\"containsComments";
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
            BufferedReader in = new BufferedReader(new FileReader(FILE_NAME));
            while((line = in.readLine()) != null) {
                p.createPage(line);
            }
            pageSet.add(p);
            pageSet.forEach(Page -> Page.printPage());
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }



}
