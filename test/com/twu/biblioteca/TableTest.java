package com.twu.biblioteca;

import org.junit.Test;

import static org.junit.Assert.*;

public class TableTest {
    @Test
    public void testTable() {
        var table = new Table("Title", "Author", "Year published");
        table.addRow("Compilers: Principles, Techniques, and Tools", "Alfred V. Aho, Monica S. Lam, Ravi Sethi, and Jeffrey D. Ullman", "1986");
        table.addRow("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein", "1990");
        table.addRow("Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma, Richard Helm, Ralph Johnson and John Vlissides", "1994");

        String expected = "| Title                                                          | Author                                                                      | Year published |\n" +
                "| -------------------------------------------------------------- | --------------------------------------------------------------------------- | -------------- |\n" +
                "| Compilers: Principles, Techniques, and Tools                   | Alfred V. Aho, Monica S. Lam, Ravi Sethi, and Jeffrey D. Ullman             | 1986           |\n" +
                "| Introduction to Algorithms                                     | Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein | 1990           |\n" +
                "| Design Patterns: Elements of Reusable Object-Oriented Software | Erich Gamma, Richard Helm, Ralph Johnson and John Vlissides                 | 1994           |";

        assertEquals(expected, table.toString());
    }
}
