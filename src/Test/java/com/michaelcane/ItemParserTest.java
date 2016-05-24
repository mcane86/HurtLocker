package com.michaelcane;

import jdk.nashorn.internal.runtime.ECMAException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by michaelcane on 5/24/16.
 */
public class ItemParserTest {
    ItemParser itemParser;

    @Before
    public void setUp() throws Exception {
        itemParser = new ItemParser();
    }

    @Test
    public void stringSplitterTest () throws Exception {
        String expected = "Milk";
        String actual = itemParser.stringSplitter(Main.readRawDataToString())[1];
        assertEquals(expected, actual);
    }

    @Test
    public void stringSplitterTestToFailure () {
        String expected = "";
        String actual = itemParser.stringSplitter("naME:;price:1.23;type:Food;expiration:1/02/2016##")[1];
        assertEquals(expected, actual);
    }

    @Test
    public void testItemCounter() throws Exception {
        try {
            String expected = "name:\tMilk\t\tseen: 6 times\n" +
                    "=============\t\t=============\n" +
                    "Price:\t3.23\t\tseen: 5 times\n" +
                    "-------------\t\t-------------\n" +
                    "Price:\t1.23\t\tseen: 1 times";
            itemParser.createItem(Main.readRawDataToString());
            String actual = itemParser.itemCounter("Milk");
            assertEquals(expected, actual);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Test
    public void nameCleanup() throws Exception {
        String expected = "Milk";
        String actual = itemParser.nameCleanup("milk");
        assertEquals(expected, actual);
    }

    @Test
    public void createItem() throws Exception {
        String expectedName = "Milk";
        String expectedPrice = "3.23";
        itemParser.createItem("naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##");
        String actualName = itemParser.getListOfItems().get(0).getName();
        String actualPrice = itemParser.getListOfItems().get(0).getPrice();
        assertEquals(expectedName, actualName);
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void addItemToList() throws Exception {
        int expectedLength = 1;
        itemParser.addItemToList("Milk", "1.23", "Food", "1/25/2016");
        int actualLength = itemParser.getListOfItems().size();
        assertEquals(expectedLength, actualLength);
    }

    @Test
    public void findItemField() throws Exception {
        String expected = "Milk";
        String actual = itemParser.findItemField(Main.readRawDataToString(), 1);
        assertEquals(expected, actual);
    }

    @Test
    public void findItemFieldExceptionErrorCounter() throws Exception {
        int errorsBeforeTheCounter = 1;
        String expected = "Bread";
        String actual = itemParser.findItemField("naME:;price:1.23;type:Food;expiration:1/02/2016##", 1);
        int errorsAfterTheCounter = itemParser.getErrorCount();
        assertEquals(errorsBeforeTheCounter, errorsAfterTheCounter);
    }

    @Test
    public void testGroceryListConstructor() throws Exception {
        String expected = "name:\tMilk\t\tseen: 6 times\n" +
                "=============\t\t=============\n" +
                "Price:\t3.23\t\tseen: 5 times\n" +
                "-------------\t\t-------------\n" +
                "Price:\t1.23\t\tseen: 1 times\n" +
                "\n" +
                "name:\tBread\t\tseen: 6 times\n" +
                "=============\t\t=============\n" +
                "Price:\t1.23\t\tseen: 6 times\n" +
                "-------------\t\t-------------\n" +
                "\n" +
                "name:\tCookies\t\tseen: 8 times\n" +
                "=============\t\t=============\n" +
                "Price:\t2.25\t\tseen: 8 times\n" +
                "-------------\t\t-------------\n" +
                "\n" +
                "name:\tApples\t\tseen: 4 times\n" +
                "=============\t\t=============\n" +
                "Price:\t0.25\t\tseen: 2 times\n" +
                "-------------\t\t-------------\n" +
                "Price:\t0.23\t\tseen: 2 times\n" +
                "\n" +
                "Errors\t\t\tseen: 4 times";
        itemParser.createItem(Main.readRawDataToString());
        String actual = itemParser.groceryListConstructor();
        assertEquals(expected, actual);
    }
}