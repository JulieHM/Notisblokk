#Core module
This directory contains the core logic classes for [notisblokk](../README.md).

The core of our app consists of many notes that the user has created, sorted into different 
categories, which then make up a notebook. These three components ``(Notebook, Category and Note)`` 
communicate with the other modules (fxui and restapi) with the help of the 
[serializer](/src/main/java/notisblokk/json/NoteSerializer.java) and 
[deserializer](/src/main/java/notisblokk/json/NoteDeserializer.java).

NoteDeserializer and NoteSerializer are helper classes that use the
Gson library to serialize and deserialize all core objects.              