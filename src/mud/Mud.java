package mud;

import java.util.HashMap;

/**
 *
 * @author Cujo
 */
public class Mud 
{
    public static void main(String[] args)
    {
        game();
    }
    
    private static void game()
    {
        HashMap roomData;
        roomData = LoadRooms.formatData("testing.txt");
        
        System.out.println(roomData.keySet().toString());
        
        String roomInfo;
        String roomName = "START";
        
        roomInfo = (String) roomData.get(roomName);
        
        FormatRoom map = new FormatRoom(roomInfo);
        map.printRoomInfo();
        map.askChoice();
        
        roomName = "Straight 1";
        roomInfo = (String) roomData.get(roomName);
        
        map = new FormatRoom(roomInfo);
        map.printRoomInfo();
        map.askChoice();
    }
}
