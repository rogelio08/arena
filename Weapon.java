// CSE 205
// Class ID: 1133
// Meeting date and times: T Thu 4:30pm-5:45pm
// Final Assignment 6
// Author: Rogelio A Tarin  Std Id: 1216748689
// Weapon File Contents: The weapon class is made to be a "has a" relationship with a monster. A weapon can be instantiated with a given
// name and average damage and contains setter methods to change those values depending on the monster. When returning damage it returns a value
// between the range on 7 above and 7 below to mix up combat.
//
public class Weapon {
    private int maxDmg;
    private String name;

    public Weapon(){
        maxDmg = 25;
        name = "wooden sword";
    }

    public Weapon(int dmg, String newName){
        maxDmg = dmg;
        name = newName;
    }

    public void setMaxDmg(int dmg){
        maxDmg = dmg;
    }

    public int getMaxDmg(){
        int dmg = randomNum(maxDmg-7, maxDmg+7);
        return dmg;
    }

    public void setName(String newName){
        name = newName;
    }

    public String getName(){
        return name;
    }

    public int randomNum(int min, int max){
        return (int) Math.round((Math.random() * ((max - min) + 1)) + min);
    }
}
