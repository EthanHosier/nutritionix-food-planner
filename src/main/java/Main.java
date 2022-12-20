import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main{

    //store somewhere:

    private final  String vitaminAunit = "IU";
    private final String vitaminBunit = "mg";
    private final String vitaminCunit = "mg";
    private final  String vitaminDunit = "mg";



    public static void main(String[] args) {


        //ArrayList<Food> foods = NixUtility.getMeals("car");


        //User f1 = new FemaleUser("girl", 20,130,50,true,"Lightly Active");
        //f1.setDietaryAttributes();


       // new SetUpProfileFrame(null,700,750);

        MealPlan mp = new MealPlan("test meal plane");

       // mp.setMeal(2,3,"2 portions of Spaghetti bolognese, 2 apples");
       // mp.setMeal(2,2,"one omelette");
        // mp.printShoppingList();

        //new MealPlanFrame(new FemaleUser("1",20,165,55,"Sedentary", Color.gray,true), new MealPlan("ahah"));


        String mealPlanNames[] = {"meal 1", "meal 2", "meal 3", "meal 4"};

        /*
        new HomeFrame(new FemaleUser("1",20,165,55,"Sedentary", Color.gray,true){{

            /*
            for(String s:mealPlanNames){
                this.addNewMealPlan(new MealPlan(s));
            }



        }});
        */

        //new ProfileFrame("username");

        FileHandler.checkForDirectory();
        new LogInFrame();

    }


}





