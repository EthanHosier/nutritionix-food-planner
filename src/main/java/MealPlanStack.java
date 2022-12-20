import java.io.Serializable;

//actually comprised of two stacks (a left and right stack) which nodes get popped between
public class MealPlanStack implements Serializable {
    private Stack leftStack;
    private Stack rightStack; //this is full at start (default 'pop' direction is from right stack to left stack)

    public MealPlanStack(){
        leftStack = new Stack();
        rightStack = new Stack();
    }//constructor
    public MealPlan peak(){
        return rightStack.peak().getMealPlan();
    }//returns mealPlan on top of right stack
    public void pop(){//pops node from right stack to left stack
        if (!rightStack.isEmpty()) {leftStack.put(rightStack.pop()); }
        else{System.out.println("Error: Right Stack Empty");}
    }
    public void unPop(){//pops node from LEFT stack TO RIGHT stack
        if(!leftStack.isEmpty()) {rightStack.put(leftStack.pop());}
        else{System.out.println("Error: Left Stack Empty");}
    }
    public void put(MealPlan mealPlanToPut){//unPops all nodes back onto right stack, then puts new node with new MealPlan on top of there
       reset();
       rightStack.put(new Node(mealPlanToPut));
    }
    public void reset(){//resets the stack, so that all elements are on right hand side
        while (!leftStack.isEmpty()){
            unPop();
        }
    }
    public void moveToTop(MealPlan mealPlan){ //moves mealPlan to top of stack system
        deleteMealPlan();
        put(mealPlan);
    }
    public int getLeftStackSize(){
        return leftStack.getSize();
    }
    public int getRightStackSize(){
        return rightStack.getSize();
    }
    public int getTotalSize(){return getLeftStackSize() + getRightStackSize();} //returns size of left and right stacks summed together
    public void deleteMealPlan(){rightStack.pop();}//deletes meal plan on top of right stack (meal plan selected)
    public boolean contains(String s){ //returns if either left or right stack contains s
        if (leftStack.contains(s) || rightStack.contains(s)){
            return true;
        }
        return false;
    }

    private class Stack implements Serializable{
        //node on top of stack
        private Node topNode = null;
        public boolean isEmpty(){
            return topNode == null;
        }

        public Node pop(){
            Node tempNode = topNode;
            topNode = topNode.getNodeOnTopOf();
            return tempNode;
        }

        public void put(Node nodeToPut){
            //if is empty, node gets put on top without setting node it has underneath as there is none.
            if (isEmpty()) {
                nodeToPut.setNodeOnTopOf(null);
            }else{
                nodeToPut.setNodeOnTopOf(topNode);
            }
            topNode = nodeToPut;

        }//puts node on top of stack
        public Node peak(){
            return topNode;
        }//returns node on top of stack

        public int getSize(){
            if (topNode != null){
                return recursiveGetSize(topNode,1); //size starts as 1 due to presence of top node
            }
            else{
                return 0; //returns 0 if there is no top node
            }
        }

        //returns if stack contains meal plan with name s
        public boolean contains(String s){
            if (topNode == null){
                return false;
            }

            if (topNode.getMealPlan().getName().toLowerCase().equals(s.toLowerCase())){
                return true;
            }

            else{
                return recursiveGetContains(topNode,s);
            }
        }

        private int recursiveGetSize(Node nodeOn, int size){ //recursively iterates through stack to get the size of the stack
            if (nodeOn.getNodeOnTopOf() != null){
                return recursiveGetSize(nodeOn.getNodeOnTopOf(), size + 1);
            }else{
                return size;
            }
        }


        private boolean recursiveGetContains(Node nodeOn, String s){
            if (nodeOn.getNodeOnTopOf()!= null){
                if (nodeOn.getNodeOnTopOf().getMealPlan().getName().toLowerCase().equals(s.toLowerCase())){
                    return true;
                } else{
                    return recursiveGetContains(nodeOn.getNodeOnTopOf(),s);
                }
            }

            else{
                return false;
            }
        }
    }

    private class Node implements Serializable{
        private MealPlan mealPlan; //mealPlan stored in node
        private Node nodeOnTopOf = null;//node which this node sits on top of in stack (none by default)

        public Node(MealPlan mealPlan){
            this.mealPlan = mealPlan;
        }//constructor
        public Node getNodeOnTopOf(){return nodeOnTopOf;} //returns node beneath current node in stack
        public void setNodeOnTopOf(Node nodeOnTopOf) {this.nodeOnTopOf = nodeOnTopOf;}//sets node beneath current node in stack
        public MealPlan getMealPlan() {return mealPlan;} //returns meal plan associated with node
    }
}
