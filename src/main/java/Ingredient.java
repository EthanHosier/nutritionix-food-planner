import java.io.Serializable;

//class for individual ingredient of each food (from sub_recipe)
public class Ingredient implements Serializable {

    //stores name ID assigned from nutritionIX database - used for 'stacking' of ingredients in shopping list
    protected String nameID;

    //quantity of ingredient of unit
    protected double quantity;

    //unit of measurement for ingredient
    protected String unit;

    //dietary attributes
    private double calories, totalFat, saturatedFat, cholestral, sodium, carbohydrates, fiber, sugars, protein, potassium;

    //constructor
    public Ingredient(String nameID){
        this.nameID = nameID;
    }

    public void setQuantity(double quantity){
        this.quantity = quantity;
    }

    public void setUnit(String unit){
        this.unit = unit;
    }

    protected double getQuantity(){ return quantity; }

    protected String getNameID(){
        return nameID;
    }

    protected String getUnit(){
        return unit;
    }
    //////
    public void printInformation(){
        System.out.println(nameID + ": " + quantity + unit);
    }

}


