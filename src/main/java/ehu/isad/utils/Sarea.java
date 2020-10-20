package ehu.isad.utils;
import com.google.gson.Gson;
import ehu.isad.Book;

import java.net.*;
import java.io.*;

public class Sarea {

    private static Gson gson;


    public static Book URLlortu(String s) throws Exception {
        java.net.URL oracle = new java.net.URL("https://openlibrary.org/api/books?bibkeys="+s+"&jscmd=details&format=json");
        System.out.println(oracle);
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

        String inputLine = in.readLine();

        in.close();

        System.out.println(inputLine);

        String[] zatiak = inputLine.split("\""+s+"\": ");
        Gson gson = new Gson();
        Book emaitza =  gson.fromJson(zatiak[1].substring(0, zatiak[1].length()-1),  Book.class);
        return emaitza;
    }

}



