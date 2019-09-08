# Notisblokk
This application was made as the first part of a group project. 
We decided to create a diary-type application, in which the user can scribble notes,
save them, and open them at a later time.

![Image of the graphical user interface of the application.](docs/Notisblokk.png)

# Overview
The file structure is based on the MVC architectural pattern. We are keeping each layer in their
own package and resources separate from Java source code. 

- **src/main/java/controller** - JSON serialization and deserialization source code.
- **src/main/java/model** - Domain classes with data and logic source code.
- **src/main/java/view** - Graphical User Interface source code.
- **src/main/resources** - Other files such as configs, .css and .fxml.
- **src/test/java** - Test files for our source code.

# Quickstart: Build and run

The project is built and run with Gradle following these steps:

1. Clone this project
2. Import the project as a Gradle Project to your favorite IDE
3. Build with `./gradlew clean build`
4. Run with `./gradlew run`