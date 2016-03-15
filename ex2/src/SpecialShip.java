import java.util.Random;

/**
 * This class represents a Space ship with the ability to switch between different strategies.
 * Created by jenia on 13/03/2016.
 */
public class SpecialShip extends SpaceShip{

    private static final int ROUNDS_KEEP_STRATEGY = 500; // Number of rounds this ship should keep the current strategy.

    private int strategyCounter;
    private int strategy;

    public SpecialShip(){
        reset();
        strategyCounter = 0;
    }
    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        Random rand = new Random();

        if(strategyCounter == 0) {
            strategyCounter = ROUNDS_KEEP_STRATEGY;
            strategy = rand.nextInt(2); // Gets random number for strategy selection
        }

        // This switch statement
        switch (strategy){
            case 0:
                basherStrategy(game);
                break;
            case 1:
                aggressiveStrategy(game);
                break;
            case 2:
                runnerStrategy(game);
                break;
        }

        strategyCounter--;
    }
}
