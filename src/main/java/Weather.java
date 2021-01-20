import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {
    public static String getWeather(String message, Model model) throws IOException {
        String link = "http://api.openweathermap.org/data/2.5/weather?q="+message+"&units=metric&appid=#";
        System.out.println(link);

        URL url = new URL(link);

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while(in.hasNext()) {
            result += in.nextLine();
        }
        return result;
    }
}
