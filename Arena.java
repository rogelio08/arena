// CSE 205
// Class ID: 1133
// Meeting date and times: T Thu 4:30pm-5:45pm
// Final Assignment 6
// Author: Rogelio A Tarin  Std Id: 1216748689
// Arena File Contents: The Arena class is made to house the user's monsters and have them battle if the user chooses. It contains methods
// that print the appropriate menus depending if the user wants to register a monster, find out info about the types of monsters or the arena,
// un-register a monster, print out the available fighters, or have two monsters battle.
//
import java.util.Scanner;
public class Arena {
    private Monsters[] contestants = new Monsters[6];
    private int fighterNumber = 0;
    Scanner scan = new Scanner(System.in);


    public Arena(){
        fighterNumber = 0;
    }

    public boolean moreThan1(){
        return (fighterNumber > 0);
    }

    public void register(Monsters fighter){
        contestants[fighterNumber] = fighter;
        fighterNumber++;
    }

    public boolean isEmpty(){
        return (fighterNumber <= 1);
    }

    public boolean overCapacity() {
        return (fighterNumber >= 6);
    }

    //this method recursively calls itself having fighter 1 and 2 attack each other until one of them runs out of health
    public void battle(Monsters fighter1, Monsters fighter2){
        if (!fighter1.isAlive() || !fighter2.isAlive()){
            announceWinner(fighter1, fighter2);
            return;
        }

        fighter1.attack(fighter2, fighter1);
        System.out.printf("%s has %d health points left\n",
                fighter2.getName(), fighter2.getHealth());
        System.out.println();

        fighter2.attack(fighter1, fighter2);
        System.out.printf("%s has %d health points left\n",
                fighter1.getName(), fighter1.getHealth());

        System.out.println("----------------------------------------");
        battle(fighter1, fighter2);
    }

    public void announceWinner(Monsters monsters1, Monsters monsters2){
        if(monsters1.isAlive()){
            System.out.println("Congratulations " + monsters1.getName() + " you won the battle");
        }
        else {
            System.out.println("Congratulations " + monsters2.getName() + " you won the battle");
        }
        monsters1.reset();
        monsters2.reset();
        System.out.println();
        printMainMenu();
    }

    public void printMainMenu(){
        System.out.println("Welcome to Morrie's monster pit!\n"
            + "Please choose one on the following options:\n"
            + "a. register a fighter\n"
            + "b. begin a battle\n"
            + "c. find out more about the program\n"
            + "d. unregister a fighter\n"
            + "e. show registered fighters\n"
            + "q. exit the program\n");
        userAction(scan.next().charAt(0));
    }

    public void userAction(char choice){
        switch (choice){
            case 'a':
                fighterMenu();
                break;
            case 'b':
                battleMenu();
                break;
            case 'c':
                printArenaInfo();
                break;
            case 'd':
                fighterToRemove();
                break;
            case 'e':
                printOutFighters();
                printMainMenu();
                break;
            case 'q':
                System.out.println("Thanks for participating in the arena!");
                break;
            default:
                System.out.println("That is not a recognizable option, please choose another one");
                printMainMenu();
                break;

        }
    }

    public void fighterMenu(){
        if(overCapacity()){
            System.out.println("The arena has reached maximum capacity, please remove one or more fighters to register another");
            printMainMenu();
        }
        System.out.println("Please select one of the monsters to register or pick ? to learn more about a given monster:\n"
            + "a. Slime\n"
            + "b. Vampire\n"
            + "c. Druid\n"
            + "d. Robot\n"
            + "?. More information\n");
        char choice = scan.next().charAt(0);
        if (choice == '?'){
            moreMonsterInfo();
        }
        registeringMonster(choice);
    }

    public void battleMenu(){
        if(isEmpty()){
            System.out.println("You currently do not have enough fighters to battle, please register more");
            printMainMenu();
        }
        printOutFighters();
        System.out.println("Please enter the number of the first fighter you would like to have battle");
        int monster1 = scan.nextInt()-1;
        System.out.println("Please enter the number of the second fighter you would like to have battle");
        int monster2 = scan.nextInt()-1;
        if (monster1 == monster2){
            System.out.println("Having a monster fight itself is against the rules. Please select two different monsters");
            battleMenu();
        }
        battle(contestants[monster1], contestants[monster2]);
    }

