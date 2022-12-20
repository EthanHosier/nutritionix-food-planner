import javax.swing.*;
import java.awt.*;


public class CreateAccountFrame extends JFrame{

    int WIDTH = 400;
    int HEIGHT = 350;

    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JLabel takenUsernameMessage;
    private JLabel invalidUsernamePasswordMessage;
    private JButton createAccountButton;

    public CreateAccountFrame(){
        //get centre screen position:
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - WIDTH) / 2);
        int y = (int) ((dimension.getHeight() - HEIGHT) / 2);
        this.setBounds(x,y,WIDTH,HEIGHT);
        this.setLayout(null);
        this.setResizable(false);


        this.add(new JLabel("Create Account"){{
            this.setBounds((CreateAccountFrame.this.WIDTH - 140 ) / 2, 25, 150,50);
            this.setFont(new Font("Sans-serif", Font.BOLD, 15));
        }});


        //go back to sign in button
        this.add(new JButton("Log In"){{
            this.setBounds(5,5,80,20);
            this.setFocusable(false);
            this.setFont(new Font("Sans-serif", Font.BOLD,10));
            this.addActionListener(e -> {
                new LogInFrame();
                CreateAccountFrame.this.dispose();
            });
        }});

        usernameTextField = new JTextField(){{
            this.setBounds(140,100,200,30);
        }};
        this.add(usernameTextField);

        this.add(new JLabel("Username"){{
            this.setBounds(20,100,100,30);
            this.setFont(new Font("Sans-serif", Font.BOLD,15));
        }});

        passwordTextField = new JTextField(){{
            this.setBounds(140,140,200,30);
        }};
        this.add(passwordTextField);

        this.add(new JLabel("Password"){{
            this.setBounds(20,140,100,30);
            this.setFont(new Font("Sans-serif", Font.BOLD,15));
        }});


        takenUsernameMessage = new JLabel("Please Enter a Unique Username"){{
            this.setBounds((CreateAccountFrame.this.WIDTH - 140) / 2,180,250,20);
            this.setForeground(Color.red);
        }};

        invalidUsernamePasswordMessage = new JLabel("Please Enter a Valid Username and Password"){{
            this.setBounds((CreateAccountFrame.this.WIDTH - 170) / 2,180,300,20);
            this.setForeground(Color.red);
        }};


        createAccountButton = new JButton("Create Account"){{
                this.setBounds(CreateAccountFrame.this.WIDTH - 200, CreateAccountFrame.this.HEIGHT - 120, 150, 30);
                this.setFocusable(false);
        }};

        createAccountButton.addActionListener(e -> {
            if (FileHandler.getIfUsernameAlreadyTaken(usernameTextField.getText())) {

                //flash username red if already taken
                Thread thread = new Thread() {
                    public void run() {
                        usernameTextField.setBackground(Color.RED);

                        CreateAccountFrame.this.add(takenUsernameMessage);
                        CreateAccountFrame.this.repaint();
                        createAccountButton.setEnabled(false);

                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        createAccountButton.setEnabled(true);
                        usernameTextField.setBackground(Color.WHITE);

                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }

                        CreateAccountFrame.this.remove(takenUsernameMessage);
                        CreateAccountFrame.this.repaint();
                    }
                };

                thread.start();
                return;
            }


            if (!(checkForNonSpace(usernameTextField.getText()) && checkForNonSpace(passwordTextField.getText()))){
                //flash username and password field red if are just spaces (empty)
                Thread thread = new Thread(){
                    public void run(){
                        usernameTextField.setBackground(Color.RED);
                        passwordTextField.setBackground(Color.red);

                        CreateAccountFrame.this.add(invalidUsernamePasswordMessage);
                        CreateAccountFrame.this.repaint();
                        createAccountButton.setEnabled(false);

                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        createAccountButton.setEnabled(true);
                        usernameTextField.setBackground(Color.WHITE);
                        passwordTextField.setBackground(Color.WHITE);

                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }

                        CreateAccountFrame.this.remove(invalidUsernamePasswordMessage);
                        CreateAccountFrame.this.repaint();
                    }
                };

                thread.start();
                return;
            }

            new ProfileFrame(usernameTextField.getText(), passwordTextField.getText());
            CreateAccountFrame.this.dispose();

        });

        this.add(createAccountButton);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
