import java.util.Random;

/**
 * This class implements the Drunkard ship.
 */
public class DrunkardShip extends SpaceShip{
    private static final int ROUND_COUNTER = 24;

    private int turnDirection;
    private int counter;
    private boolean accelerate;

    /**
     * DrunkardShip constructor.
     */
    public DrunkardShip(){
        reset();
        turnDirection = STRAIGHT_HEADING;
        accelerate = false;
        counter = 0;
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

        if(counter == 0){
            accelerate = rand.nextBoolean();
            switch (rand.nextInt(3)){
                case 0:
                    turnDirection = LEFT_TURN;
                    break;
                case 1:
                    turnDirection = RIGHT_TURN;
                    break;
                case 2:
                    turnDirection = STRAIGHT_HEADING;
                    break;
            }

            counter = ROUND_COUNTER;
        }

        if(rand.nextBoolean())
            fire(game);

        if(rand.nextBoolean())
            teleport();

        getPhysics().move(accelerate, turnDirection);
        updateGunCoolDown();
        chargeEnergy();
        counter--;
    }
}
