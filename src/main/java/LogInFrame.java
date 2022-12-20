import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInFrame extends JFrame {

    int WIDTH = 400;
    int HEIGHT = 350;

    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JLabel   invalidLoginMessage;
    private JButton loginButton;

    public LogInFrame(){
        //get centre screen position:
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - WIDTH) / 2);
        int y = (int) ((dimension.getHeight() - HEIGHT) / 2);
        this.setBounds(x,y,WIDTH,HEIGHT);
        this.setLayout(null);
        //add log in textPane
        this.setResizable(false);

        this.add(new JLabel("Log in"){{
            this.setBounds((LogInFrame.this.WIDTH - 50 ) / 2, 25, 50,50);
            this.setFont(new Font("Sans-serif", Font.BOLD, 15));
        }});

        usernameTextField = new JTextField(){{
            this.setBounds(140,100,200,30);
        }};
        this.add(usernameTextField);

        this.add(new JLabel("Username"){{
            this.setBounds(20,100,100,30);
            this.setFont(new Font("Sans-serif", Font.BOLD,15));
        }});

        passwordTextField = new JPasswordField(){{
            this.setBounds(140,140,200,30);
        }};
        this.add(passwordTextField);

        this.add(new JLabel("Password"){{
            this.setBounds(20,140,100,30);
            this.setFont(new Font("Sans-serif", Font.BOLD,15));
        }});

        this.add(new JButton("Create Account"){{
            this.setBounds((LogInFrame.this.WIDTH - 140),5,120,20);
            this.setFont(new Font("Sans-serif",Font.BOLD,10));
            this.setFocusable(false);

            this.addActionListener(e -> {
                new CreateAccountFrame();
                LogInFrame.this.dispose();
            });
        }});

        invalidLoginMessage = new JLabel("Invalid Login"){{
            this.setForeground(Color.red);
            this.setBounds((LogInFrame.this.WIDTH + 10) / 2,90,200,200);
        }};


        loginButton = (new JButton("Log in"){{
            this.setBounds(LogInFrame.this.WIDTH - 120,LogInFrame.this.HEIGHT - 120, 70,30);
            this.setFocusable(false);
        }});

        loginButton.addActionListener(e->{
            if(FileHandler.getIfUsernamePasswordValid(usernameTextField.getText(),new String(passwordTextField.getPassword()))){

                //LOGIN TO ACCOUNT
                new HomeFrame(FileHandler.loadUser(usernameTextField.getText()));
                LogInFrame.this.dispose();

            }else{
                //flash red if invalid password
                Thread thread = new Thread(){
                    public void run(){
                        usernameTextField.setBackground(Color.RED);
                        passwordTextField.setBackground(Color.RED);

                        LogInFrame.this.add(invalidLoginMessage);
                        LogInFrame.this.repaint();
                        loginButton.setEnabled(false);

                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        loginButton.setEnabled(true);
                        usernameTextField.setBackground(Color.WHITE);
                        passwordTextField.setBackground(Color.WHITE);

                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }

                        LogInFrame.this.remove(invalidLoginMessage);
                        LogInFrame.this.repaint();
                    }
                };

                thread.start();
            }
        });

        this.add(loginButton);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }



}
