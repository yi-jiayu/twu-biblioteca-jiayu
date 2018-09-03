package com.twu.biblioteca;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Options {
    private InputStream is = System.in;
    private PrintStream os = System.out;

    private String prompt;
    private List<String> keys = new ArrayList<>();
    private List<String> options = new ArrayList<>();

    private static final String invalidChoiceErrorText = "No such option! Please try again:";
    private static final String invalidInputErrorText = "Enter the number corresponding to your selected option:";

    Options(String prompt) {
        this.prompt = prompt;
    }

    public Options(InputStream is, PrintStream os, String prompt) {
        this.is = is;
        this.os = os;
        this.prompt = prompt;
    }

    void addOption(String key, String text) {
        this.keys.add(key);
        this.options.add(text);
    }

    private void show() {
        this.os.println(this.prompt);
        for (int i = 0; i < this.keys.size(); i++) {
            // i + 1 so the options are numbered starting from 1
            this.os.printf("(%d) %s\n", i + 1, options.get(i));
        }
    }

    String getChoice() {
        var sc = new Scanner(this.is);
        while (true) {
            this.show();
            try {
                var index = Integer.parseInt(sc.nextLine());
                // choice - 1 here to convert it back to a 0-indexed value
                return this.keys.get(index - 1);
            } catch (NumberFormatException e) {
                this.os.println(invalidInputErrorText);
            } catch (IndexOutOfBoundsException e) {
                this.os.println(invalidChoiceErrorText);
            }
        }
    }
}
