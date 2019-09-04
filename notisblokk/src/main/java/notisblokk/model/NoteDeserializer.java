package notisblokk.model;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteDeserializer {

    private Gson gsonDeserializer;

    public NoteDeserializer() {
        gsonDeserializer = new Gson();
    }

    public List<Note> DeserializeNotes(String path) throws IOException {
        if (Files.exists(Paths.get(path))) {
            String jsonFromFile = Files.readString(Paths.get(path));
            // Could probably improve the way this is done
            Note[] noteArray = gsonDeserializer.fromJson(jsonFromFile, Note[].class);
            List<Note> noteList = new ArrayList<>();
            Collections.addAll(noteList, noteArray);
            return noteList;
        }

        return new ArrayList<>();
    }

}
