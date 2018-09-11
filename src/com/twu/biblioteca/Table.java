package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.List;

class Table {
    private final String[] header;
    private final List<String[]> rows = new ArrayList<>();


    Table(String... header) {
        this.header = header;
    }

    private static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    private static String formatColumns(int[] widths, String[] columns) {
        int numColumns = widths.length;
        var padded = new String[numColumns];
        for (int i = 0; i < numColumns; i++) {
            padded[i] = padRight(columns[i], widths[i]);
        }
        return String.format("| %s |", String.join(" | ", padded));
    }

    private static String horizontalSeparator(int[] widths) {
        var sep = new String[widths.length];
        for (int i = 0; i < widths.length; i++) {
            sep[i] = new String(new char[widths[i]]).replace("\0", "-");
        }
        return formatColumns(widths, sep);
    }

    void addRow(String... columns) {
        if (columns.length != header.length) {
            throw new IllegalArgumentException("number of columns must be equal to number of columns in header");
        }
        this.rows.add(columns);
    }

    private int[] columnWidths() {
        int numColumns = header.length;
        var maxLengths = new int[numColumns];
        for (int i = 0; i < numColumns; i++) {
            maxLengths[i] = header[i].length();
        }
        for (String[] row : rows) {
            for (int i = 0; i < numColumns; i++) {
                if (row[i].length() > maxLengths[i]) {
                    maxLengths[i] = row[i].length();
                }
            }
        }
        return maxLengths;
    }

    @Override
    public String toString() {
        var widths = this.columnWidths();
        var lines = new ArrayList<String>(1 + this.rows.size());
        lines.add(formatColumns(widths, this.header));
        lines.add(horizontalSeparator(widths));
        for (String[] row : rows) {
            lines.add(formatColumns(widths, row));
        }
        return String.join("\n", lines);
    }
}
