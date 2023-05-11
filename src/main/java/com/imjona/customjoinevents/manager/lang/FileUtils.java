package com.imjona.customjoinevents.manager.lang;

import com.imjona.customjoinevents.CustomJoinEvents;
import com.imjona.customjoinevents.utils.UtilsPlugin;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtils {

    public static void insertData(String from, String to, CustomJoinEvents plugin) {
        File file = new File(to);
        file.delete();
        try {
            InputStream localInput = plugin.getResource(from);
            try {
                if (localInput != null) {
                    Paths.get(to);
                    Files.copy(localInput, Paths.get(to), StandardCopyOption.REPLACE_EXISTING);
                } else {
                    UtilsPlugin.sendMessageToConsole("&7InputStream is null for &b" + from);
                }
                if (localInput != null)
                    localInput.close();
            } catch (Throwable throwable) {
                if (localInput != null)
                    try {
                        localInput.close();
                    } catch (Throwable throwable1) {
                        throwable.addSuppressed(throwable1);
                    }
                throw throwable;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
