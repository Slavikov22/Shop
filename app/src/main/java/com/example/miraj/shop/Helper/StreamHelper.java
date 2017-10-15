package com.example.miraj.shop.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamHelper {
    public static String convertInputStreamToString(InputStream iStream) throws IOException{
        StringBuilder sb = new StringBuilder();
        String line;

        if (iStream != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(iStream, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                iStream.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }
}
