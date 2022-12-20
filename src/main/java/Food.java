import java.io.Serializable;
import java.util.ArrayList;

//individual food
public class Food implements Serializable {

    //stores the ingredients objects for this particular food
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

    //name of food from 'food_name' key
    private String foodName;

    //dietary attributes
    private double calories, totalFat, saturatedFat, cholesterol, sodium, carbohydrates, fiber, sugars,
            protein, potassium, totalVitaminA, totalVitaminB,totalVitaminC, totalVitaminD;

    private int servingQty;

    //constructor
    public Food(String foodName){
        this.foodName = foodName;
    }

    public void setServingQty(int servingQty){this.servingQty = servingQty;};

    //method to add passed ingredient to 'ingredients' ArrayList
    public void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
    }

    public void setTotalCalories(double calories) {
        this.calories = calories;
    }

    public void setTotalCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public void setTotalCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public void setTotalPotassium(double potassium) {
        this.potassium = potassium;
    }

    public void setTotalFiber(double fiber) {
        this.fiber = fiber;
    }

    public void setTotalProtein(double protein) {
        this.protein = protein;
    }

    public void setTotalSaturatedFat(double saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public void setTotalSodium(double sodium) {
        this.sodium = sodium;
    }

    public void setTotalSugars(double sugars) {
        this.sugars = sugars;
    }

    public void setTotalFat(double totalFat) {
        this.totalFat = totalFat;
    }

    public void setTotalVitaminA(double totalVitaminA){
        this.totalVitaminA = totalVitaminA;
    }

    public void setTotalVitaminB(double totalVitaminB){
        this.totalVitaminB = totalVitaminB;
    }

    public void setTotalVitaminC(double totalVitaminC){
        this.totalVitaminC = totalVitaminC;
    }

    public void setTotalVitaminD(double totalVitaminD){
        this.totalVitaminD = totalVitaminD;
    }

    public double getCalories() { return calories;}

    public double getCholesterol() {
        return cholesterol;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public double getSaturatedFat() {
        return saturatedFat;
    }

    public double getTotalFat() {
        return totalFat;
    }

    public double getFiber() {
        return fiber;
    }

    public double getPotassium() {
        return potassium;
    }

    public double getProtein() {
        return protein;
    }

    public double getSugars() {
        return sugars;
    }

    public double getSodium() {
        return sodium;
    }

    public double getTotalVitaminA() {
        return totalVitaminA;
    }

    public double getTotalVitaminB() {
        return totalVitaminB;
    }

    public double getTotalVitaminC() {
        return totalVitaminC;
    }

    public double getTotalVitaminD() {
        return totalVitaminD;
    }

    public int getServingQty(){return servingQty;};

    public String getFoodName(){
        return foodName;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
}
