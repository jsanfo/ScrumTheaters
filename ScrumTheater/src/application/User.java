package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class User
{
    // Username String
    private String userName;
    //Password String
    private String password;
    private String cardNum;
    private List<String> tickets = new ArrayList<>();
    private List<String> cart = new ArrayList<>();

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
        String userFileName = getFilePath();
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
    public String getFilePath()
    {
        return "ScrumTheater/src/resources/text/accountFiles/" + userName;
    }
    public void overwriteLine(String oldLine, String newLine) throws IOException {
        Path path = Paths.get(getFilePath());
        List<String> lines = new ArrayList<>(Files.readAllLines(path));

        for (int i = 0; i < lines.size(); i++)
        {
            if (Objects.equals(lines.get(i), oldLine))
            {
                lines.set(i, newLine);
                break;
            }
        }

        Files.write(path, lines);
    }
    public void renameFile(String newName)
    {
        Path oldPath = Paths.get(getFilePath());
        Path newPath = Paths.get("ScrumTheater/src/resources/text/accountFiles/" + newName);

        File oldf = new File(oldPath.toUri());
        File newf = new File(newPath.toUri());

        oldf.renameTo(newf);
    }

    private void readAccountFile(File f) throws FileNotFoundException {
        LinkedList<String> lines = new LinkedList<>();
        Scanner sc = new Scanner(f);

        userName = sc.nextLine();
        password = sc.nextLine();
        cardNum = sc.nextLine();

        while (sc.hasNextLine())
        {
            lines.add(sc.nextLine());
        }
        sc.close();
    }
    public String getUserName()
    {
        return userName;
    }
    public String getCardNum()
    {
        return cardNum;
    }

    public void setUserName(String newUserName) throws IOException
    {
        overwriteLine(userName, newUserName);
        renameFile(newUserName);
        userName = newUserName;
    }
    public void setPassword(String newPassword) throws IOException
    {
        overwriteLine(password, newPassword);
        password = newPassword;
    }
    public void setCardNum(String newCardNum) throws IOException
    {
        overwriteLine(cardNum, newCardNum);
        cardNum = newCardNum;
    }

    public void addToCart(List<String> selections){
        cart.addAll(selections);
    }

}