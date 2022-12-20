import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


//gui class
public class ShoppingListFrame extends JFrame {

    int HEIGHT = 600;
    int WIDTH = 200;

    private int numOfLines;

    private String shoppingListString;

    public ShoppingListFrame(ShoppingList shoppingList){

        //set bounds
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) dimension.getWidth() - 200;
        int y = 50;
        this.setBounds(x,y,WIDTH, HEIGHT);
        this.setResizable(false);

        shoppingListString = getShoppingListString(shoppingList.getIngredients());
        setNumOfLines(shoppingListString);


        //text area
        JTextArea textArea = new JTextArea(){{
            this.setFont(new Font("Serif Sans", Font.BOLD, 15));
            this.setWrapStyleWord(true);
            this.setLineWrap(true);
            this.setText(shoppingListString);
            this.setEditable(false);
        }};

        this.add(new JScrollPane(textArea));

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

    }

    private String getShoppingListString(ArrayList<Ingredient> ingredients){
        //actual strings to be printed in text area

        if (ingredients != null) {

            String shoppingListString = "";

            Ingredient tempIngredient;
            String ingredientName;
            String unit;
            Double quantity;
            for (int i = 0; i < ingredients.size(); i++) {
                tempIngredient = ingredients.get(i);
                ingredientName = tempIngredient.getNameID();
                unit = tempIngredient.getUnit();
                quantity = tempIngredient.getQuantity();

                shoppingListString += "• ";

                if (unit == null) {
                    shoppingListString += (int)(double)quantity + " " + ingredientName;
                } else {
                    //get rid of " " on each side of unit
                    shoppingListString += quantity + " " + unit.substring(1, unit.length() - 1) + " of " + ingredientName;
                }

                shoppingListString += "\n\n";

            }

            return shoppingListString;
        }
        else{
            return "";
        }
    }

    public void setNumOfLines(String s){
        int x = 0;
        for (int i = 0; i < s.length(); i ++){
            if (s.charAt(i) == '\n'){
                x ++;
            }
        }
        //each line break is 2 lines remember
        numOfLines = x / 2;
    }



    public int getNumOfLines() {
        return numOfLines;
    }

    public String getShoppingListString(){
        return shoppingListString;
    }


}
