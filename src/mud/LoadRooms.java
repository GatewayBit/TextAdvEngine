package mud;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cujo
 */
public class LoadRooms 
{
    public static HashMap formatData(String filePath)
    {
        try
        {
            HashMap rooms = new HashMap();
            
            String textFileData = loadRoomData(filePath, Charset.defaultCharset());
            compileRoomInfo(textFileData, rooms);
            return rooms;
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(LoadRooms.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /*
     * Compiles our data in an all in one neat format in a hashmap
     * and returned to the function that called it.
     */
    private static void compileRoomInfo(String roomInfo, HashMap rooms)
    {
        int totalRooms = getRoomCount(roomInfo);
        String[] roomName = new String[totalRooms];
        String[] roomText = new String[totalRooms];
        
        String name;
        String text;
        
        for (int i = 0; i < totalRooms; i++)
        {
            name = getRoomName(roomInfo, i);
            roomName[i] = name;
            
            text = getRoomText(roomInfo, i);
            roomText[i] = text;
            
            rooms.put(roomName[i], roomText[i]);
        }
    }
    
    /*
     * Logically counts the amount of "rooms" we have stored in the
     * text file to be used in our game.
     */
    private static int getRoomCount(String roomInfo)
    {
        StringBuilder sb = new StringBuilder(roomInfo);
        
        int i = 0;
        
        while(sb.indexOf("{e}") != -1)
        {
            sb.delete(0, sb.indexOf("{e}") + 1);
            i++;
        }
        return i;
    }
    
    /*
     * This function cuts out the garbage info and simply grabs
     * only the room text to be stored in a hashmap.
     */
    private static String getRoomText(String roomInfo, int roomNumber)
    {
        StringBuilder sb = new StringBuilder(roomInfo);
        String roomText;
        int i = 0;
        
        while (i < roomNumber)
        {
            sb.delete(0, sb.indexOf("{e}") + 3);
            i++;
        }
        
        sb.delete(0, sb.indexOf("}") + 1);
        roomText = sb.substring(0, sb.indexOf("{e}"));
        return roomText;
    }
    
    /*
     * Logically grabs the room's name that is contained in braces { }
     * and returns it in a formatted string value.
     */
    private static String getRoomName(String roomInfo, int roomNumber)
    {
        StringBuilder sb = new StringBuilder(roomInfo);
        
        int i = 0;
        while (i < roomNumber)
        {
            sb.delete(0, sb.indexOf("{e}") + 3);
            i++;
        }
        String name = sb.substring(sb.indexOf("{") + 1, sb.indexOf("}"));
        return name;
    }
    
    /*
     * Load room data, byte by byte and return it in 
     * a string format to be used in future functions.
     */
    private static String loadRoomData(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }
}
