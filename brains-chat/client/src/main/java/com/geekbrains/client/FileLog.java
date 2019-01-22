package com.geekbrains.client;

import java.io.*;
import java.util.ArrayList;

public class FileLog {

    PrintWriter writer;

    void openWriter(String fileName) {
        try {
            writer = new PrintWriter(new FileWriter(fileName, true));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void writeMsg( String msg) {
        if (writer != null) {
            writer.println(msg);
        }
    }

    void closeWriter() {
        if (writer != null) {
            writer.flush();
            writer.close();
        }
    }

    public ArrayList<String> lastMessages(String fileName, int messagesNumber) {
        ArrayList<String> list = new ArrayList<>();
        String message;

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName)))  {
            while ((message = reader.readLine()) != null){
                list.add(message);
                if (list.size() > messagesNumber ) {
                    list.remove(messagesNumber - 1);
                }
            }
        }
        catch (IOException ex  ) {
            ex.printStackTrace();
        }

        return list;
    }

}
