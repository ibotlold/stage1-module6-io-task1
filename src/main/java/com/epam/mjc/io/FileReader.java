package com.epam.mjc.io;

import java.io.*;
import java.util.HashMap;


public class FileReader {

    public Profile getDataFromFile(File file) {
        HashMap<String, String> map = new HashMap<>();
        try (FileInputStream fileStream = new FileInputStream(file)) {
            int ch;
            StringBuilder builder = new StringBuilder();
            String key;
            String value;
            while ((ch = fileStream.read()) != -1) {
                builder.delete(0, builder.length());
                builder.append((char) ch);
                while ((ch = fileStream.read()) != -1) {
                    if ((char) ch == ':') {
                        break;
                    }
                    builder.append((char) ch);
                }
                key = builder.toString();
                builder.delete(0, builder.length());
                if (fileStream.skip(1) != 1) {
                    return null;
                }
                while ((ch = fileStream.read()) != -1) {
                    if ((char) ch == '\n') {
                        break;
                    }
                    builder.append((char) ch);
                }
                value = builder.toString();
                map.put(key, value);
            }
        } catch (IOException e) {
            return null;
        }
        return new Profile(map.get("Name"), Integer.parseInt(map.get("Age")), map.get("Email"), Long.parseLong(map.get("Phone")));
    }
}
