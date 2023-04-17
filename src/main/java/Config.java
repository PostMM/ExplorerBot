import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Config {

    public static String[] getConfig(String path) {

        String[] config = {"", ""};
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

            lines = reader.lines().collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[Config] FATAL ERROR: INVALID TOKEN");
        }

        for(int i = 0; i < lines.size() && i < 2; i++) {
            String[] splitLine;
            if ((splitLine = lines.get(i).split(":", 2)).length > 1) {
                config[i] = lines.get(i).split(":", 2)[1];
            }
        }

        return config;
    }

    public static void setConfig(String token, String user) {

        File config = new File(ExplorerBot.RES + "/config.txt");

        try (final FileWriter writer = new FileWriter(config)) {
            // Clear file
            PrintWriter pw = new PrintWriter(config);
            pw.flush();
            pw.close();

            writer.write("TOKEN:" + token + "\n" + "USER:" + user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
