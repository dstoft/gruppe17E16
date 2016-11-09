package worldofzuul;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;

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
    
    

    //Method for JSON
    public <T> T getJSON(String fileSource, Class<T> classType) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(fileSource), classType);
        } catch(IOException e) {
            return null;
        }
    }
    
    public boolean doesFileExist(String fileSource) {
        File file = new File(fileSource);
        // .exists() will return true, if the fileSource is a directory (a folder), 
        //but we want to check whether a file exists, so we have to make sure that the fileSource is not a directory
        return (file.exists() && !file.isDirectory());
    }

}