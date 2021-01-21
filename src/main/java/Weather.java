import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {
    public static String getWeather(String message, Model model) throws IOException {
        String link = "http://api.openweathermap.org/data/2.5/weather?q="+message+"&units=metric&appid=8d81a0df4e68c1de72ca0c55920b58c9";

        URL url = new URL(link);

        Scanner in = new Scanner((InputStream) url.getContent());

        String result = "";

        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject object1 = getArray.getJSONObject(i);
            model.setIcon((String) object1.get("icon"));
            model.setMain((String) object1.get("main"));
        }

        return "City:" + model.getName() + "\n" +
                "Temperature:" + model.getTemp() + "C" + "\n" +
                "Humidity:" + model.getHumidity() + "%" + "\n" +
                "Main:" + model.getMain();
    }
}
