package worldofzuul;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileHandler{

    public List<String> getText(int id) {
        List<String> text = null;
        try {
            text = Files.readAllLines(Paths.get("NPC" + id + ".txt"));
            System.out.println(text.size());
        } catch (IOException e) {
            return null;
        }
        return text;
    }
}