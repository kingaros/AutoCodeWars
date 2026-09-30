package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;

@Component
public class CodewarAutomaticRunner implements CommandLineRunner {

    // Начальное значение должно совпадать с раскладкой в трее Windows перед запуском
    private boolean isRussianActive = true;
    private static final int CHAR_DELAY_MS = 350;


    @Override
    public void run(String... args) {
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary("C:\\Program Files\\Zen Browser\\zen.exe");

        options.addArguments("--no-remote");
        options.addArguments("--disable-blink-features=AutomationControlled");

        File profileDir = new File("C:\\Users\\Asrock\\AppData\\Roaming\\zen\\Profiles\\ahq4wx8j.Default (release)");
        if (profileDir.exists()) {
            FirefoxProfile profile = new FirefoxProfile(profileDir);
            options.setProfile(profile);
        } else {
            System.err.println("Указанный путь к профилю не найден: " + profileDir.getAbsolutePath());
        }

        WebDriver driver = new FirefoxDriver(options);
        Actions actions = new Actions(driver);

        try {
            String initialUrl = "https://codewar.ru/cabinet/learning/dvuiazycnyi-nabor/perekliucenie-tuda-i-obratno/";
            driver.get(initialUrl);
            driver.manage().window().maximize();

            System.out.println("Даётся 10 секунд на прогрузку авторизованного профиля...");
            Thread.sleep(10000);

            Robot robot = new Robot();
            int levelCounter = 1;

            while (true) {
                System.out.println("\n==========================================");
                System.out.println("Старт уровня #" + levelCounter + " | URL: " + driver.getCurrentUrl());

                WebElement textElement = null;

                while (textElement == null) {
                    try {
                        textElement = driver.findElement(By.cssSelector("#training_text"));
                    } catch (Exception e) {
                        Thread.sleep(1000);
                    }
                }

                String codeToType = "";
                try {
                    codeToType = textElement.getText();
                } catch (Exception e) {
                    Thread.sleep(1000);
                    continue;
                }

                if (codeToType.trim().isEmpty()) {
                    Thread.sleep(1000);
                    continue;
                }

                System.out.println("Текст для ввода: " + codeToType);

                try {
                    textElement.click();
                } catch (Exception e) {
                    System.err.println("Ошибка клика по элементу ввода, пробуем повторить...");
                    Thread.sleep(1000);
                    continue;
                }
                Thread.sleep(500);

                for (char ch : codeToType.toCharArray()) {
                    typeCharacter(robot, actions, ch);
                    Thread.sleep(CHAR_DELAY_MS);
                }

                System.out.println("Уровень #" + levelCounter + " успешно введён!");
                levelCounter++;

                Thread.sleep(3000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                driver.quit();
            } catch (Exception ignored) {}
        }
    }

    private void typeCharacter(Robot robot, Actions actions, char ch) throws InterruptedException {
        // 1. Управление раскладкой ОС через Robot (если язык текста меняется)
        boolean isCyr = isCyrillicChar(ch);
        boolean isEng = isEnglishLetter(ch);

        if (isCyr && !isRussianActive) {
            switchLayout(robot);
            isRussianActive = true;
        } else if (isEng && isRussianActive) {
            switchLayout(robot);
            isRussianActive = false;
        }

        // 2. Безопасный и точный ввод через Selenium Actions
        if (ch == '\n' || ch == '\r') {
            actions.sendKeys(Keys.ENTER).perform();
        } else if (ch == '\t') {
            actions.sendKeys(Keys.TAB).perform();
        } else {
            actions.sendKeys(String.valueOf(ch)).perform();
        }
    }

    private void switchLayout(Robot robot) throws InterruptedException {
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.keyRelease(KeyEvent.VK_ALT);
        Thread.sleep(200); // Время на обработку смены языка в Windows и браузерe
    }

    private boolean isCyrillicChar(char ch) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(ch);
        return block == Character.UnicodeBlock.CYRILLIC || ch == '№';
    }

    private boolean isEnglishLetter(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }
}