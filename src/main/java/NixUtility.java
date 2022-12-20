//orangebook: ba6cd4be; 2aeb61b98f69fac662996763d664ff48
//ejhosier: c39a75d6; 2146a35f6cd7240be5b100f827209abd

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

//utility class for handling API requests and json response parsing
public abstract class NixUtility {
    private final static String x_app_id = "APP_ID";
    private final static String x_app_key = "APP_KEY";
    //base URI of NutritionIX natural language processing endpoint.
    private final static String baseURI = "https://trackapi.nutritionix.com/v2/natural/nutrients";
    //nutrient ID values:
    private final static int vitaminAid = 320;
    private final static int vitaminBid = 418;
    private final static int vitaminCid = 401;
    private final static int vitaminDid = 328;

    public static ArrayList<Food> getFoods(String query){ //returns arrayList of foods from given query
            //get json string for passing from query
            String apiResponse = getAPIResponse(query);
            //creating instance of jsonReader for parsing json string
            JsonReader jsonReader = Json.createReader(new StringReader(apiResponse));
            JsonObject obj = jsonReader.readObject();
            JsonArray foodsJsonArray =  obj.getJsonArray("foods");
            return getFoodsArrayList(foodsJsonArray);
    } //returns arraylist of food objects computed from query

    private static String getAPIResponse(String query){

        HttpClient client = HttpClient.newBuilder().build();

        //building http post request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURI))
                .POST(HttpRequest.BodyPublishers.ofString("{\"query\":\"" + query + "\", \"include_subrecipe\":\"true\"}"))
                .header("x-app-id",x_app_id)
                .header("x-app-key",x_app_key)
                .build();

        //post request
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    } //gets JSON string from api

