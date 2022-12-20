import java.awt.*;
import java.io.Serializable;

public class User implements Serializable {

    protected String name;
    protected int age;
    protected int height; //cm
    protected int weight; //kg
    protected String exerciseLevel; //Sedentary, Lightly Active, Active
    protected Color profileColour;
    protected double dailyCalories;
    private NutrientHashMap dailyNutrientRequirements = new NutrientHashMap();
    private MealPlanStack mealPlanStack;

    public User(String name, int age, int height, int weight, String exerciseLevel, Color profileColour){
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.exerciseLevel = exerciseLevel;
        this.profileColour = profileColour;

        setDailyDietaryAttributes();

        mealPlanStack = new MealPlanStack();
    } //constructor
    public void setDailyDietaryAttributes(){
        setDailyCalories(getBmr());

        //set each individual nutrient (man)
        setIndividualNutrient("Calories",dailyCalories,"kcal");
        setIndividualNutrient("Total Fat",66 ,"g");
        setIndividualNutrient("Saturated Fat", 30 ,"g");
        setIndividualNutrient("Cholesterol", 250 ,"mg");
        setIndividualNutrient("Sodium", 2000 ,"mg");
        setIndividualNutrient("Carbohydrates", 300 ,"g");
        setIndividualNutrient("Fiber",30,"g");
        setIndividualNutrient("Sugars", 25 ,"g");
        setIndividualNutrient("Protein", 0.75 * weight ,"g");
        setIndividualNutrient("Potassium", 3500,"mg");
        setIndividualNutrient("Total Vitamin A", 900 ,"Âµg");
        setIndividualNutrient("Total Vitamin B", 2.4 ,"Âµg");
        setIndividualNutrient("Total Vitamin C", 40 ,"Âµg");
        setIndividualNutrient("Total Vitamin D", 10 ,"Âµg");






    } //sets users nutritional daily requirements
    protected void setIndividualNutrient(String nutrientName, double quantity, String unit){
        dailyNutrientRequirements.put(nutrientName,"Quantity", quantity);
        dailyNutrientRequirements.put(nutrientName,"Unit",unit);
    } //sets a nutrient to a given quantity
    protected void setDailyCalories(double bmr){

        if (exerciseLevel.equals("Sedentary")){
            dailyCalories = bmr * 1;
            return;
        }

        if (exerciseLevel.equals("Lightly Active")){
            dailyCalories = bmr * 1.375;
            return;
        }

        if (exerciseLevel.equals("Active")) {
            dailyCalories = bmr * 1.5;
            return;
        }

    } //returns user's daily calories
    private double getBmr(){
        System.out.println("men bmr");
        //men: BMR=66.47+ (13.75 x W) + (5.0 x H) - (6.75 x A)
        return 66.47 + (13.75 * (double) weight) + (5.0 * (double) height) - (6.75 * (double) age);
    } //returns user's computed BMR
    public NutrientHashMap getDailyNutrientRequirements() {
        return dailyNutrientRequirements;
    } //returns NutrientHashMap of user's nutritional requirements
    public Color getProfileColour() {
        return profileColour;
    } //returns profile colour user has chosen
    public String getName() {
        return name;
    } //returns user's name
    public MealPlanStack getMealPlanStack(){
        return mealPlanStack;
    } //returns the users meal plan stack (all meal plans in 'last opened' order)
    public void updateProfile(int age, int height, int weight, String exerciseLevel, Color profileColour){
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.exerciseLevel = exerciseLevel;
        this.profileColour = profileColour;

        setDailyDietaryAttributes();

    } //updates profile accordingly
    public int getAge() {
        return age;
    } //returns user's age
    public int getHeight() {
        return height;
    } //returns user's height
    public int getWeight() {
        return weight;
    } //returns user's weight
    public String getExerciseLevel() {
        return exerciseLevel;
    } //returns excercise level
}
