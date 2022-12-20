import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

//gui class
public class HomeFrame extends JFrame {

    private int HEIGHT = 700;
    private int WIDTH = 800;

    private MealPlanStack usersMealPlanStack;
    private MealPlan mealPlanOn;

    private JButton leftArrowButton;
    private JButton rightArrowButton;
    private JButton selectButton;


    public HomeFrame(User user){

        //get centre screen position:
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - WIDTH) / 2);
        int y = (int) ((dimension.getHeight() - HEIGHT) / 2);

        this.setBounds(x,y,WIDTH,HEIGHT);
        this.setLayout(null);
        this.setResizable(false);

        //logo
        this.add(new JLabel(getLogo("homeIcon.png")){{
            this.setBounds((HomeFrame.this.WIDTH - 250) / 2, (HomeFrame.this.HEIGHT - 250) / 2 - 175, 250,250);

        }});

        //profile button
        this.add(new JButton("Profile"){{
            this.setBackground(user.getProfileColour());
            this.setFocusable(false);
            this.setFont(new Font("Serif Sans", Font.BOLD, 14));
            this.setBounds(HomeFrame.this.WIDTH - 150, 50, 100,100);

            this.addActionListener(e->{
                new ProfileFrame(user);
                HomeFrame.this.dispose();
            });
        }});

        //username label top left
        this.add(new JLabel(user.getName()){{
            this.setBounds(30, 0, 500,50);
            this.setFont(new Font("Serif Sans",Font.BOLD,20));
            this.setForeground(user.getProfileColour());
        }});

        usersMealPlanStack = user.getMealPlanStack();

        //Set default meal plan on
        if (usersMealPlanStack.getTotalSize() !=0) {
            mealPlanOn = usersMealPlanStack.peak();



            //meal plan label
            JLabel mealPlanLabel = new JLabel() {{
                this.setText(usersMealPlanStack.peak().getName());
                this.setFont(new Font("Serif Sans", Font.BOLD, 20));
                this.setBounds(HomeFrame.this.WIDTH / 2 - 160, HomeFrame.this.HEIGHT / 2 + 75, 200, 50);

            }};
            this.add(mealPlanLabel);

            //left arrow button
            leftArrowButton = new JButton("<") {{
                this.setFocusable(false);
                this.setBackground(new Color(0xA027EF));
                this.setFont(new Font("Serif Sans", Font.BOLD, 20));
                this.setBounds(HomeFrame.this.WIDTH / 2 - 250, HomeFrame.this.HEIGHT / 2 + 75, 50, 50);

                this.addActionListener(e -> {
                    usersMealPlanStack.unPop();
                    mealPlanOn = usersMealPlanStack.peak();
                    mealPlanLabel.setText(mealPlanOn.getName());
                    setSelectionButtons();
                });
                //ADD ACTION LISTENER TO SELECT PREVIOUS
                //Priority queue, greyed out if no previous
            }};
            this.add(leftArrowButton);


            //left arrow button
            rightArrowButton = new JButton(">") {{
                this.setFocusable(false);
                this.setBackground(new Color(0xA027EF));
                this.setFont(new Font("Serif Sans", Font.BOLD, 20));
                this.setBounds(HomeFrame.this.WIDTH / 2 + 175, HomeFrame.this.HEIGHT / 2 + 75, 50, 50);

                this.addActionListener(e -> {
                    usersMealPlanStack.pop();
                    mealPlanOn = usersMealPlanStack.peak();
                    mealPlanLabel.setText(mealPlanOn.getName());
                    setSelectionButtons();
                });

                //ADD ACTION LISTENER TO SELECT NEXT
                //Priority queue, greyed out if no next
            }};

            this.add(rightArrowButton);
            setSelectionButtons();

            //select button
            selectButton = new JButton("Select") {{
                this.setFocusable(false);
                this.setBackground(new Color(0xCBCBCB));
                this.setFont(new Font("Serif Sans", Font.BOLD, 14));
                this.setBounds(HomeFrame.this.WIDTH / 2 + 250, HomeFrame.this.HEIGHT / 2 + 75, 100, 50);

                this.addActionListener(e -> {
                    new MealPlanFrame(user, mealPlanOn);
                    HomeFrame.this.dispose();
                });
            }};

            this.add(selectButton);

            //delete button
            this.add(new JButton("Delete") {{
                this.setFocusable(false);
                this.setBackground(new Color(0xCBCBCB));
                this.setFont(new Font("Serif Sans", Font.BOLD, 14));
                this.setBounds(20, HomeFrame.this.HEIGHT / 2 + 75, 100, 50);

                this.addActionListener(e->{
                    //delete meal plan
                    usersMealPlanStack.deleteMealPlan();

                    if (usersMealPlanStack.getTotalSize() > 0){
                        mealPlanOn = usersMealPlanStack.peak();
                        mealPlanLabel.setText(mealPlanOn.getName());
                        setSelectionButtons();
                    }else{
                        mealPlanLabel.setText("");
                    }

                    if (usersMealPlanStack.getTotalSize() == 0){
                        this.setEnabled(false);
                        setSelectionButtons();
                    }
                });

            }});

        }
        this.add(new JButton("+"){{
            this.setFocusable(false);
            this.setBackground(new Color(0xCBCBCB));
            this.setFont(new Font("Serif Sans", Font.BOLD, 60));
            this.setBounds(HomeFrame.this.WIDTH - 132, HomeFrame.this.HEIGHT - 175, 80,80);

            this.addActionListener(e->{{
                boolean valid = false;
                String name = null;

                while (!valid) {
                    name = (String) JOptionPane.showInputDialog(
                            HomeFrame.this,
                            "Please Enter a Unique Meal Plan Name:",
                            "Set Meal Plan Name",
                            JOptionPane.PLAIN_MESSAGE
                    );

                    //if exited, returns
                    if (name == null){
                        return;
                    }

                    if (usersMealPlanStack.contains(name) || !checkForNonSpace(name)){
                        JOptionPane.showMessageDialog(HomeFrame.this,
                                "Please Enter a Unique, Valid Name",
                                "Name Error",
                                JOptionPane.ERROR_MESSAGE);
                    }else{
                        valid = true;
                    }
                }

                MealPlan newMealPlan = new MealPlan(name);
                usersMealPlanStack.put(newMealPlan);
                new MealPlanFrame(user,newMealPlan);
                HomeFrame.this.dispose();
            }});



        }});

        /* TODO: 26/08/2021  : I've now made the stack class and user now has one, need to make it so if press 'create new stack', puts new one on top (method in user already), and add so scrolls through which one selected when press arrows, peaking each time (peak, then pop), then can select that one as its has been returned already via peak()
           TODO: 26/08/2021  : (read all of that bit first) ...  ACTUALLY i think if you pop before peak, will get to null node before realising ~~ so fix this (e.g so stops if theres only one left on the stack) EXCEPT DONT DO THIS BUT THINK OF OTHER WAY
* */       // TODO: 27/08/2021 Actually wouldnt doing a getSize() method work, and only doing it if size > 0 or 1 or sumin? 

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private ImageIcon getLogo(String fileName){
        //load logo
        java.net.URL logoURL = Main.class.getClassLoader().getResource(fileName);
        Image image = null;
        try {
            image = ImageIO.read(logoURL);

        }catch (Exception e){
            System.out.println("Error loading image");
        }
        ImageIcon icon = new ImageIcon(image);
        return icon;
    }

    //enables or disables arrow buttons and select button accordingly (if can move left or right or select)
    private void setSelectionButtons(){
        //left arrow button
        if (usersMealPlanStack.getLeftStackSize() < 1){
            leftArrowButton.setEnabled(false);
            leftArrowButton.setBackground(new Color(0xCBCBCB));
        }else{
            leftArrowButton.setEnabled(true);
            leftArrowButton.setBackground(new Color(0xA027EF));
        }

        //right arrow button (meal being viewed is meal on top of stack, thus the extra number)
        if (usersMealPlanStack.getRightStackSize() < 2){
            rightArrowButton.setEnabled(false);
            rightArrowButton.setBackground(new Color(0xCBCBCB));
        }else{
            rightArrowButton.setEnabled(true);
            rightArrowButton.setBackground(new Color(0xA027EF));

        }

        if (usersMealPlanStack.getTotalSize() == 0){
            selectButton.setEnabled(false);
        }
    }


    //method to check if string contains any letter other than a space
    private boolean checkForNonSpace(String s){
        for (int i = 0; i < s.length(); i ++){
            if (s.charAt(i) != ' ' && s.charAt(i) != '\n'){
                return true;
            }
        }
        return false;
    }
}
