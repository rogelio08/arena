// CSE 205
// Class ID: 1133
// Meeting date and times: T Thu 4:30pm-5:45pm
// Final Assignment 6
// Author: Rogelio A Tarin  Std Id: 1216748689
// Vampire File Contents: The Vampire class implements the Monsters class and what makes the vampire unique is
// the healthDrained method that allows it to heal for a % of the damage dealt as well as having a chance to lower
// an attackers defense.
//
public class Vampire implements Monsters {
    private int health;
    private int originalHealth;
    private int defense = 20;
    private int originalDefense = defense;
    private Weapon weapon;
    private String name;
    private String type = "Vampire";


    public Vampire(){
        health = 100;
        originalHealth = health;
        weapon = new Weapon(30, "vampire bats");
        name = "Count Dracula";
    }

    public Vampire(String name1, int health1, Weapon weapon1){
        name = name1;
        health = health1;
        originalHealth = health;
        weapon = weapon1;
    }

    @Override
    public void setHealth(int health1){
        health = health1;
    }

    @Override
    public int getHealth(){
        return health;
    }

    @Override
    public boolean isAlive(){
        return (health > 0);
    }

    @Override
    public void setDefense(int defense1){
        defense = defense1;
    }

    @Override
    public int getDefense(){
        return defense;
    }

    @Override
    public void setWeapon(Weapon weapon1){
        weapon = weapon1;
    }

    @Override
    public void setName(String name1){
        name = name1;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public String getType(){
        return type;
    }

    @Override
    public void attack(Monsters target, Monsters attacker){
        if (!isAlive()){
            System.out.println(name + " is unable to continue battling");
            return;
        }
        int dmg = (weapon.getMaxDmg() - target.getDefense());
        int remainingHealth = overKill(target, dmg);
        target.setHealth((remainingHealth));
        int vamp = healthDrained(dmg);
        System.out.printf("%s shows it's fangs, then attacks %s with %s, dealing %d points of damage and heals for %d\n",
                this.name, target.getName(), weapon.getName(), dmg, vamp);
        setHealth(health + vamp);
        target.defend(attacker);
    }

    @Override
    public void defend(Monsters attacker){
        int chance = randomNum(1, 10);
        if (chance == 2){
            System.out.printf("After being attacked by %s, %s unleashes a dark curse, lowering %s's defence by 15 points\n",
                    attacker.getName(), this.name, attacker.getName());
            attacker.setDefense(defense-15);
        }
    }

    @Override
    public int overKill(Monsters monster1, int dmg){
        if ((monster1.getHealth() - dmg) <= 0){
            return 0;
        }
        else {
            return monster1.getHealth() - dmg;
        }
    }

    @Override
    public void reset(){
        health = originalHealth;
        defense = originalDefense;
    }

    public int healthDrained(int dmg){
        return dmg/5;
    }

    public int randomNum(int min, int max){
        return (int) Math.round((Math.random() * ((max - min) + 1)) + min);
    }
}