    private static ArrayList<Food> getFoodsArrayList(JsonArray foodsJsonArray){
        ArrayList<Food> foodsArrayList = new ArrayList<>(); //values added as JsonArray is parsed

        //temporary food object used in adding each food to foodsArrayList
        Food tempFood;
        if (foodsJsonArray != null) { // if food contains subfoods
            //add each food to foodsArrayList
            for (final JsonObject food : foodsJsonArray.getValuesAs(JsonObject.class)) { //iterate through each food in list
                String foodName = food.getString("food_name"); //parse name of food
                tempFood = new Food(foodName);

                //All set() statements must be surrounded by try catch, in case of null value
                try {//parse calorie total
                    double calories = food.getJsonNumber("nf_calories").doubleValue();
                    tempFood.setTotalCalories(calories);
                } catch (ClassCastException e) {//if null value
                    tempFood.setTotalCalories(0);
                }

                try {//parse total fat
                    double totalFat =food.getJsonNumber("nf_total_fat").doubleValue();
                    tempFood.setTotalFat(totalFat);
                } catch (ClassCastException e) {//if null value
                    tempFood.setTotalFat(0);
                }

                try {
                    double saturatedFat = food.getJsonNumber("nf_saturated_fat").doubleValue();
                    tempFood.setTotalSaturatedFat(saturatedFat);
                } catch (ClassCastException e) {
                    tempFood.setTotalSaturatedFat(0);
                } //saturated fat

                try {
                    double cholesterol = food.getJsonNumber("nf_cholesterol").doubleValue();
                    tempFood.setTotalCholesterol(cholesterol);
                } catch (ClassCastException e) {
                    tempFood.setTotalCholesterol(0);
                } //cholesterol

                try {
                    double sodium = food.getJsonNumber("nf_sodium").doubleValue();
                    tempFood.setTotalSodium(sodium);
                } catch (ClassCastException e) {
                    tempFood.setTotalSodium(0);
                } //sodium

                try {
                    double carbohydrates = food.getJsonNumber("nf_total_carbohydrate").doubleValue();
                    tempFood.setTotalCarbohydrates(carbohydrates);
                } catch (ClassCastException e) {
                    tempFood.setTotalCarbohydrates(0);
                } //carbohydrates

                try {
                    int servingQty = food.getJsonNumber("serving_qty").intValue();
                    tempFood.setServingQty(servingQty);
                }catch (ClassCastException e){
                    tempFood.setServingQty(0);
                }
                try {
                    double fiber = food.getJsonNumber("nf_dietary_fiber").doubleValue();
                    tempFood.setTotalFiber(fiber);
                } catch (ClassCastException e) {
                    tempFood.setTotalFiber(0);
                }

                try {
                    double sugar = food.getJsonNumber("nf_sugars").doubleValue();
                    tempFood.setTotalSugars(sugar);
                } catch (ClassCastException e) {
                    tempFood.setTotalSugars(0);
                }

                try {
                    double protein = food.getJsonNumber("nf_protein").doubleValue();
                    tempFood.setTotalProtein(protein);
                } catch (ClassCastException e) {
                    tempFood.setTotalProtein(0);
                }

                try {
                    double potassium = food.getJsonNumber("nf_potassium").doubleValue();
                    tempFood.setTotalPotassium(potassium);
                } catch (ClassCastException e) {
                    tempFood.setTotalPotassium(0);
                }


                //add nutrients to the food:
                //stores nutrient ID for each nutrient within the food
                int tempNutrientID;
                JsonArray fullNutrients = null;
                //in case of null value
                try {
                    fullNutrients = food.getJsonArray("full_nutrients");
                } catch (ClassCastException e) {
                    //nothing
                }

                if (fullNutrients != null) {
                    for (final JsonObject nutrient : fullNutrients.getValuesAs(JsonObject.class)) {

                        tempNutrientID = nutrient.getJsonNumber("attr_id").intValue();

                        if (tempNutrientID == vitaminAid) {
                            try {
                                tempFood.setTotalVitaminA(nutrient.getJsonNumber("value").doubleValue());
                            } catch (ClassCastException e) {
                                tempFood.setTotalVitaminA(0);
                            }
                        } else if (tempNutrientID == vitaminBid) {
                            try {
                                tempFood.setTotalVitaminB(nutrient.getJsonNumber("value").doubleValue());
                            } catch (ClassCastException e) {
                                tempFood.setTotalVitaminB(0);
                            }
                        } else if (tempNutrientID == vitaminCid) {

                            try {
                                tempFood.setTotalVitaminC(nutrient.getJsonNumber("value").doubleValue());
                            } catch (ClassCastException e) {
                                tempFood.setTotalVitaminC(0);
                            }
                        } else if (tempNutrientID == vitaminDid) {
                            try {
                                tempFood.setTotalVitaminD(nutrient.getJsonNumber("value").doubleValue());
                            } catch (ClassCastException e) {
                                tempFood.setTotalVitaminD(0);
                            }
                        }
                    }
                }

                JsonArray subRecipe = null;
                try {//in case of null value
                    subRecipe = food.getJsonArray("sub_recipe");
                } catch (ClassCastException e) {} //do nothing
                if (subRecipe != null) { //if there is a sub recipe, add each ingredient to the Food object.
                    for (final JsonObject sub_recipe : subRecipe.getValuesAs(JsonObject.class)) { //for each ingredient:
                        Ingredient tempIngredient = new Ingredient(sub_recipe.getString("food"));
                        try {
                            tempIngredient.setQuantity(sub_recipe.getJsonNumber("serving_qty").doubleValue()); //set the ingredient's quantity
                        } catch (ClassCastException e) {
                            tempIngredient.setQuantity(0);
                        }
                        try {
                            tempIngredient.setUnit(sub_recipe.getJsonString("serving_unit").toString()); //set the ingredient's unit
                        } catch (ClassCastException e) { //if unit is 'null', no unit set.
                            tempIngredient.setUnit(null);
                        }
                        tempFood.addIngredient(tempIngredient); //add this ingredient to the Food object
                    }
                }else{ //if no sub-recipe present
                    //set ingredient as itself (eg an apple has ingredient apple)
                    Food finalTempFood = tempFood;
                    tempFood.addIngredient(new Ingredient(finalTempFood.getFoodName()){{
                        this.setUnit(null);
                        this.setQuantity(finalTempFood.getServingQty());
                    }});
                }
                foodsArrayList.add(tempFood); //add the food to the foods list
            }
        }
        return foodsArrayList; //return the food array (containing all foods with their nutritional attributes and ingredients)

    } //parses the jsonArray into an arrayList of Food objects
}
