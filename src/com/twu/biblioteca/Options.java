package com.twu.biblioteca;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Options {
    private InputStream is = System.in;
    private PrintStream os = System.out;

    private final String prompt;
    private final List<String> keys = new ArrayList<>();
    private final List<String> options = new ArrayList<>();

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

    String getChoice() throws InvalidChoiceException {
        var sc = new Scanner(this.is);
        this.show();
        try {
            var index = Integer.parseInt(sc.nextLine());
            // choice - 1 here to convert it back to a 0-indexed value
            return this.keys.get(index - 1);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new InvalidChoiceException();
        }
    }
}
