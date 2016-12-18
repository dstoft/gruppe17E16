package futurama;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * This class takes care of file handling, both reading from and writing to.
 */
public class FileHandler {

    /**
     * Used by the Conversation class to read the conversation files, this
     * method reads a file, line by line, and puts each line into a list to be
     * returned.
     *
     * @param path identifies the path to which file that should be read
     * @return the list of strings
     */
    public List<String> getText(String path) {
        List<String> text = null;
        try {
            text = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            return null;
        }
        return text;
    }

    /**
     * Reads a json file and returns a freshly created object of the type T
     * passed into the parameter.
     *
     * @param fileSource where to read the file from
     * @param classType which type of object that should be written and returned
     * as
     * @return a new object of the type that was set with the parameter
     * classType
     */
    public <T> T getJSON(String fileSource, Class<T> classType) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(fileSource), classType);
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Checks whether a file exists and is not a directory ("folder").
     *
     * @param fileSource which path to check
     * @return a boolean, whether the file exists or not and is not a "folder"
     */
    public boolean doesFileExist(String fileSource) {
        File file = new File(fileSource);
        return (file.exists() && !file.isDirectory());
    }

    /**
     * Writes to a file, it simply overwrites whatever is already written to
     * file, or it creates a new file, if the file is non-existing.
     *
     * @param src which path to write to
     * @param toWrite what to write in the file
     */
    public void writeToFile(String src, String toWrite) {
        try {
            FileWriter fileWriter = new FileWriter(src);
            fileWriter.write(toWrite);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex) {

        }
    }

}