    public void printArenaInfo(){
        System.out.println("The monster pit is a program designed to allow users to register up to 6 specific monsters as fighters and create battles between said monsters.\n"
            + "Note that the options are case sensitive so be sure to enter the exact character of the option you wish to pick, and be aware of whether you are being asked for a number\n"
            + "or a string during registration. Entering the wrong value will result in an error. Each monster has a base defense that can be found under their information\n"
            + "but the user is allowed to choose their health, name, and weapon. Each monster is guaranteed to know how to attack another monster and how to defend when it is \n"
            + "being attacked, with each monster having different variations depending on their type. To find out the true potential of each monster look up their information\n"
            + "under registration. After 2 or more monsters have been registered the user can have two monsters of their choice battle, where a coin flip determines which monster will attack first\n"
            + "after the battle the winner's name will be announced and the user will be returned to the main menu again. For the sake of having a reference point assume 350 is above average\n"
            + "health while 300 is average. 45 is a good average for weapon damage as well");
        System.out.println();
        printMainMenu();
    }

    public void printOutFighters(){
        if(!moreThan1()){
            System.out.println("There are currently no fighters registered");
            printMainMenu();
        }
        System.out.println("There are currently " + fighterNumber + " available fighters. They are as follows:");
        for (int i = 0; i < fighterNumber; i++){
            System.out.println("Fighter number: " + (i+1) + " " + contestants[i].getName() + " the " + contestants[i].getType() + ", max health " + contestants[i].getHealth());
        }
    }

    public void fighterToRemove(){
        if(!moreThan1()){
            System.out.println("There are currently no fighters to remove, please register more");
            printMainMenu();
        }
        printOutFighters();
        System.out.println("Please enter the number of the fighter you would like to have removed");
        int monster1 = scan.nextInt()-1;
        remove(monster1);
    }

    public void remove(int index){
        Monsters [] newArray = new Monsters[5];
        for (int x = 0; x < index; x++){
            newArray[x] = contestants[x];
        }
        for (int y = index; y < fighterNumber-1; y++){
            newArray[y] = contestants[y+1];
        }
        fighterNumber--;
        contestants = newArray;
        printMainMenu();
    }

    public void printSlimeInfo(){
        System.out.println("Slimes: While normally looking down upon for being so common, slimes are the backbone of most dungeons\n"
            + "and should be respected. In this arena slimes are known for their enormous health points but meager defenses. To compensate that,\n"
            + "slimes have the chance to solidify every time they are attacked and increase their defenses by 5 points up to 5 times in a a given battle.\n"
            + "Slimes are also known to multiple so don't be surprised if cutting a slime in half results in two slimes. Lastly slimes can metabolize\n"
            + "and burn some of their hp in order to power up their weapon.\n");
        moreMonsterInfo();
    }

    public void printVampireInfo(){
        System.out.println("Vampires: As the rulers of the night vampires are known and feared for their need to feed off the blood of the living\n"
            + "as well as dabbling in the dark arts. As such vampires in this arena have the innate ability to drain health from their victims with\n"
            + "their weapons and when being attacked may unleash a curse upon their attacker, weakening their defenses by 15 points.\n");
        moreMonsterInfo();
    }

    public void printDruidInfo(){
        System.out.println("Druids: Although normally gentle creatures, druids are still guardians of the forest and will not hesitate to utilize\n"
            + "to use their powers to protect their home. For example druids will spread seeds when attacking, be sure to keep track of said seeds\n"
            + "because before you know it they'll bloom and it will surely be your doom. Take care when attacking because as every rose has its thorn\n"
            + "those who attack the druid may receive damage from said thorns based on the druids max health");
        moreMonsterInfo();
    }

    public void printRobotInfo(){
        System.out.println("Robots: Highly intelligent killing machines, robots are some of the most frightful monsters in the arena. They may start off\n"
            + "normally but every time they are attacked they harness energy until they are eventually able to activate their deadly kill mode. While in\n"
            + "kill mode robots gain access to their deadliest weapons including: flamethrowers, gatling guns, electric shocks, and even a particle cannon!\n"
            + "They may also choose to repair themselves. Worry not for 6 attacks are required to enter this mode that will only last 3 turns but it will be\n"
            + "a frightening 3 turns.");
        moreMonsterInfo();
    }

