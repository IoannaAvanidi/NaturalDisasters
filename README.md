# Natural Disasters Data Analysis System (Java)

A comprehensive data analysis software developed for the **Software Development (MYY301)** course, designed to process and analyze historical natural disaster data from the IMF.

## 🎮 System Functionality

* **Data Loading:** Imports delimited text files containing disaster records and maps them to specialized domain objects.
* **Advanced Recovery:** Retrieves data based on specific combinations of Country and Disaster Type, with support for defined year ranges.
* **Statistical Analysis:** Calculates essential metrics including maximum, minimum, average, median, and total event counts.
* **Regression Analysis:** Utilizes linear regression to determine disaster tendencies (Increasing, Decreasing, or Stable) over time.
* **Reporting:** Exports findings into three distinct formats: Plain Text, Markdown, and HTML.

## 🚀 Key Features

* **Two-Tier Architecture:** Separates the system into a Front-End client (GUI/Console) and a modular Back-End server.
* **External Integration:** Employs the **Apache Commons Math** library for descriptive statistics and regression modeling.
* **Interface-Driven Design:** Ensures system stability by strictly implementing mandatory interfaces for Front-to-Back communication.
* **Unit Testing:** Includes comprehensive Junit 4 test cases for both Happy Day and Rainy Day scenarios to verify all system use cases.

## 🛠️ Technical Structure

The project is organized into several specialized packages:

* **engine**: Contains the central controller that manages the execution of all use cases.
* **recovery**: Services responsible for data retrieval from stored files.
* **analytics**: Handles the statistical processing and analytical calculations.
* **reporting**: Manages the generation and formatting of analysis reports.
* **domain**: Hosts the core domain classes and the bridge interfaces.

## 📋 Execution Instructions

Compile all `.java` files ensuring the required libraries (libs) are in your classpath, and run the main entry point:

```bash
javac -cp ".;libs/*" *.java
java -cp ".;libs/*" app.AppController
