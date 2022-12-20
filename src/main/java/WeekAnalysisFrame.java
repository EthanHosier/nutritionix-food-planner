import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;

//GUI class
public class WeekAnalysisFrame extends JFrame {

    private int WIDTH = 600;
    private int HEIGHT = 300;

    public WeekAnalysisFrame(User user, MealPlan mealPlan){

        //so at centre
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - WIDTH) / 2);
        int y = (int) ((dimension.getHeight() - HEIGHT) / 2);

        this.setBounds(x,y,WIDTH,HEIGHT);
        this.setResizable(false);
        this.setLayout(null);

        //set days
        NutrientHashMap dailyNutrientRequirements = user.getDailyNutrientRequirements();
        String[] HASH_KEYS = {"Calories", "Total Fat", "Saturated Fat", "Cholesterol", "Sodium", "Carbohydrates", "Fiber", "Sugars","Protein", "Potassium", "Total Vitamin A", "Total Vitamin B","Total Vitamin C", "Total Vitamin D"};

        String[] days = {"Monday","Tuesday","Wednesday", "Thursday", "Friday", "Saturday","Sunday"};
        double[] percentageDifferenceTotalsPerDay = new double[7]; // one per day

        for (int i = 0; i < 7; i ++){
            HashMap<String,Double> dayNutritionTotals = mealPlan.getDayNutritionTotals(i);
            for (int j = 0; j < HASH_KEYS.length; j ++){
                percentageDifferenceTotalsPerDay[i] += getPercentageDifference(dayNutritionTotals.get(HASH_KEYS[j]),(Double) dailyNutrientRequirements.get(HASH_KEYS[j], "Quantity"));
            }
        }

        //getBestday
        int bestDay = 0;
        for (int i = 1; i < percentageDifferenceTotalsPerDay.length; i ++){
            if (percentageDifferenceTotalsPerDay[i] < percentageDifferenceTotalsPerDay[bestDay]){
                bestDay = i;
            }
        }

        //getWorstDay
        int worstDay = 0;
        for (int i = 1; i < percentageDifferenceTotalsPerDay.length; i ++){
            if (percentageDifferenceTotalsPerDay[i] > percentageDifferenceTotalsPerDay[bestDay]){
                worstDay = i;
            }
        }

        //set total percentage difference
        double totalPercentageDifference = 0;
        for (int i = 0; i < percentageDifferenceTotalsPerDay.length; i ++){
            totalPercentageDifference += percentageDifferenceTotalsPerDay[i];
        }

        int finalBestDay = bestDay;
        this.add(new JLabel(){{
            this.setText("Your best day of the week was " + days[finalBestDay] + " with an average % nutrient difference of " + new DecimalFormat("#.00").format(percentageDifferenceTotalsPerDay[finalBestDay] / (double)HASH_KEYS.length ) + "%");
            this.setFont(new Font("Sans-serif", Font.BOLD, 12));
            this.setBounds(5,55,600,20);
            this.setForeground(Color.green);
        }});


        int finalWorstDay = worstDay;
        this.add(new JLabel(){{
            this.setText("Your worst day of the week was " + days[finalWorstDay] + " with an average % nutrient difference of " + new DecimalFormat("#.00").format(percentageDifferenceTotalsPerDay[finalWorstDay] / (double)HASH_KEYS.length) + "%" );
            this.setFont(new Font("Sans-serif", Font.BOLD, 12));
            this.setBounds(5,100,600,20);
            this.setForeground(Color.red);
        }});

        double finalTotalPercentageDifference = totalPercentageDifference;
        this.add(new JLabel(){{
            this.setText("On average, you had a % nutrient difference of " + new DecimalFormat("#.00").format(finalTotalPercentageDifference / (double)(HASH_KEYS.length * days.length) )+ "%");
            this.setFont(new Font("Sans-serif", Font.BOLD, 12));
            this.setBounds(5,145,600,20);
        }});



        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }


        private double getPercentageDifference(double num1, double num2){
        return (Math.abs(num1 - num2) / num2) * 100;
        }

}
