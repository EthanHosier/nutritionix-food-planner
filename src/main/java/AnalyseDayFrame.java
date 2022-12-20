import javax.swing.*;
import java.awt.*;
import java.util.HashMap;


public class AnalyseDayFrame extends JFrame {

    int HEIGHT = 550;
    int WIDTH = 300;

    int startLabelY = 0;
    int labelX = 0;
    int labelSpacing = 30;

    public AnalyseDayFrame(HashMap<String,Double> dayNutritionTotals, User user){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) dimension.getWidth() - 400;
        int y = 50;
        this.setBounds(x,y,WIDTH, HEIGHT);
        this.setLayout(null);

        NutrientHashMap dailyNutrientRequirements = user.getDailyNutrientRequirements();
        String[] HASH_KEYS = {"Calories", "Total Fat", "Saturated Fat", "Cholesterol", "Sodium", "Carbohydrates", "Fiber", "Sugars","Protein", "Potassium", "Total Vitamin A", "Total Vitamin B","Total Vitamin C", "Total Vitamin D"};

        for (int i = 0; i< HASH_KEYS.length; i ++){
            int finalI = i;
            this.add(new JLabel(){{
                String text = HASH_KEYS[finalI] + ":   " + dayNutritionTotals.get(HASH_KEYS[finalI]) + " out of " + dailyNutrientRequirements.get(HASH_KEYS[finalI],"Quantity") + dailyNutrientRequirements.get(HASH_KEYS[finalI],"Unit");
                this.setText(text);
                this.setBounds(labelX,startLabelY + labelSpacing * finalI,400,50);
                this.setFont(new Font("Serif Sans", Font.BOLD,12));
                this.setForeground(getTextColour(dayNutritionTotals.get(HASH_KEYS[finalI]), (double) dailyNutrientRequirements.get(HASH_KEYS[finalI],"Quantity")));
            }});
        }

        this.add(new JLabel());


        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);


    }

    private Color getTextColour(double num1, double num2){
        double percentageDifference = Math.abs((num1 - num2)) / num2;

        if (percentageDifference <= 0.1){
            return new Color(0x00D022);
        }

        if (percentageDifference <= 0.3){
            return Color.orange;
        }

        return Color.red;
    }
}
