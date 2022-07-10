package hammurabi;

import java.util.*;
public class Calculations {
    static Random rand = new Random();

    public static Integer plagueDeaths(int population) {
        if (rand.nextInt(100) < 15) {
            return population / 2;
        }
        return 0;
    }

    public static Integer starvationDeaths(int population, int bushelsFedToPeople) {
        if (population * 20 < bushelsFedToPeople) return 0;
        return population - (bushelsFedToPeople / 20);
    }

    public static Boolean uprising(int population, int howManyPeopleStarved) {
        return (double)howManyPeopleStarved/population > 0.45;
    }

    public static Integer immigrants(int population, int acresOwned, int grainInStorage) {
        return (20 * acresOwned + grainInStorage) / (100 * population) + 1;
    }

    public static Integer harvest(int acres) {
       return acres * (rand.nextInt(6) + 1);
    }

    public static Integer grainEatenByRats(int bushels) {
        if (rand.nextInt(100) < 40) {
            return (int) (bushels * .01 * (rand.nextInt(30 - 10 + 1) + 10));
        }
        return 0;
    }

    public static Integer newCostOfLand() {
        return (rand.nextInt(23 - 17 + 1) + 17);
    }




    //git push origin Karem
}
