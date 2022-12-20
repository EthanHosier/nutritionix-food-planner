import java.io.Serializable;
import java.util.ArrayList;

/*
* Used within MealPlan
* 'Compresses' all the individual ingredients from each food in the plan into a single list (alphabetically)
* Priority queue used, with pointers to each ingredient node stored in hashtable
* */
public class ShoppingList implements Serializable {

    private SLHashtable hashtable;
    private SLPriorityQueue priorityQueue;

    public ShoppingList(){
        hashtable = new SLHashtable(26); //26 letters in alphabet
        priorityQueue = new SLPriorityQueue();
    }

    //overloaded methods (one for individual MealPlan.Meal, and one for whole 2D array of MealPlan.Meals):
    //1. Individual Meal:
    public void addIngredients(MealPlan.Meal meal){
        ArrayList<Food> foods = meal.getFoods();

        if (foods != null) {
            for (Food food : foods) {
                for (Ingredient ingredient : food.getIngredients()) {
                    SLIngredient tempSLIngredient = new SLIngredient(ingredient);
                    if (hashtable.smartAdd(tempSLIngredient)) {
                        //if new ingredient, is added to priority queue too
                        priorityQueue.add(tempSLIngredient);
                    }
                }
            }
        }
    }

    //2. 2D array of meals
    public void addIngredients(MealPlan.Meal[][] meals){
        for (int i = 0; i < meals.length; i ++){
            for (int j = 0; j < meals[i].length; j ++){
                addIngredients(meals[i][j]);
            }
        }
    }

    //removes the ingredient amounts which were previously added from this meal
    //if the new quantity is now 0.0 of this ingredient, is removed entirely from list
    public void removeMealIngredientQuantities(MealPlan.Meal meal){
        ArrayList<Food> foods = meal.getFoods();

        if (foods != null) {
            for (Food f : foods) {
                for (Ingredient i : f.getIngredients()) {
                    SLIngredient slIngredient = new SLIngredient(i);

                    //if new quantity is 0.0, thus fully removed from list
                    if (hashtable.remove(slIngredient)) {
                        priorityQueue.remove(slIngredient);
                    }
                }
            }
        }
    }

    public void printList(){
        if (!priorityQueue.isEmpty()) {
            priorityQueue.printPriorityQueue();
        }
        else{
            System.out.println("Empty list");
        }
    }

    //returns arrayList of all ingredients (as objects)
    public ArrayList<Ingredient> getIngredients(){
        return priorityQueue.getAllIngredients();
    }


    //Each individual ingredient making up shopping list
    private class SLIngredient extends Ingredient{

        public SLIngredient(Ingredient ingredient){
            super(ingredient.getNameID());
            setQuantity(ingredient.getQuantity());
            setUnit(ingredient.getUnit());
        }


        public void addTogether(SLIngredient i){
            this.quantity += i.getQuantity();
        }
        public void subtractQuantity(SLIngredient s){this.quantity -= s.getQuantity();}

    }

    //hashtable class for shopping list
    private class SLHashtable implements Serializable{
        //stores first listNode of each sub list per bucket
        ListNode[] buckets;

        //constructor
        public SLHashtable(int length){
            buckets = new ListNode[length];
        }

        //method to remove a given SLIngredient quantity from SLHashtable
        //if ingredients new quantity is 0, is removed entirely from hashtable (returns true)
        public boolean remove(SLIngredient ingredient){

            int sum = (int)Character.toLowerCase(ingredient.getNameID().charAt(0)) -96;

            ListNode lNodeOn = buckets[sum];


            //removes node within bucket at start of list if node is node looking for
            if (lNodeOn.getSLIngredient().getNameID().equals(ingredient.getNameID())){

                buckets[sum].getSLIngredient().subtractQuantity(ingredient);
                if (buckets[sum].getSLIngredient().getQuantity() == 0.0){
                    buckets[sum] = lNodeOn.getNextLNode();
                    return true;
                }
                return false;

            }

            else {

                boolean removed = false;

                //iterates through each lNode, checking if next node is the grid node looking for, and removes it if it is.
                while (removed == false){

                    //if node after lNodeOn is node to remove, 'removes' it
                    if (lNodeOn.getNextLNode().getSLIngredient().getNameID().equals(ingredient.getNameID())){

                        lNodeOn.getNextLNode().getSLIngredient().subtractQuantity(ingredient);

                        if (lNodeOn.getNextLNode().getSLIngredient().getQuantity() == 0.0) {
                            lNodeOn.setNextListNode(lNodeOn.getNextLNode().getNextLNode());
                            return true;
                        }

                        return false;
                    }
                    else {
                        lNodeOn = lNodeOn.getNextLNode();
                    }

                }

            }
            //~~~~ never reached when working right?{
            return false;

        }

