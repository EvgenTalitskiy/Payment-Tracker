# Payment-Tracker
Task for Java Experts

Developer: Talitskiy Evgeniy Maksimovich (evgen.talitskiy@gmail.com)

The task was prepared by: GEMINI teams

### Documentation

Description of the task you can find [here][task descr].


### Requirements

This project uses Maven to build and manage dependencies.

To use the currency converter you need an internet connection.

Make sure the following dependencies are installed:

*   [Java][java]
*   [Maven][maven]

### Launch program

To start the program you need to use the command line to go to the program directory and execute the command:

```
java -cp bin Launcher
```

You can specify a file name as a parameter. The format of the file should consist of one or more lines with the amount of the currency code, as in the example in the task.

For example, the Launch.bat file is attached to the project. You can experience it on startup on Windows.

### Using

After starting, you can enter any three-letter currency code and, after a space, the amount of this transaction.

Example:
```
EUR 4586.22
```

If the entered line does not comply with the format, you will receive an error message:
```
Неверный формат строки!
```

Once a minute, a conclusion is displayed indicating the net amount of each currency. For the currency for which information about the current exchange rate is available, at the end of the line, the converted number is written according to the USD exchange rate.

Information on the current exchange rate was obtained from the portal: [CBR.ru][cbr].

### Bugs

The error could not be fixed, due to which the information entered by the user during the screen update was erased, but the input buffer was not cleared.

[task descr]: https://bscideas-my.sharepoint.com/:w:/g/personal/astafyev_bscpraha_cz/EXTNsDv-5z5AhL3FRXnQn2YBMNrTzjwFtGej6H1yPtstZw?rtime=kGeTIvQ_10g
[java]: https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[maven]: https://maven.apache.org/
[cbr]: https://www.cbr.ru/currency_base/daily/
