import java.awt.*;

public class FemaleUser extends User{
    private boolean isPregnant;

    public FemaleUser(String name, int age, int height, int weight, String exerciseLevel, Color iconColour, boolean isPregnant) {
        super(name, age, height,weight,exerciseLevel, iconColour);
        this.isPregnant = isPregnant;

        setDailyDietaryAttributes();
    } //constructor
    public void setDailyDietaryAttributes(){
        setDailyCalories(getBmr());

        //set individual nutrient for woman
        setIndividualNutrient("Calories",dailyCalories,"kcal");
        setIndividualNutrient("Total Fat",60 ,"g");
        setIndividualNutrient("Saturated Fat", 20 ,"g");
        setIndividualNutrient("Cholesterol", 250 ,"mg");
        setIndividualNutrient("Sodium", 2000 ,"mg");
        setIndividualNutrient("Carbohydrates", 250 ,"g");
        setIndividualNutrient("Fiber",30,"g");
        setIndividualNutrient("Sugars", 25 ,"g");
        setIndividualNutrient("Protein", 0.75 * weight ,"g");
        setIndividualNutrient("Potassium", 3500,"mg");
        setIndividualNutrient("Total Vitamin A", 700 ,"Âµg");
        setIndividualNutrient("Total Vitamin B", 2.4 ,"Âµg");
        setIndividualNutrient("Total Vitamin C", 40 ,"Âµg");
        setIndividualNutrient("Total Vitamin D", 10 ,"Âµg");


    } //sets female users daily attributes
    private double getBmr(){
        //women: BMR=665.09 + (9.56 x W) + (1.84 x H) - (4.67 x A)
        double bmr  = 665.09 + (9.56 * weight) + (1.84 * height) - (4.67 * age);

        System.out.println("woman bmr");

        if (isPregnant){
            return bmr * 1.2;
        } else{
            return bmr;
        }
    } //returns female user's computed bmr
    public void updateProfile(int age, int height, int weight, String exerciseLevel, Color profileColour, boolean isPregnant){
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.exerciseLevel = exerciseLevel;
        this.profileColour = profileColour;
        this.isPregnant = isPregnant;

        setDailyDietaryAttributes();

    } //updates profile
    public boolean isPregnant(){
        return isPregnant;
    } //returns bool of whether user is pregnant
}
