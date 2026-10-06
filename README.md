# Tealium Ecommerce – Web Automation

Automated tests for the [Tealium Ecommerce Demo](https://ecommerce.tealiumdemo.com) website.

## What is tested?

| Feature | Test cases |
|---|---|
| Registration | Register with valid data · Register with empty fields |
| Login | Login with valid data · Wrong password · Unregistered email |
| Add to Cart | Login → Accessories → Shoes → Sort by price → Choose shoes → Choose color & size → Add to cart |

All test cases are written in `docs/TestCases.xlsx`.

## Tools

| Tool | Why |
|---|---|
| Java + Selenium | Free and the most popular tool for web automation |
| TestNG | Runs the tests and reads test data with `@DataProvider` |
| Maven | Downloads the libraries and runs the tests with one command |
| Apache POI | Reads test data from Excel, so data can change without changing code |
| Allure | Easy-to-read HTML report with steps and screenshots |

## Before you start

Install:
- Java 17
- Maven (or use IntelliJ IDEA, it has Maven inside)
- Google Chrome

## How to run the tests

```bash
mvn clean test
```

Or in IntelliJ: right-click `testng.xml` → **Run**.

## How to see the report

```bash
mvn allure:serve
```

The report opens in your browser. It shows every test, its steps and a screenshot.

## Project folders

| Folder | What is inside |
|---|---|
| `src/main/java/pages` | One class for each page of the website (Page Object Model) |
| `src/main/java/base` | `BasePage` – common actions like click, type and wait |
| `src/main/java/utils` | Read config file and Excel file |
| `src/test/java/tests` | The test classes |
| `src/test/resources/testdata/TestData.xlsx` | Test data |
| `src/main/resources/config.properties` | Browser, website URL and wait time |
| `docs/TestCases.xlsx` | Written test cases |