        //method to check if given Ingredient is in SLHashtable and add it if it isnt.
        //returns true if node was added
        //returns false if node was not added (was already present in SLHashtable)
        public boolean smartAdd(SLIngredient ingredient) {

            int sum = (int)Character.toLowerCase(ingredient.getNameID().charAt(0)) -96;

            //node to start from is node at bucket
            ListNode firstLNode = buckets[sum];

            //adds lNode to bucket (start of list) if list is empty
            if (firstLNode == null) {
                buckets[sum] = new ListNode(ingredient);
                return true;
            }

            return recursiveSmartAdd(firstLNode,ingredient);

        }

        //used in SLHashtable.smartAdd()
        //returns true if node was added
        //returns false if node was not added (was already present in SLHashtable), thus quantities were added
        private boolean recursiveSmartAdd(ListNode nodeOn, SLIngredient ingredientToAdd){

            SLIngredient tempSLIngredient = nodeOn.getSLIngredient();

            if (tempSLIngredient.getNameID().equals(ingredientToAdd.getNameID())){
                //node is present therefore not added, but its ingredients added
                tempSLIngredient.addTogether(ingredientToAdd);
                return false;
            }

            //if have reached end of list, adds this node to end of list
            if (nodeOn.getNextLNode() == null){
                nodeOn.setNextListNode(new ListNode(ingredientToAdd));
                return true;
            }


            else{
                return recursiveSmartAdd(nodeOn.getNextLNode(),ingredientToAdd);
            }

        }

        public boolean checkFor(SLIngredient ingredient){

            int sum = (int)Character.toLowerCase(ingredient.getNameID().charAt(0)) -96;

            ListNode firstLNode = buckets[sum];

            //instantly returns false if bucket pointer is null
            if (firstLNode == null){
                return false;
            }

            return recursiveCheckFor(firstLNode,ingredient);

        }

        //used in EHashtable.checkFor()
        //returns true if is present, false if isn't
        private boolean recursiveCheckFor(ListNode lNodeOn, SLIngredient ingredientCheckingFor ){

            Ingredient tempIngredient = lNodeOn.getSLIngredient();

            //if current Ingredient is required Ingredient, returns true
            if (tempIngredient.getNameID().equals(ingredientCheckingFor.getNameID())){
                return true;
            }

            //if no more LNodes in list after this node, therefore GN looking for is not present
            if (lNodeOn.getNextLNode() == null){
                return false;
            }

            return recursiveCheckFor(lNodeOn.getNextLNode(), ingredientCheckingFor);

        }

        //internal class ListNode only used within EHashtable class
        private class ListNode implements Serializable{

            //Ingredient stored within ListNode
            private SLIngredient ingredientNode;

            //pointerToNextNode
            private ListNode nextListNode;

            protected ListNode(SLIngredient gridNode){
                this.ingredientNode = gridNode;
            }

            //getters + setters
            public SLIngredient getSLIngredient(){
                return ingredientNode;
            }
            public ListNode getNextLNode() {
                return nextListNode;
            }
            public void setNextListNode(ListNode listNode) {
                this.nextListNode = listNode;
            }
        }

    }

    private class SLPriorityQueue implements Serializable{

        private PQNode startNode;

