package com.michaelcane;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by michaelcane on 5/24/16.
 */
public class ItemParserTest {
    ItemParser itemParser;

    double delta = 1e-15;

    @Before
    public void setUp() throws Exception {
        itemParser = new ItemParser();
    }

    @Test
    public void stringSplitterTest () {
        String expected = "BreaD";
        String actual = itemParser.stringSplitter("naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##")[1];
        assertEquals(expected, actual);
    }

    @Test
    public void stringSplitterTestToFailure () {
        String expected = "";
        String actual = itemParser.stringSplitter("naME:;price:1.23;type:Food;expiration:1/02/2016##")[1];
        assertEquals(expected, actual);
    }

    @Test
    public void testItemCounter() throws AttributeNotFoundException {
        String expected = "name:    Milk \t\t seen: 6 times\n" +
                "============= \t \t =============\n" +
                "Price: \t 3.23\t\t seen: 5 times\n" +
                "-------------\t\t -------------\n" +
                "Price:   1.23\t\t seen: 1 time";
        itemParser.createItem("naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##");
        String actual = itemParser.itemCounter("Milk");
        assertEquals(expected, actual);
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
        double expectedPrice = 3.23;
        itemParser.createItem("naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##");
        String actualName = itemParser.getListOfItems().get(0).getName();
        double actualPrice = itemParser.getListOfItems().get(0).getPrice();
        assertEquals(expectedName, actualName);
        assertEquals(expectedPrice, actualPrice, delta);
    }

    @Test
    public void addItemToList() throws Exception {
        int expectedLength = 1;
        itemParser.addItemToList("Milk", 1.23, "Food", "1/25/2016");
        int actualLength = itemParser.getListOfItems().size();
        assertEquals(expectedLength, actualLength);
    }

    @Test
    public void findItemField() throws Exception {
        String expected = "Bread";
        String actual = itemParser.findItemField("naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##", 1);
        assertEquals(expected, actual);
    }

    @Test(expected = AttributeNotFoundException.class)
    public void findItemFieldExceptionTest() throws Exception {
        String expected = "Bread";
        String actual = itemParser.findItemField("naME:;price:1.23;type:Food;expiration:1/02/2016##", 1);
    }

    @Test
    public void findItemFieldExceptionErrorCounter() throws Exception {
        int errorsBeforeTheCounter = 1;
        try {
            String expected = "Bread";
            String actual = itemParser.findItemField("naME:;price:1.23;type:Food;expiration:1/02/2016##", 1);
        } catch (AttributeNotFoundException e) {        }
        int errorsAfterTheCounter = itemParser.getErrorCount();
        assertEquals(errorsBeforeTheCounter, errorsAfterTheCounter);
    }

}