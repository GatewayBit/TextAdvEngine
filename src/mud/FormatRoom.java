package mud;

import java.util.Scanner;

/**
 *
 * @author Cujo
 */
public class FormatRoom 
{
    int choiceAmount;
    
    private String roomInfo;
    private String formatRoomInfo;
    String choice;
    
    private String[] choices;
    
    StringBuilder sb;
    
    public FormatRoom(String room)
    {
        roomInfo = room;
        sb = new StringBuilder(roomInfo);
        choiceAmount = getChoiceCount(roomInfo);
        choices = grabChoices(roomInfo, choiceAmount);
        formatRoomInfo = formatRoomInfo(roomInfo, choices);
    }
    
    public void printRoomInfo()
    {
        System.out.println(formatRoomInfo);
    }
    
    /*
     * This asks for the users input grabbed from the valid choices
     * found in the method grabChoices.
     */
    public String askChoice()
    {
        printChoices(choices);
        Scanner sc = new Scanner(System.in);
        boolean invalidChoice = false;
        while (true)
        {
            System.out.print("Choice: ");
            choice = sc.next();
            
            for (int i = 0; i < choices.length; i++)
            {
                if (choice.equalsIgnoreCase(choices[i]))
                {
                    return choice;
                }
                else
                {
                    invalidChoice = true;
                }
            }
            if (invalidChoice)
            {
                System.out.println(choice + " is not a valid choice. Try again.");
                invalidChoice = false;
            }
        }
    }
    
    /*
     * This formats the room's text spacing by entering a new line
     * for every amount set by the variable textLength.
     * 
     * Still may have issues; watch carefully.
     */
    private String formatRoomText (String roomInfo)
    {
        int textLength = 70;
        
        sb = new StringBuilder(roomInfo);

        for (int i = 0; i < sb.length(); i++)
        {
            if (i % textLength == 0)
            {
                if (i != 0 && sb.charAt(i - 1) != ' ')
                {
                    int pos = i;
                    while (sb.charAt(pos - 1) != ' ')
                    {
                        pos--;
                    }
                    sb.insert(pos - 1, '\n');
                }
            }
        }
        return sb.toString();
    }
    
    private void printChoices(String[] choices)
    {
        System.out.println("(Your choices are...)");
        for (int i = 0; i < choices.length; i++)
        {
            System.out.println("- " + choices[i].toLowerCase());
        }
    }
    
    /*
     * This method removes any brackets [] from the text
     * along with formats the text length to enter any
     * new line breaks for a certain amount of words reached.
     */
    private String formatRoomInfo(String roomInfo, String[] choices)
    {
        sb = new StringBuilder(roomInfo);
        
        for (int i = 0; i < choices.length; i++)
        {
            sb.replace(sb.indexOf("["), sb.indexOf("]") + 1, choices[i].toLowerCase());
        }
        
        String room = formatRoomText(sb.toString());
        
        return room;
        
    }
    
    /*
     * This method will logically grab the choices
     * contained in the text that are in brackets []
     * and store them in a String array to be returned
     * and used again in another method.
     */
    private String[] grabChoices(String roomInfo, int amount)
    {
        sb = new StringBuilder(roomInfo);
        choices = new String[amount];
        
        int i = 0;
        int currentChoice = 1;
        while(i < amount)
        {
            for (int j = 0; j < currentChoice; j++)
            {
                sb.delete(0, sb.indexOf("[") + 1);
            }
            
            sb.delete(sb.indexOf("]"), sb.length());
            choices[i] = sb.toString();
            
            sb = new StringBuilder(roomInfo);
            i++;
            currentChoice++;
        }
        return choices;
    }
    
    /*
     * This method is required for the grabChoices method
     * since we need to logically find out how many choices
     * we have available to us to use.
     */
    private int getChoiceCount(String roomInfo)
    {
        int i = 0;
        sb = new StringBuilder(roomInfo);
        
        while(sb.indexOf("[") != -1)
        {
            sb.delete(0, sb.indexOf("[") + 1);
            i++;
        }
        return i;
    }
}
