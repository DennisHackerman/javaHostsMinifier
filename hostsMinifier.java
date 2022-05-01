import java.io.*;
import java.net.URL;
import java.nio.file.Path;

import static java.nio.file.Files.writeString;

public class hostMinifier {

    public static void main(String[] args) {

        final String hostsPath = "C:\\Windows\\System32\\drivers\\etc\\hosts";
        final String hostsURL = "https://someonewhocares.org/hosts/zero/hosts";

        File htmlPage = downloadWebpage(hostsURL);

        try {
            writeString(Path.of(hostsPath), minify(htmlPage.getPath()));
            htmlPage.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static File downloadWebpage(String urlString) {

        try {
            URL url = new URL(urlString);
            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String webpageText = "";
            String currentLine = "";

            while(currentLine != null) {
                webpageText += "\r\n" + currentLine;
                currentLine = br.readLine();
            }

            File hosts = new File("hosts.txt");
            PrintWriter prw = new PrintWriter(hosts);
            prw.print(webpageText);

            prw.close();
            br.close();
            is.close();

            return hosts;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Minifies a file and outputs it as a string
     * @param filepath path of the .txt file to minify
     * @return returns a correctly formatted string of the minified content, or null if something went wrong
     */
    static String minify(String filepath) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String currentLine = "";
            String output = "#Beginning of your trimmed down hosts file:\r\n127.0.0.1 localhost\r\n127.0.0.1 localhost.localdomain\r\n127.0.0.1 local\r\n255.255.255.255 broadcasthost\r\n::1 localhost\r\n::1 ip6-localhost\r\n::1 ip6-loopback\r\nfe80::1%lo0 localhost\r\nff00::0 ip6-localnet\r\nff00::0 ip6-mcastprefix\r\nff02::1 ip6-allnodes\r\nff02::2 ip6-allrouters\r\nff02::3 ip6-allhosts\r\n0.0.0.0 0.0.0.0";


            while(currentLine != null) {

                currentLine = removeComments(currentLine);

                currentLine = removeWhitespaces(currentLine);
                if(currentLine == null) {
                    currentLine = br.readLine();
                    continue;
                }

                if(!regexOK(currentLine)) {
                    currentLine = br.readLine();
                    continue;
                }

                if(!output.contains("\r\n" + currentLine + "\r\n")) {
                    output += "\r\n" + currentLine;
                }
                currentLine = br.readLine();
            }

            br.close();
            return output;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static String removeComments(String line) {

        if(line.contains("#")) {
            return line.substring(0, line.indexOf("#"));
        } else {
            return line;
        }
    }

    static String removeWhitespaces(String line) {

        line = line.strip();
        line = line.replaceAll(" {2,}", " ");
        line = line.replaceAll("\\t", " ");

        if(line.length() == 0) {
            return null;
        } else {
            return line;
        }
    }

    static boolean regexOK(String line) {

        return line.matches("([0-9]{1,3}\\.){3}[0-9]{1,3}\\s\\S+(\\.\\S+)+");
    }
}
