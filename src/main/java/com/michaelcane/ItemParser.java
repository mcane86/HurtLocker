package com.michaelcane;

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
    private String stringSplitPattern = "([;:^@%*])";
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
        int stringCount = 0;
        for(Item item : listOfItems) {
            if(item.getName().equals(string)) {
                stringCount ++;
            }
        }
        return "name:\t" + string + "\t\t" + "seen: " + stringCount + " times\n";
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
            throw new AttributeNotFoundException();
        }
    }

    public void createItem(String input) throws AttributeNotFoundException {
        String[] itemArray = input.split(itemSplitPattern);
        for(int i = 0; i < itemArray.length; i ++) {
            addItemToList(findItemField(itemArray[i], 1), Double.parseDouble(findItemField(itemArray[i], 3)), findItemField(itemArray[i], 5), findItemField(itemArray[i], 7));
        }
    }

    public void addItemToList(String name, double price, String type, String date) {
        listOfItems.add(new Item(name, price, type, date));
    }


}
