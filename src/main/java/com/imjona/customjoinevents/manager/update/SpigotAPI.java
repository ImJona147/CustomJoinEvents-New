package com.imjona.customjoinevents.manager.update;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static com.imjona.customjoinevents.utils.UtilsPlugin.sendMessageToConsole;

public class SpigotAPI {

    public static SpigotResource getSpigotResource(int id) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL("https://api.spigotmc.org/simple/0.2/index.php?action=getResource&id=" + id);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bf = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bf.readLine()) != null)
                response.append(line);
            bf.close();
        } catch (IOException e) {
            sendMessageToConsole("&7Ha ocurrido un error al intentar conectarse a la URL");
        }
        Gson gson = new Gson();
        return gson.fromJson(response.toString(), SpigotResource.class);
    }
}
