package hammurabi;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Console {
    Scanner scan = new Scanner(System.in);
    int currentYear = 1;
    int acres = 1000;
    int bushelsGrain = 2800;
    int bushelsToFeed;
    int bushelsHarvested = 3000;
    int acresToFarm = 1000;
    int bushelsEatenByRats = 200;
    int population = 100;
    int landValue = 19;
    int deaths = 0;
    int runningDeathTotal = 0;
    boolean plague = false;
    boolean uprising = false;
    int immigrants = 5;
    int harvestQuality = 3;

    public void playGame() {
        instructions();
        while(currentYear < 11) {
            summary();
            // USER INPUT
            askHowManyAcresToBuy();
            askHowMuchGrainToFeedPeople();
            askHowManyAcresToPlant();
            // PLAGUE
            plague();
            // STARVATION & UPRISING
            if(starvation()) break;
            // IMMIGRATION
            immigration();
            // HARVEST
            harvest();
            // RATS
            rats();
            // NEW LAND COST
            landValue = Calculations.newCostOfLand();
            currentYear++;
        }
        review();
    }

    public void instructions() {
        System.out.println("Congratulations, you are the newest ruler of ancient Sumer, \n" +
                "elected for a ten year term of office. Your duties are to dispense food, \n" +
                "direct farming, and buy and sell land as needed to support your people. \n" +
                "Watch out for rat infestations and the plague! Grain is the general currency, \n" +
                "measured in bushels. The following will help you in your decisions:\n" +
                "\n" +
                "  * Each person needs at least 20 bushels of grain per year to survive\n" +
                "  * Each person can farm at most 10 acres of land\n" +
                "  * It takes 2 bushels of grain to farm an acre of land\n" +
                "  * The market price for land fluctuates yearly\n" +
                "\n" +
                "Rule wisely and you will be showered with appreciation at the end of \n" +
                "your term. Rule poorly and you will be kicked out of office! \n");
    }

    public void summary() {

        System.out.println("O great Hammurabi!\n" +
                "You are in year " + currentYear + " of your ten year rule.");
        if (plague) System.out.println("Plague has stricken your people and HALF of them perished.");
        System.out.println("In the previous year " + deaths + " people starved to death.\n" +
                "In the previous year " + immigrants + " people entered the kingdom.\n" +
                "The population is now " + population + ".\n" +
                "We harvested " + bushelsHarvested + " bushels at " + harvestQuality + " bushels per acre.\n" +
                "Rats destroyed " + bushelsEatenByRats + " bushels, leaving " + bushelsGrain + " bushels in storage.\n" +
                "The city owns " + acres + " acres of land.\n" +
                "Land is currently worth " + landValue + " bushels per acre.");

        plague = false; // resets plague status

    }

    public void review() {

        if (uprising) {
            System.out.println("O great Hammurabi, you have failed your people. " +
                    "Too many people have starved and there has been an uprising. " +
                    "Your rule is now over." );
        } else {
            System.out.println(
                    "\nIn your ten year term, Hammurabi, " + runningDeathTotal + " of your subjects have died\n" +
                    "through plague, rats, and starvation. \n\nHowever, your kingdom persevered!\n" +
                    "\nYou leave your term with " + bushelsGrain + " bushels of grain \n" +
                    "and " + acres + " acres of land.");
        }

    }

    public void askHowManyAcresToBuy() {

        while (true) {
            int num = getNumber("How many acres of land do you wish to buy? \n");
            //if (num < 0) continue;
            if (num == 0) {
                askHowManyAcresToSell();
                break;
            } else if (num * landValue <= bushelsGrain) {
                acres += num;
                bushelsGrain -= num * landValue;
                break;
            } else {
                System.out.println("O no! You do not have enough grain for that!");
            }
        }

    }

    public void askHowManyAcresToSell() {

        while (true) {
            int num = getNumber("How many acres of land do you wish to sell? \n");
            if (num > acres) {
                System.out.println("O no! You do not have enough land for that!");
            } else {
                acres -= num;
                bushelsGrain += num * landValue;
                break;
            }
        }

    }

    public void askHowMuchGrainToFeedPeople() {

        while (true) {
            int num = getNumber("How much grain would you like to feed people? \n");
            if (num > bushelsGrain) {
                System.out.println("O no! You do not have enough grain for that!");
            } else {
                bushelsGrain -= num;
                bushelsToFeed = num;
                break;
            }
        }

    }

    public void askHowManyAcresToPlant() {

        while (true) {
            int num = getNumber("How many acres of grain do you wish to plant? \n");
            if (num > acres) {
                System.out.println("O no! You do not have enough land for that! \n");
            } else if (num > population * 10) {
                System.out.println("O no! You do not have enough people for that! \n");
            } else if (num * 2 > bushelsGrain) {
                System.out.println("O no! You do not have enough grain for that! \n");
            } else {
                acresToFarm = num;
                break;
            }
        }

    }

    public void plague() {

        int plagueDeaths = Calculations.plagueDeaths(population);
        if (plagueDeaths > 0) {
            population -= plagueDeaths;
            runningDeathTotal += plagueDeaths;
            plague = true;
        }

    }

    public boolean starvation() {

        deaths = Calculations.starvationDeaths(population, bushelsToFeed);
        if (Calculations.uprising(population,deaths)) {
            uprising = true;
            return true; // starvation will end the game
        }
        population -= deaths;
        runningDeathTotal += deaths;
        return false;

    }

    public void immigration() {

        if (deaths == 0) {
            immigrants = Calculations.immigrants(population, acres, bushelsGrain);
            population += immigrants;
        } else {
            immigrants = 0;
        }

    }

    public void harvest() {
        bushelsHarvested = Calculations.harvest(acresToFarm);
        harvestQuality = bushelsHarvested / acresToFarm;
        bushelsGrain += (harvestQuality - 2) * acresToFarm;
    }

    public void rats() {
        bushelsEatenByRats = Calculations.grainEatenByRats(bushelsGrain);
        bushelsGrain -= bushelsEatenByRats;
    }

    public int getNumber(String message) {

        while (true) {
            System.out.print(message);
            try {
                int input = scan.nextInt();
                if (input < 0) continue;
                return input;
            }
            catch (InputMismatchException e) {
                System.out.println("\"" + scan.next() + "\" isn't a number!");
            }
        }

    }

}

