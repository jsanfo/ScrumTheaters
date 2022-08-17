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
    private String userName;
    private String password;
    //Card Number String
    private String cardNum;
    //Current tickets (Tickets for movies whose showtime is now or in the future)
    private List<String> currentTickets = new ArrayList<>();
    //Past Tickets (Tickets for movies whose showtime has already passed)
    private List<String> pastTickets = new ArrayList<>();
    //List of tickets currently in User's cart.
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

    /**
     * @return String to the path of the User's account file.
     */
    public String getFilePath()
    {
        return "ScrumTheater/src/resources/text/accountFiles/" + userName;
    }

    /**
     * Overwrites the old line found in the User's account file.
     * @param oldLine Old line to be found
     * @param newLine New line to replace the old
     * @throws IOException
     */
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

    /**
     * Gives the User's account file a new name
     * @param newName new name for the file
     */
    public void renameFile(String newName)
    {
        Path oldPath = Paths.get(getFilePath());
        Path newPath = Paths.get("ScrumTheater/src/resources/text/accountFiles/" + newName);

        File oldf = new File(oldPath.toUri());
        File newf = new File(newPath.toUri());

        oldf.renameTo(newf);
    }

    /**
     * Reads through the account file and parses the username, password, and card number out.
     * @param f File to read through
     * @throws FileNotFoundException
     */
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

    /**
     * @return Username of the User
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * @return Card Number of the User
     */
    public String getCardNum()
    {
        return cardNum;
    }
    public List<String> getCart()
    {
        List<String> tix = cart;
        List<Integer> amts = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> times = new ArrayList<>();

        for (int i = 0; i < tix.size(); i++)
        {
            String strs[] = tix.get(i).split(",");

            amts.add(Integer.parseInt(strs[0]));
            names.add(strs[1]);
            times.add(strs[2]);
        }

        for (int i = 0; i < names.size(); i++)
        {
            for (int j = 0; j < names.size(); j++)
            {
                if (i != j && names.get(i).equals(names.get(j)) && times.get(i).equals(times.get(j)))
                {
                    int newAmt = amts.get(i) + amts.get(j);
                    cart.remove(amts.get(i) + "," + names.get(i) + "," + times.get(i));
                    cart.remove(amts.get(j) + "," + names.get(j) + "," + times.get(j));
                    cart.remove(newAmt + "," + names.get(i) + "," + times.get(i));
                    cart.add(newAmt + "," + names.get(i) + "," + times.get(i));
                }
            }
        }

        return cart;
    }
    public List<String> getCartDisplayList()
    {
        List<String> displayList = new ArrayList<>();

        for (int i = 0; i < cart.size(); i++)
        {
            String strs[] = cart.get(i).split(",");

            displayList.add(strs[0] + " For: " + strs[1] + " at " + strs[2]);
        }

        return displayList;
    }

    /**
     * Overwrites the username line in the file, renames the file to the new name, changes the username variable to new.
     * @param newUserName New username to set to
     * @throws IOException
     */
    public void setUserName(String newUserName) throws IOException
    {
        overwriteLine(userName, newUserName);
        renameFile(newUserName);
        userName = newUserName;
    }

    /**
     * Overwrites password line, sets password variable to new password.
     * @param newPassword New password to set to
     * @throws IOException
     */
    public void setPassword(String newPassword) throws IOException
    {
        overwriteLine(password, newPassword);
        password = newPassword;
    }

    /**
     * Overwrites Card Number line, sets cardNum variable to new one.
     * @param newCardNum New card number to set to
     * @throws IOException
     */
    public void setCardNum(String newCardNum) throws IOException
    {
        overwriteLine(cardNum, newCardNum);
        cardNum = newCardNum;
    }

    /**
     * Adds items in list passed in to cart List
     * @param selections List of Strings passed in
     */
    public void addToCart(List<String> selections){
        cart.addAll(selections);
    }

}