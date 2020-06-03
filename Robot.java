// CSE 205
// Class ID: 1133
// Meeting date and times: T Thu 4:30pm-5:45pm
// Final Assignment 6
// Author: Rogelio A Tarin  Std Id: 1216748689
// Robot File Contents: The Robot class implements the Monsters interface and what makes the robot unique is that whenever
// it is attacked it charges up, when it reaches max charge it switches to a stronger form where it's attacked it replaced with
// one of five options that can be found in the killBotActions method. After a few turns it returns to it's normal form where
// it will need to charge again.
//
public class Robot implements Monsters {
    private int health;
    private int originalHealth;
    private int defense = 20;
    private int originalDefense = defense;
    private Weapon weapon;
    private String name;
    private String type = "Robot";

    private int chargeStacks;
    private boolean killMode;
    private int killBotTurns;


    public Robot(){
        health = 100;
        originalHealth = health;
        weapon = new Weapon(50, "blaster");
        name = "Kill Bot-9000";
    }

    public Robot(String name1, int health1, Weapon weapon1){
        name = name1;
        health = health1;
        originalHealth = health;
        weapon = weapon1;
        chargeStacks = 0;
        killMode = false;
        killBotTurns = 0;
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

        if (killMode){
            killBotActions(target);
            target.defend(attacker);
            return;
        }

        int dmg = (weapon.getMaxDmg() - target.getDefense());
        int remainingHealth = overKill(target, dmg);
        target.setHealth((remainingHealth));
        System.out.printf("%s preforms a quick calculation, then attacks %s with %s, dealing %d points of damage\n",
                this.name, target.getName(), weapon.getName(), dmg);
        target.defend(attacker);
    }

    @Override
    public void defend(Monsters attacker){
        if (!killMode) {
            chargeStacks++;
            System.out.println(name + " is gathering energy. " + (6 - chargeStacks) + " turns left until something terrible happens");
            if (chargeStacks == 6) {
                activateKillMode();
            }
        }
        else {
            return;
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

    public void activateKillMode(){
        System.out.println("Watch out! " + name + " has activated kill mode and is ready for bloodshed!");
        killMode = true;
        killBotTurns = 4;
    }

    public void deactivateKillMode(){
        if (killBotTurns <= 0){
            System.out.println(name + " has exhausted it's batteries and has switched to low-power mode, deactivating kill mode");
            killMode = false;
            chargeStacks = 0;
        }
    }

    //While it looks complicated this method really just randomly chooses one of five options using a switch statement then decreases the number of killBot turns
    public void killBotActions(Monsters target){
        int chance = randomNum(0,5);
        if (chance == 3 && (target.getHealth() < 50)){
            chance = 2;
        }
        switch (chance){
            case 1:
                System.out.println(name + " transforms their arm into a flame-thrower and incinerates " + target.getName() + " dealing 30 points of damage");
                target.setHealth(target.getHealth() - 30);
                killBotTurns--;
                deactivateKillMode();
                break;
            case 2:
                int dmg1 = randomNum(10, 20);
                int dmg2 = randomNum(10, 20);
                int dmg3 = randomNum(10, 30);
                System.out.println(name + " reveals a gatling gun, dealing "+ dmg1 + " , + " + dmg2 + " , + "+ dmg3 + " points of damage to poor " + target.getName());
                killBotTurns--;
                deactivateKillMode();
                break;
            case 3:
                System.out.println(name + " unleashes its most powerful weapon and fires a particle cannon, reducing " + target.getName() + "'s health by half!");
                target.setHealth(target.getHealth()/2);
                killBotTurns -= 2;
                deactivateKillMode();
                break;
            case 4:
                System.out.println(name + " lets loose a powerful electric shock, damaging " + target.getName() + " for 45 points!");
                target.setHealth(target.getHealth() - 45);
                System.out.println(name + " receives some recoil and loses 5 defense points");
                setDefense(getDefense()-5);
                deactivateKillMode();
                killBotTurns--;
                break;
            case 5:
                System.out.println(name + " preforms some self-repairs and recovers 25 health points");
                setHealth(health+25);
                deactivateKillMode();
                killBotTurns--;
                break;
            default:
                System.out.println(name + " watches " + target.getName() + " closely");
                break;
        }
    }

    public int randomNum(int min, int max){
        return (int) Math.round((Math.random() * ((max - min) + 1)) + min);
    }
}
