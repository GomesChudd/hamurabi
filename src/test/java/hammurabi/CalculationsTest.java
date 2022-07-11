package hammurabi;

import static org.junit.Assert.*;

import org.junit.Test;

public class CalculationsTest {
    
    boolean about(double expected, double actual) {
        return actual > 0.90 * expected && actual < 1.10 * expected;
    }

    @Test
    public final void testPlagueDeaths1() {
        int number_of_plagues = 0;
        for (int i = 0; i < 10000; i++) {
            int deaths = Calculations.plagueDeaths(100);
            if (deaths > 0) {
                number_of_plagues += 1;
            }
        }
        int percentPlagues = number_of_plagues / 100;
        assertTrue("Number of plagues is about " + percentPlagues + ", not about 15%.",
                   about(1500, number_of_plagues));
    }

    @Test
    public final void testPlagueDeaths2() {
        int deaths = 0;
        for (int i = 0; i < 10000; i++) {
            deaths = Calculations.plagueDeaths(100);
            if (deaths > 0) break;
        }
        assertEquals("In a plague, " + deaths + "% of your people die, not 50%.",
                     50, deaths);
    }
    
    @Test
    public final void testStarvationDeaths() {
        int deaths = Calculations.starvationDeaths(100, 1639);
        assertEquals("Wrong number of starvation deaths.", 19, deaths);
        deaths = Calculations.starvationDeaths(100, 2500);
        if (deaths < 0) {
            fail("You starved a negative number of people!");
        }
    }

    @Test
    public final void testUprising() {
        assertTrue("Should have had an uprising!", Calculations.uprising(1000, 451));
        assertFalse("Should not have had an uprising!", Calculations.uprising(1000, 449));
    }

    @Test
    public final void testImmigrants() {
        int imm = Calculations.immigrants(10, 1200, 500);
        assertEquals("Wrong number of immigrants.", 25, imm);
    }

    @Test
    public final void testHarvest() {
        int[] yield = new int[7];
        for (int i = 0; i < 1000; i++) {
            int harvest = Calculations.harvest(1);
            assertTrue("Illegal harvest per acre: " + harvest, harvest > 0 && harvest <= 6);
            yield[harvest] += 1;
        }
        for (int j = 1; j <= 6; j++) {
            assertTrue("You never have a yield of " + j + " bushels per acre.", yield[j] > 0);
        }
    }

    @Test
    public final void testGrainEatenByRats1() {
        int infestations = 0;
        for (int i = 0; i < 1000; i++) {
            int eaten = Calculations.grainEatenByRats(100);
            if (eaten > 0) {
                infestations += 1;
            }
        }
        int percentInfestations = infestations / 100;
        assertTrue("Number of rat infestations is about " + percentInfestations + 
                   ", not about 40%.", about(400, infestations));
    }

    @Test
    public final void testGrainEatenByRats2() {
        int percent = 0;
        int[] counts = new int[31];
        for (int i = 0; i < 10000; i++) {
            percent = Calculations.grainEatenByRats(100);
            if (percent == 0) continue;
            counts[percent] += 1;
            assertTrue("Rats ate " + percent + "% of your grain, not 10% to 30%.",
                       percent >= 10 && percent <= 30);
        }
        for (int j = 11; j < 30; j++) {
            assertTrue("Rats never ate " + j + "% of your grain.", counts[j] > 0);
        }
    }

    @Test
    public final void testNewCostOfLand() {
        int[] cost = new int[24];
        for (int i = 0; i < 1000; i++) {
            int price = Calculations.newCostOfLand();
            assertTrue("Illegal cost of land: " + price, price >= 17 && price <= 23);
            cost[price] += 1;
        }
        for (int j = 17; j <= 23; j++) {
            assertTrue("You never have a land cost of " + j + " bushels per acre.", cost[j] > 0);
        }
    }

}