    public void moreMonsterInfo(){
        System.out.println("Please select the monster you would like to know more about:\n"
                + "a. Slime\n"
                + "b. Vampire\n"
                + "c. Druid\n"
                + "d. Robot\n"
                + "e. return me to previous menu\n");
        selectingMonsterInfo(scan.next().charAt(0));
    }

    public void selectingMonsterInfo(char choice){
        switch (choice){
            case 'a':
                printSlimeInfo();
                break;
            case 'b':
                printVampireInfo();
                break;
            case 'c':
                printDruidInfo();
                break;
            case 'd':
                printRobotInfo();
                break;
            case 'e':
                fighterMenu();
                break;
        }
    }

    public void registeringMonster(char choice){
        if(overCapacity()){
            System.out.println("You currently have all 6 slots filled, please remove a fighter before attempting to add more");
            printMainMenu();
        }
        switch (choice){
            case 'a':
                createSlime();
                printMainMenu();
                break;
            case 'b':
                createVampire();
                printMainMenu();
                break;
            case 'c':
                createDruid();
                printMainMenu();
                break;
            case 'd':
                createRobot();
                printMainMenu();
                break;
            default:
                System.out.println("I'm sorry, that option isn't available. Please choose another");
                printMainMenu();
        }
    }

    public void createSlime(){
        System.out.println("To register a slime please begin by entering it's name (no spaces please):");
        String slimeName = scan.next();
        System.out.println("Next enter it's health, make sure to be generous with your choice");
        int slimeHealth = scan.nextInt();
        System.out.println("Lastly your slime needs a cool, albeit gooey, weapon. Start by entering the name (no spaces please):");
        String weaponName = scan.next();
        System.out.println("Finish it off by entering in the average amount of damage " + weaponName + " will do");
        int weaponDmg = scan.nextInt();
        Weapon slimeWeapon = new Weapon(weaponDmg, weaponName);
        Slime newSlime = new Slime(slimeName, slimeHealth, slimeWeapon);
        register(newSlime);
    }

    public void createVampire(){
        System.out.println("To register a vampire please begin by entering it's name (no spaces please):");
        String vampireName = scan.next();
        System.out.println("Next enter it's health, try to choose an average amount of health");
        int vampireHealth = scan.nextInt();
        System.out.println("Lastly your vampire needs a cool and frightening weapon. Start by entering the name (no spaces please):");
        String weaponName = scan.next();
        System.out.println("Finish it off by entering in the average amount of damage " + weaponName + " will do. Remember your vampire will heal based on the damage it does");
        int weaponDmg = scan.nextInt();
        Weapon vampireWeapon = new Weapon(weaponDmg, weaponName);
        Vampire newVampire = new Vampire(vampireName, vampireHealth, vampireWeapon);
        register(newVampire);
    }

    public void createDruid() {
        System.out.println("To register a druid please begin by entering it's name (no spaces please):");
        String druidName = scan.next();
        System.out.println("Next enter it's health, try to choose an average amount of health. Remember the damage cause by the thorns will be based off of this number");
        int druidHealth = scan.nextInt();
        System.out.println("Lastly your druid needs a cool yet natural weapon. Start by entering the name (no spaces please):");
        String weaponName = scan.next();
        System.out.println("Finish it off by entering in the average amount of damage " + weaponName + " will do.");
        int weaponDmg = scan.nextInt();
        Weapon weapon1 = new Weapon(weaponDmg, weaponName);
        Druid newDruid = new Druid(druidName, druidHealth, weapon1);
        register(newDruid);
    }

    public void createRobot() {
        System.out.println("To register a robot please begin by entering it's name (no spaces please):");
        String robotName = scan.next();
        System.out.println("Next enter it's health, try to choose an average amount of health. Remember it takes 6 attacks to activate kill mode");
        int robotHealth = scan.nextInt();
        System.out.println("Lastly your robot needs a cool yet high tech weapon. Start by entering the name (no spaces please):");
        String weaponName = scan.next();
        System.out.println("Finish it off by entering in the average amount of damage " + weaponName + " will do.");
        int weaponDmg = scan.nextInt();
        Weapon weapon1 = new Weapon(weaponDmg, weaponName);
        Robot newRobot = new Robot(robotName, robotHealth, weapon1);
        register(newRobot);
    }
}
