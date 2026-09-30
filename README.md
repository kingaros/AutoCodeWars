Держи единый, полностью укомплектованный `README.md` без блока Security:

```markdown
# ⚡ AutoCodeWars

An automated solution solver for [CodeWars](https://www.codewars.com/) built with **Java 22** and **Spring Boot**. The system uses Selenium WebDriver to navigate through CodeWars katas/courses and process challenges automatically with configurable execution speed.

---

## 🛠️ Tech Stack & Dependencies

- **Language:** Java 22
- **Framework:** Spring Boot `3.2.3`
- **Automation:** Selenium Java `4.18.1`
- **Driver Management:** WebDriverManager `5.7.0`
- **Build Tool:** Apache Maven
- **IDE:** IntelliJ IDEA 2026.1.4

---

## 📋 Features

- 🏎️️ **Configurable Speed:** Custom delay/execution rate managed directly in application parameters.
- 🎯 **Course & Kata Completion:** Automatically handles navigation, code submission, and task verification on CodeWars.
- 🦊 **Multi-Browser Support:** Out-of-the-box support for **Mozilla Firefox**, with quick setup for **Google Chrome**.

---

## ⚙️ Configuration & Requirements

### 1. Java 22 Setup
Ensure you have **JDK 22** installed and configured in your environment before building.

### 2. Browser Options (Firefox vs. Chrome)

By default, the driver is configured for **Firefox**:

```java
FirefoxOptions options = new FirefoxOptions();
// Configure profile and driver initialization
WebDriver driver = new FirefoxDriver(options);

```

#### Switching to Google Chrome:

To switch the execution engine to Chrome, update the driver initialization in your code:

1. Replace `FirefoxOptions` with `ChromeOptions`.
2. Replace `FirefoxDriver` with `ChromeDriver`.
3. Set your custom profile path in `ChromeOptions` if needed.

---

## 📦 Building and Executing

### Build the Executable Fat JAR

Run the following command in the project root folder:

```bash
mvn clean package -DskipTests

```

### Run the Application

Once compiled, launch the resulting `.jar` file located in the `target/` directory:

```bash
java -jar target/AutoCodeWars-1.0-SNAPSHOT.jar

```