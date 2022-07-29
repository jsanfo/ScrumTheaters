package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class User
{
    // Username String
    private String userName;
    //Password String
    private String password;

    // Constructors //

    /**
     * Constructor for the User class
     * @param UserName username given to the User at construction
     * @param Pass password given to the User at construction
     */
    User(String UserName, String Pass)
    {
        userName = UserName;
        password = Pass;
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
        File file = new File(userFileName);

        if (file.createNewFile())
        {
            FileOutputStream fos = new FileOutputStream(userFileName, true);
            fos.write(userrn.getBytes());
            fos.write(passrn.getBytes());
            fos.close();
        }
    }

}