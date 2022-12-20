import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

//gui class
public class MealPlanFrame extends JFrame {

    private final int WIDTH = 1200;
    private final int HEIGHT = 800;

    private ShoppingListFrame shoppingListFrame = null;
    private AnalyseDayFrame analyseDayFrame = null;
    private WeekAnalysisFrame weekAnalysisFrame = null;

    private TextAreaGridPanel textAreaGridPanel;

    public MealPlanFrame(User user, MealPlan mealPlan){

        this.setTitle(mealPlan.getName());

        //so at centre
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - WIDTH) / 2);
        int y = (int) ((dimension.getHeight() - HEIGHT) / 2);

        this.setBounds(x,y - 20,WIDTH,HEIGHT);
        this.setLayout(null);
        this.setResizable(false);

        textAreaGridPanel = new TextAreaGridPanel(mealPlan,800,500,this){{
            this.setBounds( (MealPlanFrame.this.WIDTH - 800) / 2,(MealPlanFrame.this.HEIGHT - 500) / 2,800,500);
        }};

        this.add(textAreaGridPanel);

        //meals label
        this.add(new JLabel(){{
            this.setText("Breakfast                      Lunch                         " +
                         "Dinner                        Snacks");
            this.setBounds(250,90,900,50);
            this.setFont(new Font("Serif Sans", Font.BOLD,20));
        }});

        //days labels
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        int daysX = 50;
        int startDaysY = 165;
        int spacing = 71;
        for (int i = 0 ; i < days.length; i ++){
            int finalI = i;
            this.add(new JLabel(days[finalI]){{
                this.setBounds(daysX,startDaysY + spacing * finalI,150,30);
                this.setFont(new Font("Serif Sans", Font.BOLD,20));
            }});
        }

        //shopping list button
        this.add(new JButton("Shopping List"){{
            this.setBounds(MealPlanFrame.this.WIDTH - 180,80,150,50);
            this.setFocusable(false);
            this.setFont(new Font("Serif Sans", Font.BOLD, 12));
            this.setBackground(new Color(0xA027EF));
            this.addActionListener(e->{
                if (shoppingListFrame != null){
                    shoppingListFrame.dispose();
                }
                shoppingListFrame = new ShoppingListFrame(mealPlan.getShoppingList());
            });
        }});

        //print list button
        this.add(new JButton("Print List"){{
            this.setBounds(MealPlanFrame.this.WIDTH - 180,10,150,50);
            this.setFocusable(false);
            this.setFont(new Font("Serif Sans", Font.BOLD, 12));
            this.setBackground(new Color(0xA027EF));

            this.addActionListener(e -> {

                ShoppingListFrame tempShoppingListFrame = new ShoppingListFrame(mealPlan.getShoppingList()){{

                    this.setBounds(-50,-50,0,0);
                    this.setVisible(false);
                }};


                JPanel tempPanel = new JPanel(){{
                    this.setLayout(new GridLayout(1,0));
                    this.add(new JTextArea(){{
                        this.setFont(new Font("Serif Sans", Font.BOLD, 8));
                        this.setWrapStyleWord(true);
                        this.setLineWrap(true);
                        this.setText(tempShoppingListFrame.getShoppingListString());
                        this.setEditable(false);
                    }});
                    this.setBackground(Color.BLUE);
                }};

                System.out.println(tempShoppingListFrame.getNumOfLines());

                JFrame tempFrame = new JFrame(){{
                    this.setBounds(-2000,-2000,1000,tempShoppingListFrame.getNumOfLines() * 50);
                    this.setLayout(new GridLayout(1,0));
                    this.add(tempPanel);
                    this.setVisible(true);
                }};

               MealPlanFrame.this.printComponent(tempPanel);
            });
        }});

        //home button
        this.add(new JButton("Home"){{
            this.setBounds(MealPlanFrame.this.WIDTH / 2 - 170,25,80,50);
            this.setFocusable(false);
            this.setFont(new Font("Serif Sans", Font.BOLD, 16));
            this.setBackground(new Color(0xA027EF));

            this.addActionListener(e->{
                //moves the current meal plan to top of MealPlanStack (so in order of when opened)
                user.getMealPlanStack().moveToTop(mealPlan);
                new HomeFrame(user);
                //save users meal plans
                FileHandler.saveUser(user);
                MealPlanFrame.this.dispose();
            });
        }});

        //analyse day buttons
        int startButtonY = 160;
        int ButtonX = WIDTH - 150;
        int buttonSpacing = 73;
        for (int i = 0; i < 7; i ++){

            int finalI = i;
            this.add(new JButton("Analyse"){{
                this.setFont(new Font("Serif Sans", Font.BOLD, 15));
                this.setBounds(ButtonX, startButtonY + spacing * finalI, 100,50);
                this.setFocusable(false);
                if (finalI % 2 == 0){
                    this.setBackground(new Color(0xCBCBCB));
                }else{
                    this.setBackground(new Color(0xE8E8E8));
                }
                this.addActionListener(e->{
                    if (analyseDayFrame != null){
                        analyseDayFrame.dispose();
                    }
                    analyseDayFrame = new AnalyseDayFrame(mealPlan.getDayNutritionTotals(finalI),user);
                });
            }});

        }

        //icon top left
        java.net.URL imageURL = Main.class.getClassLoader().getResource("98by130logo.png");
        Image image = null;

        try {
            image = ImageIO.read(imageURL);

        }catch (Exception e){
            System.out.println("Error loading image");
        }

        this.add(new JLabel(new ImageIcon(image)){{
            this.setBounds(15,0,180,180);
        }});



        this.add(new JButton("Analyse Week"){{
            this.setBounds((MealPlanFrame.this.WIDTH - 150) / 2, MealPlanFrame.this.HEIGHT - 120, 150,50);
            this.setFont(new Font("Sans-serif", Font.BOLD, 14));
            this.setBackground(new Color(0xA027EF));
            this.setFocusable(false);

            this.addActionListener(e -> {
                if (weekAnalysisFrame != null){
                    weekAnalysisFrame.dispose();
                }

                weekAnalysisFrame = new WeekAnalysisFrame(user,mealPlan);
            });

        }});

        //save button

        //home button
        this.add(new JButton("Save"){{
            this.setBounds(MealPlanFrame.this.WIDTH / 2 + 90,25,80,50);
            this.setFocusable(false);
            this.setFont(new Font("Serif Sans", Font.BOLD, 16));
            this.setBackground(new Color(0xA027EF));

            this.addActionListener(e->{
                FileHandler.saveUser(user);
        });
        }});



        this.repaint();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);




    }

    public void printComponent(Component component){
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setJobName(" Print Component ");

        pj.setPrintable (new Printable() {
            public int print(Graphics pg, PageFormat pf, int pageNum){
                if (pageNum > 0){
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2 = (Graphics2D) pg;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                component.paint(g2);
                return Printable.PAGE_EXISTS;
            }
        });
        if (pj.printDialog() == false)
            return;

        try {
            pj.print();
        } catch (PrinterException ex) {
            // handle exception
        }
    }


    private class TextAreaGridPanel extends JPanel{

        public TextAreaGridPanel(MealPlan mealPlan, int width, int height,MealPlanFrame mealPlanFrame){

            JTextArea[][] textAreaGrid = new JTextArea[7][4];

            this.setLayout(new GridLayout(7,4));
            this.setBackground(new Color(0xA8A5A5));

            //y
            for (int i = 0; i < 7; i ++){
                //x
                for (int j = 0; j < 4; j ++) {
                    int finalI = i;
                    int finalJ = j;

                    JTextArea tempJTextArea = new JTextArea() {

                        public void resetSquareColour(int i){
                            if (i % 2 == 0){
                                this.setBackground(new Color(0xCBCBCB));
                            }else{
                                this.setBackground(new Color(0xE8E8E8));
                            }
                        }
                        {

                        this.setBorder(new LineBorder(new Color(0xBDBDBD), 2));
                        this.setLineWrap(true);
                        this.setWrapStyleWord(true);
                        this.setFont(new Font("Sarif Sans", Font.BOLD, 12));
                        resetSquareColour(finalI);
                        this.setText(mealPlan.getMeal(finalI,finalJ).getQuery());
                    }};

                    //add listener for if click off box
                    tempJTextArea.addFocusListener(new FocusListener() {
                        @Override
                        public void focusGained(FocusEvent e) {

                            if (finalI % 2 == 0){
                                tempJTextArea.setBackground(new Color(0xCBCBCB));
                            }else{
                                tempJTextArea.setBackground(new Color(0xE8E8E8));
                            }
                        }

                        @Override
                        public void focusLost(FocusEvent e) {

                             mealPlan.removeMealFromShoppingList(finalI, finalJ); //remove what was in square before change from shopping list

                                //attempt to set meal as what was inputted into square (after reformatting) - if is invalid food item, box turns red
                                if (!mealPlan.setMeal(finalI, finalJ, tempJTextArea.getText().replaceAll("\n", ", "))) {
                                    if (checkForNonSpace(tempJTextArea.getText())) {
                                        tempJTextArea.setBackground(Color.red);
                                    }

                                //if was valid food item, flashes green
                                } else {
                                    //temporarily flashes green - separate thread must be called otherwise whole system would sleep for 400 ms.
                                    Thread flashGreen = new Thread(() -> { //initiate thread instance
                                        tempJTextArea.setBackground(Color.green); //set colour as green
                                        mealPlanFrame.repaint();    //forces GUI to repaint square

                                        try { //sleep for 400ms
                                            Thread.sleep(400);
                                        } catch (InterruptedException interruptedException) {
                                            interruptedException.printStackTrace();
                                        }

                                        if (finalI % 2 == 0) { //reset square colour to its original colour
                                            tempJTextArea.setBackground(new Color(0xCBCBCB));
                                        } else {
                                            tempJTextArea.setBackground(new Color(0xE8E8E8));
                                        }

                                        mealPlanFrame.repaint();  //forces GUI to repaint square
                                    });
                                    flashGreen.start(); //run thread.
                                }
                            }

                       // }
                    });
                    this.add(new JScrollPane(tempJTextArea));
                    textAreaGrid[i][j] = tempJTextArea;
                }
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

}
