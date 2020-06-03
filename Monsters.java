// CSE 205
// Class ID: 1133
// Meeting date and times: T Thu 4:30pm-5:45pm
// Final Assignment 6
// Author: Rogelio A Tarin  Std Id: 1216748689
// Monsters File Contents: The Monsters class is a interface used to create multiple unique monsters that share given methods
// but do them in unique ways. A given monster is capable of knowing its name, original and current health and defense values as well
// as what type of monster it is and its weapon. It also contains the corresponding getter and setter methods, can tell if it has died,
// can attack other monsters and defend from a given attack. There is also the overKill method that let's a monster know if it has taken
// mortal damage, and a reset that sets the monster back to its original state.
//
public interface Monsters {
    public int health = 0;
    public int originalHealth = health;
    public int defense = 0;
    public int originalDefense = defense;
    public Weapon weapon = new Weapon();
    public String name = "";
    public String type = "";

    public void setHealth(int health1);

    public int getHealth();

    public boolean isAlive();

    public int overKill(Monsters target, int dmg);

    public void setDefense(int defense1);

    public int getDefense();

    public void setWeapon(Weapon weapon1);

    public void setName(String name1);

    public String getName();

    public String getType();

    public void attack(Monsters target, Monsters attacker);

    public void defend(Monsters attacker);

    public void reset();
}
