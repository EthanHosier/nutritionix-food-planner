import java.io.Serializable;
import java.util.HashMap;

public class NutrientHashMap implements Serializable {
    //Associates the specified map (from nutrient name) with the specified key within this map

    //main Hashmap of "nutrient name" : nutrient attributes
    private HashMap<String, HashMap<String, Object>> nutrientMap;

    public NutrientHashMap(){
        nutrientMap = new HashMap<>();
    } //constructor

    public void put(String nutrientName, String key, Object value){
        //if nutrientName already in nutrientMap
        if (nutrientMap.containsKey(nutrientName)){
            nutrientMap.get(nutrientName).put(key,value);
        }

        //if new nutrientName
        else{
            nutrientMap.put(nutrientName, new HashMap<>(){{
                this.put(key,value);
            }});
        }
    }

    public Object get(String nutrientName, String key){
        return nutrientMap.get(nutrientName).get(key);
    }
}
