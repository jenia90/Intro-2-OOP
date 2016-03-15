import java.util.Random;

/**
 * This class represents a Space ship with a drunk driver.
 * Created by jenia on 13/03/2016.
 */
public class DrunkardShip extends SpaceShip{

    public DrunkardShip(){
        reset();
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

    }
}
