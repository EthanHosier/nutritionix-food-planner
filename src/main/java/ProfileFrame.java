import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

//GUI class
public class ProfileFrame extends JFrame {

    int WIDTH = 700;
    int HEIGHT = 800;

    private JButton colourButton;

    private JComboBox agesComboBox, weightsComboBox, heightsComboBox, gendersComboBox, exerciseLevelComboBox;

    private ArrayList<JLabel> jLabels;

    private Color iconColour = Color.gray;

    boolean isPregnant;

    //constructor for when program is starting up
    public ProfileFrame(String username, String password){
        String[] labels = {"Age:","Height (cm):","Weight (kg):","Activeness:","Gender:",  "Pregnant?"};
        jLabels = new ArrayList<>();
        //get centre screen position:
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - WIDTH) / 2);
        int y = (int) ((dimension.getHeight() - HEIGHT) / 2);

        this.setBackground(new Color(0xE8E8E8));
        this.setLayout(null);
        this.setBounds(x,y,WIDTH,HEIGHT);
        this.setResizable(false);

        colourButton = new JButton("+"){{
            this.setFont(new Font("Serif Sans", Font.BOLD, 30));
            this.setBounds(ProfileFrame.this.WIDTH / 2 - 100, 100, 200,200);
            this.setFocusable(false);
            this.setBackground(Color.gray);
            this.setBorder(null);
        }};
        colourButton.addActionListener(e -> {
            Color color = JColorChooser.showDialog(null,"Choose Icon Colour", Color.gray);
            colourButton.setBackground(color);
            iconColour = color;
        });

        this.add(colourButton);

        //temp variables for positioning of components
        int highesty = 350;
        int spacingy = 60;
        int xPosComboBox = (WIDTH/2) + 50;


        for (int i = 0; i < labels.length; i ++){
            int finalI = i;
            JLabel label = new JLabel(){{
                this.setText(labels[finalI]);
                this.setFont(new Font("Serif Sans",Font.BOLD,20));
                this.setBounds(xPosComboBox - 200, highesty + spacingy * finalI,120,50);

            }};
            jLabels.add(label);
        }

        //add all labels apart from pregnant? label
        for (int i = 0; i < jLabels.size() - 1; i ++){
            this.add(jLabels.get(i));
        }

        //format agesComboBox
        int[] validAges = IntStream.rangeClosed(5,120).toArray();
        agesComboBox = new JComboBox(Arrays.toString(validAges).substring(1,Arrays.toString(validAges).length() - 1).split(", ")){{
            this.setBounds(xPosComboBox, highesty,100,50);
            this.setFont(new Font("Serif Sans", Font.BOLD,20));
        }};
        this.add(agesComboBox);

        //format heightsComboBox (cm)
        int[] validHeights = IntStream.rangeClosed(91,244).toArray();
        heightsComboBox = new JComboBox(Arrays.toString(validHeights).substring(1,Arrays.toString(validHeights).length() - 1).split(", ")){{
            this.setBounds(xPosComboBox, highesty + spacingy,100,50);
            this.setFont(new Font("Serif Sans", Font.BOLD,20));
        }};
        this.add(heightsComboBox);

        //format weightsComboBox (kg)
        int[] validWeights = IntStream.rangeClosed(30,200).toArray();
        weightsComboBox = new JComboBox(Arrays.toString(validWeights).substring(1,Arrays.toString(validWeights).length() - 1).split(", ")){{
            this.setBounds(xPosComboBox, highesty + spacingy * 2,100,50);
            this.setFont(new Font("Serif Sans", Font.BOLD,20));
        }};
        this.add(weightsComboBox);

        //format exerciseLevelComboBox (kg)
        String[] exerciseLevels = {"Sedentary","Lightly Active","Active"};

        exerciseLevelComboBox = new JComboBox(Arrays.toString(exerciseLevels).substring(1,Arrays.toString(exerciseLevels).length() - 1).split(", ")){{
            this.setBounds(xPosComboBox, highesty + spacingy * 3,100,50);
            this.setFont(new Font("Serif Sans", Font.BOLD,10));
        }};

        this.add(exerciseLevelComboBox);


        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        //format pregnantTickBox and genderComboBox
        JCheckBox pregnantCheckBox = new JCheckBox(){{
            this.setBounds(400, 650 + 10,35,35);
            this.setOpaque(false);
        }};
        pregnantCheckBox.addActionListener(e -> {
            if (pregnantCheckBox.isSelected()){
                isPregnant = true;
            }else{
                isPregnant = false;
            }
        });

        String[] genders = {"Male","Female"};
        gendersComboBox = new JComboBox(genders){{
            this.setBounds(400, 590,100,50);
            this.setFont(new Font("Serif Sans", Font.BOLD,20));
        }};


        gendersComboBox.addActionListener(e -> {
            if (gendersComboBox.getSelectedItem() == "Male"){
                ProfileFrame.this.remove(pregnantCheckBox);
                ProfileFrame.this.remove(jLabels.get(jLabels.size() - 1));
            }else if (gendersComboBox.getSelectedItem() == "Female"){
                ProfileFrame.this.add(pregnantCheckBox);
                ProfileFrame.this.add(jLabels.get(jLabels.size() - 1));
            }
            ProfileFrame.this.repaint();
        });
        this.add(gendersComboBox);


        this.add(new JButton("Enter"){{
            this.setFont(new Font("Serif Sans", Font.BOLD, 15));
            this.setBounds(ProfileFrame.this.WIDTH - 125,ProfileFrame.this.HEIGHT - 125, 75,50);
            this.setFocusable(false);
            this.addActionListener(e -> {
                System.out.println("Clicked");
                User user;
                if (gendersComboBox.getSelectedItem() == "Male"){
                    user = new User(username,Integer.parseInt(agesComboBox.getSelectedItem().toString()),Integer.parseInt(heightsComboBox.getSelectedItem().toString()),Integer.parseInt(weightsComboBox.getSelectedItem().toString()), exerciseLevelComboBox.getSelectedItem().toString(),iconColour);
                }else{
                    user = new FemaleUser(username,Integer.parseInt(agesComboBox.getSelectedItem().toString()),Integer.parseInt(heightsComboBox.getSelectedItem().toString()),Integer.parseInt(weightsComboBox.getSelectedItem().toString()), exerciseLevelComboBox.getSelectedItem().toString(),iconColour,isPregnant);
                }
                new HomeFrame(user);
                FileHandler.addUsernamePassword(username,password);
                FileHandler.saveUser(user);
                ProfileFrame.this.dispose();
            });
        }});



    }

    //already have a profile
    public ProfileFrame(User user){

        //set default icon colour (to fix bug where being switched back to grey if not colour not selected)
        iconColour = user.getProfileColour();

        String[] labels = {"Age:","Height (cm):","Weight (kg):","Activeness:",  "Pregnant?"};

        //get class (whether male or female)
        String userClass;
        Class<?> enclosingClass = user.getClass().getEnclosingClass();
        if (enclosingClass != null) {
            userClass = enclosingClass.getName();
        } else {
            userClass = user.getClass().getName();
        }

        jLabels = new ArrayList<>();
        //get centre screen position:
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - WIDTH) / 2);
        int y = (int) ((dimension.getHeight() - HEIGHT) / 2);

        this.setBackground(new Color(0xE8E8E8));
        this.setLayout(null);
        this.setBounds(x,y,WIDTH,HEIGHT);

        colourButton = new JButton("+"){{
            this.setFont(new Font("Serif Sans", Font.BOLD, 30));
            this.setBounds(ProfileFrame.this.WIDTH / 2 - 100, 100, 200,200);
            this.setFocusable(false);
            this.setBackground(iconColour);
            this.setBorder(null);
        }};
        colourButton.addActionListener(e -> {
            Color color = JColorChooser.showDialog(null,"Choose Icon Colour", iconColour);
            colourButton.setBackground(color);
            iconColour = color;
        });

        this.add(colourButton);

        //temp variables for positioning of components
        int highesty = 350;
        int spacingy = 60;
        int xPosComboBox = (WIDTH/2) + 50;


        for (int i = 0; i < labels.length; i ++){
            int finalI = i;
            JLabel label = new JLabel(){{
                this.setText(labels[finalI]);
                this.setFont(new Font("Serif Sans",Font.BOLD,20));
                this.setBounds(xPosComboBox - 200, highesty + spacingy * finalI,120,50);

            }};
            jLabels.add(label);
        }

        //add all labels apart from pregnant? label
        for (int i = 0; i < jLabels.size() - 1; i ++){
            this.add(jLabels.get(i));
        }

        //if woman
        if (!userClass.equals("User")) {

            //set default isPregnant
            FemaleUser tempUser = (FemaleUser) user;
            isPregnant = tempUser.isPregnant();

            this.add(jLabels.get(jLabels.size() - 1));
            JCheckBox pregnantCheckBox = new JCheckBox(){{
                this.setSelected(tempUser.isPregnant());
                this.setBounds(xPosComboBox,605,20,20);
            }};
            pregnantCheckBox.addActionListener(e -> {
                isPregnant = pregnantCheckBox.isSelected();
            });
            this.add(pregnantCheckBox);

        }
        //format agesComboBox
        int[] validAges = IntStream.rangeClosed(5,120).toArray();
        agesComboBox = new JComboBox(Arrays.toString(validAges).substring(1,Arrays.toString(validAges).length() - 1).split(", ")){{
            this.setBounds(xPosComboBox, highesty,100,50);
            this.setFont(new Font("Serif Sans", Font.BOLD,20));
            this.setSelectedIndex(user.getAge() - 5);
        }};
        this.add(agesComboBox);

        //format heightsComboBox (cm)
        int[] validHeights = IntStream.rangeClosed(91,244).toArray();
        heightsComboBox = new JComboBox(Arrays.toString(validHeights).substring(1,Arrays.toString(validHeights).length() - 1).split(", ")){{
            this.setBounds(xPosComboBox, highesty + spacingy,100,50);
            this.setFont(new Font("Serif Sans", Font.BOLD,20));
            this.setSelectedIndex(user.getHeight() - 91);
        }};
        this.add(heightsComboBox);

        //format weightsComboBox (kg)
        int[] validWeights = IntStream.rangeClosed(30,200).toArray();
        weightsComboBox = new JComboBox(Arrays.toString(validWeights).substring(1,Arrays.toString(validWeights).length() - 1).split(", ")){{
            this.setBounds(xPosComboBox, highesty + spacingy * 2,100,50);
            this.setFont(new Font("Serif Sans", Font.BOLD,20));
            this.setSelectedIndex(user.getWeight() - 30);
        }};
        this.add(weightsComboBox);

        //format exerciseLevelComboBox (kg)
        String[] exerciseLevels = {"Sedentary","Lightly Active","Active"};

        exerciseLevelComboBox = new JComboBox(Arrays.toString(exerciseLevels).substring(1,Arrays.toString(exerciseLevels).length() - 1).split(", ")){{
            this.setBounds(xPosComboBox, highesty + spacingy * 3,100,50);
            this.setFont(new Font("Serif Sans", Font.BOLD,10));

            String excerciseLevel = user.getExerciseLevel();
            if (excerciseLevel.equals("Sedentary")){
                this.setSelectedIndex(0);
            }else if(excerciseLevel.equals("Lightly Active")) {
                this.setSelectedIndex(1);
            } else if(excerciseLevel.equals("Active")) {
                this.setSelectedIndex(2);
            }
        }};

        this.add(exerciseLevelComboBox);


        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);



        this.add(new JButton("Save"){{
            this.setFont(new Font("Serif Sans", Font.BOLD, 15));
            this.setBounds(ProfileFrame.this.WIDTH - 125,ProfileFrame.this.HEIGHT - 125, 75,50);
            this.setFocusable(false);
            this.addActionListener(e -> {
                System.out.println("Clicked");



                if (userClass.equals("User")){
                    user.updateProfile(Integer.parseInt(agesComboBox.getSelectedItem().toString()),Integer.parseInt(heightsComboBox.getSelectedItem().toString()),Integer.parseInt(weightsComboBox.getSelectedItem().toString()), exerciseLevelComboBox.getSelectedItem().toString(),iconColour);

                }else{
                    FemaleUser femaleUser = (FemaleUser)user;
                    femaleUser.updateProfile(Integer.parseInt(agesComboBox.getSelectedItem().toString()),Integer.parseInt(heightsComboBox.getSelectedItem().toString()),Integer.parseInt(weightsComboBox.getSelectedItem().toString()), exerciseLevelComboBox.getSelectedItem().toString(),iconColour,isPregnant);
                }
                new HomeFrame(user);
                //save users settings
                FileHandler.saveUser(user);
                ProfileFrame.this.dispose();
            });
        }});

        //if female
        if (!userClass.equals("User")){

        }

    }



}


