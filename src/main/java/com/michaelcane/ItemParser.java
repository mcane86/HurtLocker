package com.michaelcane;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class ItemParser {

    private int errorCount = 0;

    public int getErrorCount() {
        return errorCount;
    }

    //--- Regex Patterns
    private String namePattern = "([A-Za-z0-9.]+)";
    private String stringSplitPattern = "([;:^@%*!])";
    private String itemSplitPattern = "((##))";

    //--- Regex compilers
    private Pattern name = Pattern.compile(namePattern);
    Pattern splitter = Pattern.compile(stringSplitPattern);
    Pattern itemSplitter = Pattern.compile(itemSplitPattern);

    private ArrayList<Item> listOfItems = new ArrayList<Item>();

    public ArrayList<Item> getListOfItems() {
        return listOfItems;
    }

    public String[] stringSplitter (String string) {
        String[] splitStrings = string.split(stringSplitPattern);
        return splitStrings;
    }

    public String itemCounter(String string) {
        StringBuilder itemChart = new StringBuilder();
        LinkedHashMap<String, Integer> priceCounter = new LinkedHashMap<>();
        int stringCount = 0;
        for(Item item : listOfItems) {
            if(item.getName().equals(string)) {
                stringCount ++;
                if(priceCounter.containsKey(item.getPrice())) {
                    priceCounter.put(item.getPrice(), priceCounter.get(item.getPrice())+1);
                } else if (item.getPrice().equals("blank")) {
                    stringCount--;
                    continue;
                } else {
                    priceCounter.put(item.getPrice(), 1);
                }
            }

        }
        itemChart.append("name:\t" + string + "\t\t" + "seen: " + stringCount + " times\n");
        itemChart.append("=============\t\t=============\n");
        int counter  = 0;
        for(String key: priceCounter.keySet()) {
            itemChart.append("Price:\t" + key + "\t\t" + "seen: " + priceCounter.get(key) + " times");
            if(counter == 0) {
                itemChart.append("\n-------------\t\t-------------\n");
            }
            counter ++;
        }

        return itemChart.toString();
    }

    public String nameCleanup(String string) {
        if(string.toLowerCase().charAt(0) == 'm') {
            return "Milk";
        } else if (string.toLowerCase().charAt(0) == 'a') {
            return "Apples";
        } else if (string.toLowerCase().charAt(0) == 'b') {
            return "Bread";
        } else if (string.toLowerCase().charAt(0) == 'c') {
            return "Cookies";
        } else {
            return string;
        }
    }

    public String findItemField(String string, int index) throws AttributeNotFoundException {
        Matcher m = name.matcher((stringSplitter(string)[index]));
        if(m.find()) {
            return nameCleanup(m.group());
        } else {
            errorCount ++;
            return "blank";
        }
    }

    public void createItem(String input) throws AttributeNotFoundException {
        String[] itemArray = input.split(itemSplitPattern);
        for(int i = 0; i < itemArray.length; i ++) {
            addItemToList(findItemField(itemArray[i], 1), findItemField(itemArray[i],3), findItemField(itemArray[i], 5), findItemField(itemArray[i], 7));
        }
    }

    public void addItemToList(String name, String price, String type, String date) {
        listOfItems.add(new Item(name, price, type, date));
    }

    public String groceryListConstructor() {
        StringBuilder groceryList = new StringBuilder();
        groceryList.append(itemCounter("Milk"));
        groceryList.append("\n\n");
        groceryList.append(itemCounter("Bread"));
        groceryList.append("\n");
        groceryList.append(itemCounter("Cookies"));
        groceryList.append("\n");
        groceryList.append(itemCounter("Apples"));
        groceryList.append("\n\n");
        groceryList.append("Errors\t\t\tseen: " + errorCount + " times");
        return groceryList.toString();
    }

}
