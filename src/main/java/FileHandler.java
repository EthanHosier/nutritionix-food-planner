import java.io.*;

public abstract class FileHandler {
    private static String path = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "E_Food";
    private static File E_FoodDirectory = new File(path);
    private static File usernameAndPasswordsDirectory = new File(path +  File.separator + "Usernames_And_Passwords");
    private static String usernamesAndPasswordsFile = "Usernames_And_Passwords.txt";

    public static boolean getIfUsernamePasswordValid(String username, String password){

        String[] parts;

        System.out.println((path + File.separator + "Usernames_And_Passwords" + File.separator  + usernamesAndPasswordsFile));

        try (BufferedReader br = new BufferedReader(new FileReader(path + File.separator + "Usernames_And_Passwords" + File.separator  + usernamesAndPasswordsFile))) {
            String line = br.readLine();
            while (line != null) {

                parts = line.split(":",-1);
                if (parts[0].equals(username) && parts[1].equals(password)){
                    return true;
                }

                line = br.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }



        return false;
    } //checks for username-password combo in save file

    public static void checkForDirectory(){
        if (!usernameAndPasswordsDirectory.exists()){
            if (usernameAndPasswordsDirectory.mkdirs()){
                try ( PrintWriter writer = new PrintWriter(path + File.separator + "Usernames_And_Passwords" + File.separator  + usernamesAndPasswordsFile, "UTF-8");
                ){
                    System.out.println("Successfully created");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


            }else{
                System.out.println("error in creating");
            }
        }

    }//checks for E_Food, creating it if it doesn't exist, as well as passwords file.

    public static boolean getIfUsernameAlreadyTaken(String username){
        String[] parts;

        try (BufferedReader br = new BufferedReader(new FileReader(path + File.separator + "Usernames_And_Passwords" + File.separator  + usernamesAndPasswordsFile))) {
            String line = br.readLine();
            while (line != null) {
                parts = line.split(":",-1);
                if (parts[0].toLowerCase().equals(username.toLowerCase())){
                    return true;
                }
                line = br.readLine();
            }
        }
        catch (IOException e) {
            //e.printStackTrace();
            //create file if doesnt exist
            try {
                PrintWriter writer = new PrintWriter(path + File.separator + "Usernames_And_Passwords" + File.separator  + usernamesAndPasswordsFile, "UTF-8");
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (UnsupportedEncodingException unsupportedEncodingException) {
                unsupportedEncodingException.printStackTrace();
            }
        }

        return false;
    } //returns bool of whether username already present within save file

    public static void addUsernamePassword(String username, String password){

        try(FileWriter fw = new FileWriter(path + File.separator + "Usernames_And_Passwords" + File.separator  + usernamesAndPasswordsFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(username + ":" + password);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    } //adds username-password combination to save file

    public static void saveUser(User user){
        File userDirectory = new File(E_FoodDirectory + File.separator + user.getName() + " save");

        //create directory if doesnt already exist
        if (!userDirectory.exists()) {
            userDirectory.mkdirs();
        }

        String file = userDirectory + File.separator+  user.getName() + "_save_file.txt";

        //saves obj to file location
        File f = new File(file);

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
            out.writeObject(user);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    } //updates user's save file

    public static User loadUser(String username){
        String file = E_FoodDirectory + File.separator + username + " save" + File.separator + username + "_save_file.txt";

        File f = new File(file);
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(f));
            User user = (User) inputStream.readObject();
            inputStream.close();
            return user;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    } //returns the user object associated with the given username

}
