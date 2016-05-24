package com.michaelcane;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemTest {
    Item item;
    @Before
    public void setUp() throws Exception {
        item = new Item("Milk", "3.23", "Food", "05/24/2016");
    }

    @Test
    public void getName() throws Exception {
        String expected = "Milk";
        String actual = item.getName();
        assertEquals(expected, actual);
    }

    @Test
    public void setName() throws Exception {
        String expected = "George";
        item.setName("George");
        String actual = item.getName();
        assertEquals(expected, actual);
    }

    @Test
    public void getPrice() throws Exception {
        String expected = "3.23";
        String actual = item.getPrice();
        assertEquals(expected, actual);
    }

    @Test
    public void setPrice() throws Exception {
        String expected = "7.50";
        item.setPrice("7.50");
        String actual = item.getPrice();
        assertEquals(expected, actual);
    }

    @Test
    public void getType() throws Exception {
        String expected = "Food";
        String actual = item.getType();
        assertEquals(expected, actual);
    }

    @Test
    public void setType() throws Exception {
        String expected = "Book";
        item.setType("Book");
        String actual = item.getType();
        assertEquals(expected, actual);
    }

    @Test
    public void getExpiration() throws Exception {
        String expected = "05/24/2016";
        String actual = item.getExpiration();
        assertEquals(expected, actual);
    }

    @Test
    public void setExpiration() throws Exception {
        String expected = "12/31/1999";
        item.setExpiration("12/31/1999");
        String actual = item.getExpiration();
        assertEquals(expected, actual);
    }

}