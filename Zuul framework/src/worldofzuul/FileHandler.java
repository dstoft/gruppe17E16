package worldofzuul;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;

public class FileHandler{

    //Method for txt file
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
    
    
    ObjectMapper mapper = new ObjectMapper();
    //Method for JSON
    public <T> T getJSON(String fileSource, Class<T> classType) throws IOException {
        return mapper.readValue(new File(fileSource), classType);
    }
}