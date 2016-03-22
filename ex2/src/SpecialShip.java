import java.util.Random;

/**
 * This class implements a Space ship with the ability to switch between different strategies.
 * Created by jenia on 13/03/2016.
 */
public class SpecialShip extends SpaceShip{

    private static final int ROUNDS_KEEP_BEHAVIOUR = 500; // Number of rounds this ship should keep the current behaviour.

    private int bahaviourCounter;
    private int behaviour;

    public SpecialShip(){
        super();

        bahaviourCounter = 0;
    }
    /**
     * switches behavioural behaviour every set number of rounds.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        Random rand = new Random();

        if(bahaviourCounter == 0) {
            rand.setSeed(rand.nextInt()); // Set random seed for the case when multiple SpecialShips are created.
            bahaviourCounter = ROUNDS_KEEP_BEHAVIOUR;
            behaviour = rand.nextInt(3); // Gets random number for behaviour selection
        }

        // This switch statement switches to the randomly chosen behaviour
        switch (behaviour){
            case 0:
                basherBehaviour(game);
                break;
            case 1:
                aggressiveBehaviour(game);
                break;
            case 2:
                runnerBehaviour(game);
                break;
        }

        bahaviourCounter--;

    }
}