        //method to add a given gridNode to the priority node
        //priority == alphabetic order of String.toLower()
        public void add(SLIngredient ingredient){

            PQNode nodeToAdd = new PQNode(ingredient);

            if (startNode == null){
                //System.out.println("null");
                startNode = nodeToAdd;
            }else{

                PQNode tempNode;

                //sets nodeToAdd as new Start node if comes before alphabetically
                if (ingredient.getNameID().compareTo(startNode.getSLIngredientName()) < 0){
                    tempNode = startNode;
                    startNode = nodeToAdd;
                    startNode.setNextNode(tempNode);
                }else {
                    recursiveAdd(nodeToAdd, startNode);
                }
            }


        }


        //recursive method utilised in add() method, adding value to priority queue
        private void recursiveAdd(PQNode nodeToAdd, PQNode currentNode){

            //holds node after current node
            PQNode afterCurrentNode = currentNode.getNextNode();

            if (afterCurrentNode == null){

                //adds node to front of queue
                currentNode.setNextNode(nodeToAdd);

            }else{

                //continues recursiveAdd of nodeToAdd if its priority is still larger than next node's priority
                if (nodeToAdd.getSLIngredientName().compareTo(afterCurrentNode.getSLIngredientName()) < 0){
                    //inserts nodeToAdd
                    currentNode.setNextNode(nodeToAdd);
                    nodeToAdd.setNextNode(afterCurrentNode);

                }else{

                    recursiveAdd(nodeToAdd,afterCurrentNode);

                }
            }

        }

        //method to print priority queue in order
        public void printPriorityQueue(){

            recursivePrint(startNode);

            //so text after starts on new line
            System.out.println();

        }

        private void recursivePrint(PQNode nodeOn){

            System.out.print(nodeOn.getSLIngredientName() + " - " + nodeOn.getSLIngredient().getQuantity()  + nodeOn.getSLIngredient().getUnit() + ";   " );

            if (nodeOn.getNextNode() != null){
                recursivePrint(nodeOn.getNextNode());
            }

        }

        public ArrayList<Ingredient> getAllIngredients(){
            if (startNode != null){
                return recursiveGetIngredient(startNode,new ArrayList<Ingredient>());
            }else{
                return null;
            }
        }

        private ArrayList<Ingredient> recursiveGetIngredient(PQNode nodeOn, ArrayList<Ingredient> allIngredients){
            allIngredients.add(nodeOn.getSLIngredient());

            if (nodeOn.getNextNode() != null){
                return recursiveGetIngredient(nodeOn.getNextNode(), allIngredients);
            }

            else {
                return allIngredients;
            }
        }

        //'pops' first node of Priority Queue, and returns it
        public Ingredient pop(){

            //makes temporary copy of start node
            PQNode tempNode = startNode;

            //new start of PQ is assigned
            startNode = startNode.getNextNode();

            return tempNode.getSLIngredient();

        }

        //returns boolean of whether priority queue is empty
        public boolean isEmpty(){
            return startNode == null;
        }

        public void remove(SLIngredient slIngredient){
            //check if start node
            if (startNode.getSLIngredient().getNameID().equals(slIngredient.getNameID())){
                startNode = startNode.getNextNode();
                return;
            }

            //otherwise,recursive remove
            recursiveRemove(startNode,slIngredient);
        }

        //used in remove()
        private void recursiveRemove(PQNode currentNode, SLIngredient slIngredient){
            if (currentNode.getNextNode().getSLIngredient().getNameID().equals(slIngredient.getNameID())){
                currentNode.setNextNode(currentNode.getNextNode().getNextNode());
                return;
            }

            recursiveRemove(currentNode.getNextNode(),slIngredient);

        }

        //private PQNode class (nodes solely used in Priority Queue class)
        private class PQNode implements Serializable{

            //GridNode value being stored
            private SLIngredient ingredient;

            private PQNode nextNode;


            protected PQNode(SLIngredient ingredient){
                this.ingredient = ingredient;
            }

            //getters and setters
            public void setNextNode(PQNode nextNode) {
                this.nextNode = nextNode;
            }


            public SLIngredient getSLIngredient() {
                return ingredient;
            }

            public String getSLIngredientName() {
                return ingredient.getNameID();
            }

            public PQNode getNextNode() {
                return nextNode;
            }

        }


    }


}
