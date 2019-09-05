package notisblokk.controller;

import com.google.gson.Gson;
import notisblokk.model.Note;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class NoteSerializer {

    //TODO: Find MacOS solution for path (file-saving)
    public static final String LOCAL_PATH = System.getenv("LOCALAPPDATA");
    public static final String COMPLETE_PATH = System.getenv("LOCALAPPDATA") + "\\projectNotes\\notes.json";

    private Gson gsonSerializer;

    public NoteSerializer() {
        gsonSerializer = new Gson();
    }

    /**
     * Takes in a list of notes and serializes them to a json-formatted string
     * and saves them locally.
     * @param noteList list of all notes
     * @return true if the action was completed
     */
    public boolean serializeNotes(List<Note> noteList, String path) throws IOException {
        //TODO: Should probably make it possible to append notes to the json file
        // instead of rewriting the whole file every time
        String json = gsonSerializer.toJson(noteList);
        FileWriter writer;
        if (Files.exists(Paths.get(path))) {
            writer = new FileWriter(path);
            writer.write(json);
        } else {
            File jsonFile = new File(COMPLETE_PATH);
            if (!jsonFile.getParentFile().mkdirs()) {
                return false;
            }
            if (!jsonFile.createNewFile()) {
                return false;
            }
            writer = new FileWriter(path);
            writer.write(json);
        }

        writer.close();
        return true;
    }

}
