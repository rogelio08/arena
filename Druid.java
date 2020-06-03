// CSE 205
// Class ID: 1133
// Meeting date and times: T Thu 4:30pm-5:45pm
// Final Assignment 6
// Author: Rogelio A Tarin  Std Id: 1216748689
// Druid File Contents: The Druid class implements the Monsters interface and what makes the druid unique is that when attacking
// it spreads seeds around the opponents feet which will grow over the course of a few turns before blooming and exploding. When
// being attacked it has a chance to deal 10% of it's max health as dmg to the attacker.
//
public class Druid implements Monsters {
    private int health = 200;
    private int originalHealth;
    private int defense = 15;
    private int originalDefense = defense;
    private Weapon weapon;
    private String name;
    private String type = "Druid";

    private int seeds = 0;


    public Druid(){
        weapon = new Weapon(35, "Throny branches");
        name = "Angry Oak";
    }
    public Druid(String name1, int health1, Weapon weapon1) {
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
        System.out.printf("%s lashes out at %s using %s, dealing %d points of damage\n",
                this.name, target.getName(), weapon.getName(), dmg);
        seedDmg(target);
        target.defend(attacker);
    }

    @Override
    public void defend(Monsters attacker){
        int chance = randomNum(1, 4);
        int dmg = thornDmg(originalHealth);
        if (chance == 2){
            System.out.printf("After being attacked by %s, %s's thorns extend, piercing %s and dealing %d points of damage\n",
                    attacker.getName(), this.name, attacker.getName(), dmg);
            int healthRemaining = overKill(attacker, dmg);
            attacker.setHealth(healthRemaining);
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

    public int thornDmg(int maxHealth){
        return maxHealth/10;
    }

    //this method keeps track of the seeds growth, when they reach 6 the seeds explode
    public void seedDmg(Monsters target){
        switch (seeds){
            case 0:
                System.out.println(name + "scatters some seeds onto the ground");
                seeds++;
                break;
            case 6:
                int dmg = randomNum(20, 30);
                System.out.printf("The seeds fully bloom and explode! %s receives %d points of damage!\n",
                        target.getName(), dmg);
                int healthRemaining = overKill(target, dmg);
                target.setHealth(healthRemaining);
                seeds = 0;
                break;
            default:
                System.out.println("The seeds planted by " + name + " continue to grow");
                seeds++;
                break;
        }
    }

    public int randomNum(int min, int max){
        return (int) Math.round((Math.random() * ((max - min) + 1)) + min);
    }
}
