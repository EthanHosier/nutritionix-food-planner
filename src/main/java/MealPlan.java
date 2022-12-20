import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MealPlan implements Serializable {
    //class for ONE MEAL plan for the week

    private String name; //name of this meal plan
    private Date dateAndTime; //date and time last edited

    //7 (days) x 4 (breakfast,lunch,dinner,snacks) representing all food for week
    private Meal[][] mealPlanArray2D;

    //list created alongside this meal plan
    private ShoppingList shoppingList;

    private String[] HASH_KEYS = {"Calories", "Total Fat", "Saturated Fat", "Cholesterol", "Sodium", "Carbohydrates", "Fiber", "Sugars","Protein", "Potassium", "Total Vitamin A", "Total Vitamin B","Total Vitamin C", "Total Vitamin D"};


    public MealPlan(String name){
        this.name = name;
        shoppingList = new ShoppingList();
        mealPlanArray2D  = new Meal[7][4];
        //fill mealPlanArray
        //y
        for (int i = 0; i < mealPlanArray2D.length; i ++){
           //x
            for (int j = 0; j < mealPlanArray2D[i].length; j ++){
                mealPlanArray2D[i][j] = new Meal();
            }
        }

        updateDateAndTime();
    }

    public String getName() {
        return name;
    }

    public HashMap<String,Double> getDayNutritionTotals(int dayNum){

        //initialize hashmap
        HashMap<String,Double> dayNutritionTotals = new HashMap<>();
        for (String s:HASH_KEYS){
            dayNutritionTotals.put(s,0.0);
        }

        ArrayList<Food> foods;
        //loop through each meal of day
        for (int i = 0; i < mealPlanArray2D[dayNum].length; i ++){
            foods = mealPlanArray2D[dayNum][i].getFoods();
            //for each food within that given meal

            if (foods != null) {
                for (Food f : foods) {
                    dayNutritionTotals.put("Calories", dayNutritionTotals.get("Calories") + f.getCalories());
                    dayNutritionTotals.put("Total Fat", dayNutritionTotals.get("Total Fat") + f.getTotalFat());
                    dayNutritionTotals.put("Saturated Fat", dayNutritionTotals.get("Saturated Fat") + f.getSaturatedFat());
                    dayNutritionTotals.put("Cholesterol", dayNutritionTotals.get("Cholesterol") + f.getCholesterol());
                    dayNutritionTotals.put("Sodium", dayNutritionTotals.get("Sodium") + f.getSodium());
                    dayNutritionTotals.put("Carbohydrates", dayNutritionTotals.get("Carbohydrates") + f.getCarbohydrates());
                    dayNutritionTotals.put("Fiber", dayNutritionTotals.get("Fiber") + f.getFiber());
                    dayNutritionTotals.put("Sugars", dayNutritionTotals.get("Sugars") + f.getSugars());
                    dayNutritionTotals.put("Protein", dayNutritionTotals.get("Protein") + f.getProtein());
                    dayNutritionTotals.put("Potassium", dayNutritionTotals.get("Potassium") + f.getPotassium());
                    dayNutritionTotals.put("Total Vitamin A", dayNutritionTotals.get("Total Vitamin A") + f.getTotalVitaminA());
                    dayNutritionTotals.put("Total Vitamin B", dayNutritionTotals.get("Total Vitamin B") + f.getTotalVitaminB());
                    dayNutritionTotals.put("Total Vitamin C", dayNutritionTotals.get("Total Vitamin C") + f.getTotalVitaminC());
                    dayNutritionTotals.put("Total Vitamin D", dayNutritionTotals.get("Total Vitamin D") + f.getTotalVitaminD());
                }

            }
        }
        return dayNutritionTotals;
    }

    public HashMap<String,Double> getWeekNutritionTotals(){

        HashMap<String,Double> dayNutritionTotals;
        HashMap<String,Double> weekNutritionTotals = new HashMap<>();

        //initialize weekNutritionTotals
        for (String s: HASH_KEYS){
            weekNutritionTotals.put(s,0.0);
        }

        //add all totals
        for (int i = 0; i < mealPlanArray2D.length; i ++){
            dayNutritionTotals = getDayNutritionTotals(i);
            for (String s:HASH_KEYS){
                weekNutritionTotals.put(s,weekNutritionTotals.get(s) + dayNutritionTotals.get(s));
            }

        }

        return weekNutritionTotals;
    }

    //method to retrieve a particular day's meal from 2D array
    public Meal getMeal(int dayNum, int mealNum){
        return mealPlanArray2D[dayNum][mealNum];
    }

    // TODO: 26/08/2021 change this back to just Meal meal parameter
    public void removeMealFromShoppingList(int dayNum, int mealNum){

        System.out.println("should be removing " + mealPlanArray2D[dayNum][mealNum].getQuery());
        shoppingList.removeMealIngredientQuantities(mealPlanArray2D[dayNum][mealNum]);

    }

    // TODO: 25/08/2021 DELETE THIS AFTER THIS TEST HERE 18:18 
    public void printShoppingList(){
        shoppingList.printList();
    }


    //returns true if valid meal, false if not
    public boolean setMeal(int dayNum, int mealNum, String query){
        mealPlanArray2D[dayNum][mealNum].setQuery(query);

        //invalid meal
        if (!checkForNonSpace(query)){
            return false;
        }

        shoppingList.addIngredients(mealPlanArray2D[dayNum][mealNum]);

        if (mealPlanArray2D[dayNum][mealNum].getFoods().isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    //sets date and time to current time
    public void updateDateAndTime(){
        this.dateAndTime = new Date();
    }

    public Meal[][] getMealPlanArray2D() {
        return mealPlanArray2D;
    }

    public ShoppingList getShoppingList(){
        return shoppingList;
    }

    public boolean mealIsEmpty(int dayNum, int mealNum){
        return mealPlanArray2D[dayNum][mealNum].getQuery() == null;
    }

    public class Meal implements Serializable{
        /*
        One 'square' within meal plan grid.
        Sum of all individual foods, determined from user-entered query
         */

        private String query; //the actual text entered by user
        private ArrayList<Food> foods; //the foods making up this meal (got from api call from query)

        //sets query according to parameter, and then assigns foods<> accordingly
        public void setQuery(String query){
            this.query = query;
            foods = NixUtility.getFoods(query);
        }

        public String getQuery() {
            return query;
        }

        public ArrayList<Food> getFoods(){
            return foods;
        }

        //returns whether entered query results in valid food from api
        public boolean getMealValid(){
            return foods != null;
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