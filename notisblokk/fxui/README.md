#GUI module
This directory contains the GUI for [notisblokk](../README.md).

The GUI for our app shows a list of notes, as well as two textfields where you can edit their title 
and content. There are also three buttons, that lets you create new category, save and delete them.

The GUI is made with javaFX and FXML, which can be found in **fxui/src/main/java** and 
**fxui/src/main/resources** respectively.

MacOS requires additional privileges to run tests using TestFX. This can be set in 
`System Preferences -> Security & Privacy -> Accessability` and allow your IDE to control the 
computer.
