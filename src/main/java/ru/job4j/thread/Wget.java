package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("src/main/java/ru/job4j/thread/file.xml")) {
            byte[] dataBuffer = new byte[speed];
            int bytesReadied;

            while ((bytesReadied = in.read(dataBuffer, 0, speed)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesReadied);
                if (bytesReadied < speed) {
                    Thread.sleep(bytesReadied * 1_000L / speed);
                }
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void validationArguments(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Not enough arguments, add additional arguments and try again");
        }
        if (!args[1].matches("^(100)$")) {
            throw new IllegalArgumentException("Not enable data format");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validationArguments(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}