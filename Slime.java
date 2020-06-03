// CSE 205
// Class ID: 1133
// Meeting date and times: T Thu 4:30pm-5:45pm
// Final Assignment 6
// Author: Rogelio A Tarin  Std Id: 1216748689
// Slime File Contents: The Slime class implements the Monsters interface and what makes the slime unique is that when attacking
// it has a chance to metabolize and power up it's weapon for some hp and if it has a miniSlime the miniSlime attacks as well.
// When being attacked it has the chance to increase it def by 5 points up to 5 times or spawn a miniSlime if it doesn't have one.
//
public class Slime implements Monsters {
    private int health;
    private int originalHealth;
    private int defense = 10;
    private int originalDefense = defense;
    private Weapon weapon;
    private String name;
    private String type = "Slime";

    private int slimeStacks = 0;
    private miniSlimes miniSlimeA = new miniSlimes();
    private boolean hasMiniSlime = false;
    private int originalWeaponDmg;


    public Slime(){
        health = 200;
        originalHealth = 200;
        weapon = new Weapon(25, "gooey gunk");
        name = "Slimey Sam";
    }

    public Slime(String name1, int health1, Weapon weapon1){
        name = name1;
        health = health1;
        originalHealth = health;
        weapon = weapon1;
        originalWeaponDmg = weapon1.getMaxDmg();
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

        System.out.printf("%s jiggles, then attacks %s with %s, dealing %d slimey points of damage\n",
                this.name, target.getName(), weapon.getName(), dmg);

        if (health - 20 > 0){
            metabolize();
        }
        if (hasMiniSlime){
            miniSlimeA.miniAttack(target);
        }
        target.defend(attacker);
    }

    @Override
    public void defend(Monsters attacker){
        int chance = randomNum(1, 4);
        if (!fullySolidified()){
            if (chance == 2) {
                System.out.printf("After being attacked by %s, %s undulates then solidifies, raising %s's defence by 5 points\n",
                        attacker.getName(), this.name, this.name);
                setDefense(defense + 5);
                slimeStacks++;
            }
        }
        if (chance == 2 && !hasMiniSlime){
            mitosis();
            System.out.println(name + " undergoes mitosis and creates mini-slime A!");
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
        weapon.setMaxDmg(originalWeaponDmg);
    }

    public boolean fullySolidified(){
        return (slimeStacks > 5);
    }

    public void mitosis(){
        hasMiniSlime = true;
    }

    //if the slime can afford it, it will lose 20 hit points to power up it's weapon
    public void metabolize(){
        int chance = randomNum(0, 8);
        if (chance == 2){
            System.out.println(name + " metabolizes, sacrificing 20 health points to boost " + weapon.getName() + "'s damage by 5 points");
            weapon.setMaxDmg(weapon.getMaxDmg()+5);
            setHealth(health-20);
        }
    }

    public int randomNum(int min, int max){
        return (int) Math.round((Math.random() * ((max - min) + 1)) + min);
    }

    private class miniSlimes{
        private String name;

        public miniSlimes(){
            name = "miniSlime A";
        }

        public void miniAttack(Monsters target){
            System.out.println(name + " quivers then rams " + target.getName() + ", dealing 5 points of damage");
            target.setHealth(target.getHealth()-5);
        }
    }
}
