package org.example.model.repository;

import org.example.model.domain.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadConnection implements Connector{
    static List<Invoice> invoiceLogRetrieved = new ArrayList<>();
    static List<Product> stockListRetrieved = new ArrayList<>();
    static File invoiceLogDB = new File("InvoiceLogDB.txt");
    static File stockListDB = new File("StockListDB.txt");

    @Override
    public void connect() {
    }

    public List<Invoice> invoiceLogReader(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(invoiceLogDB));
            String line = "";

            while((line = reader.readLine()) != null) {
                parseInvoiceEntry(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return invoiceLogRetrieved;
    }
    public static void parseInvoiceEntry(String line) {
        Pattern pattern = Pattern.compile("Id = (\\d+) \\| \\[(.*?)\\] \\| TOTAL: (\\d+\\.\\d+)€");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            int id = Integer.parseInt(matcher.group(1));
            String contentsInBrackets = matcher.group(2);
            double total = Double.parseDouble(matcher.group(3));

            Invoice invoice = new Invoice(id,contentsInBrackets,total);
            invoiceLogRetrieved.add(invoice);
        } else {
            System.out.println("Line does not match the expected format: " + line);
        }
    }
    public List<Product> stockListReader(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(stockListDB));
            String line = "";

            while((line = reader.readLine()) != null) {
                parseStockListEntry(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stockListRetrieved;
    }

    public static void parseStockListEntry(String line){
        Product p;
        Pattern pattern = Pattern.compile("Id = (\\d+) \\| Name = ([^|]+) \\| Price = (\\d+\\.\\d+) " +
                "\\| (Is wood\\? = (true|false) \\| )?(Color = ([^|]+) \\| )?" +
                "(Height = (\\d+\\.\\d+) \\| )?Type = ([^|]+)");
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            int id = Integer.parseInt(matcher.group(1));
            String name = matcher.group(2);
            double price = Double.parseDouble(matcher.group(3));
            String type = matcher.group(10); // Type is always captured

            // Variables specific to each type
            String specialAttribute = "";
            if ("Decoration".equals(type)) {
                boolean isWood = Boolean.parseBoolean(matcher.group(5));
                p = new Decoration(id,name,price,isWood);
                stockListRetrieved.add(p);
            } else if ("Flower".equals(type)) {
                String color = matcher.group(7);
                p = new Flower(id,name,price,color);
                stockListRetrieved.add(p);
            } else if ("Tree".equals(type)) {
                float height = Float.parseFloat(matcher.group(9));
                p = new Tree(id,name,price,height);
                stockListRetrieved.add(p);
            }
        }
    }
}
