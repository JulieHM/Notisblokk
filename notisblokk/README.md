# Notisblokk
This application was made as the first part of a group project. 
We decided to create a diary-type application, in which the user can scribble notes,
save them, and open them at a later time.

![Image of the graphical user interface of the application.](docs/Notisblokk.png)

# Overview 
The file structure of this project is module based, where different types of logic are put into
separate Gradle sub-projects.

- ***core***/src/main/java - Handles the all the pure logic of the application
- ***fxui***/src/main/java - All GUI parts
- ***restapi***/src/main/java - Rest API
- ***restserver***/src/main/java - Communicates between core and fxui

### User Story
We are actively using Issues and Milestones in GitLab to track our progress and tasks that
need to be completed. This is also where we keep most of our user stories.

Week 1: The goal for this week was having a Minimum Viable Product (MVP) ready. One example of an
user story we worked on is `I want to be able to save the note I wrote to continue later`.

# Quickstart: Build and run

The project is built and run with Gradle following these steps:

1. Clone this project
2. Import it to your favourite IDE as a Gradle project
3. Build with `./gradlew clean build`
4. Run with `./gradlew run`