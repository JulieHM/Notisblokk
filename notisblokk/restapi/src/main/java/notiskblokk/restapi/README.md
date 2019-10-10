# REST API

This folder contains all the logic for the REST API, which includes POST, GET, PUT and DELETE 
mappings (all logic required by the UI), custom error messaging, exception handlers.

**NoteController**: A @RestController that stores methods used by the API to add and receive Notes.

**NoteService**: A @Repository which stores the UI methods, which talks to the UI and Controller.

**NotesSpringBootApp**: Initializes the REST API.

**RestNoteNotFoundException**: Custom exception that throws when a note at a given index is not 
found.

**CustomErrorResponse**: Formats the exception text when a give note is not found. 

**CustomGlobalExceptionHandler**: Exception handler which creates the custom error response, a 
HTTP-status code, and the exception time.
