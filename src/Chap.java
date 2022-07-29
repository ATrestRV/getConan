import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.ArrayList;

public class Chap {
    private String url;
    private String name;
    private String path;
    private File file;
    private int count = 0;
    ArrayList<Image> images = new ArrayList<>();
    public Chap(String url, String path) {
        this.url = url;
        name = convertToName(url);
        this.path = path+name;
    }

    public Chap() {
        System.out.println("Enter Chap's URL:");
        url = Room.scanner.nextLine();
        System.out.println("Enter path");
        path = Room.scanner.nextLine();
        this.path = path+convertToName(url);
    }

    private boolean createFolder() {
        file = new File(path);
        return file.mkdirs();
    }

    private String convertToName(String url) {
        int prefix = url.indexOf("topic/")+6;
        int suffix = url.indexOf("-tieng");
        return url.substring(prefix,suffix);
    }

    private void getImages() throws IOException {
        Document doc = Jsoup.connect(url).get();
        for (Element element:doc.select("div.bbWrapper").select("img.bbImage")) {
            String link = element.absUrl("src");
            if (link.contains("conan") || link.contains("Conan") || link.contains("police") || link.contains("Police")) {
                count++;
                images.add(new Image(link, count +".jpg"));
            }
        }
    }

    public void run() throws IOException {
        createFolder();
        getImages();
        for (Image img:images) {
            img.download(path);
        }
    }
    public String getName() {
        return name;
    }
    public int getCount() {
        return count;
    }
}
