package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class User
{
    // Username String
    private String userName;
    //Password String
    private String password;
    private String cardNum;
    private List<String> tickets;

    // Constructors //

    /**
     * Constructor for the User class
     * @param UserName username given to the User at construction
     * @param Pass password given to the User at construction
     */
    User(String UserName, String Pass, String CardNumber)
    {
        userName = UserName;
        password = Pass;
        cardNum = CardNumber;
    }

    /**
     * Constructor for the User Class
     *  Used to read a User file from the resources > text > accountFiles file.
     * @param f Account File to be read and assigned to the private variables.
     * @throws FileNotFoundException
     */
    User(File f) throws FileNotFoundException
    {
        readAccountFile(f);
    }

    /**
     * Creates a File in resources.text.accountFiles folder for this User instance.
     *  Writes the username and password on the first and second line of the file created.
     * @throws IOException
     */
    public void createAccountFile() throws IOException
    {
        String userFileName = "ScrumTheater/src/resources/text/accountFiles/" + userName;
        String userrn = userName + "\r\n";
        String passrn = password + "\r\n";
        String cardnumrn = cardNum + "\r\n";
        File file = new File(userFileName);

        if (file.createNewFile())
        {
            FileOutputStream fos = new FileOutputStream(userFileName, true);
            fos.write(userrn.getBytes());
            fos.write(passrn.getBytes());
            fos.write(cardnumrn.getBytes());
            fos.close();
        }
    }
    public void readAccountFile(File f) throws FileNotFoundException {
        LinkedList<String> lines = new LinkedList<>();
        Scanner sc = new Scanner(f);

        userName = sc.nextLine();
        password = sc.nextLine();
        cardNum = sc.nextLine();

        while (sc.hasNextLine())
        {
            lines.add(sc.nextLine());
        }
    }
    public String getUserName()
    {
        return userName;
    }

}