import oop.ex2.SpaceShipPhysics;

import java.util.Random;

/**
 * This class implements the Drunkard ship.
 */
public class DrunkardShip extends SpaceShip{

    private static final int ROUND_COUNTER = 18;

    private int turnDirection;
    private int counter;
    private boolean accelerate;
    private boolean shoot;
    private boolean teleport;
    private boolean pursuit;

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        Random rand = new Random();
        rand.setSeed(rand.nextInt()); // Set random seed for the case when multiple DrunkardShips are created.
        shieldControl(false);

        if(teleport)
            teleport();

        if(counter == 0){
            accelerate = rand.nextBoolean();
            shoot = rand.nextBoolean();
            teleport = rand.nextBoolean();
            pursuit = rand.nextBoolean();

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

        if(pursuit) {
            SpaceShip closest = game.getClosestShipTo(this); // gets the closest ship
            SpaceShipPhysics physics = getPhysics();
            double angle = getPhysics().angleTo(closest.getPhysics()); // gets the angle to the closest ship
            shieldControl(true);

            pursuitShip(physics, angle);
        } else
            getPhysics().move(accelerate, turnDirection);

        if(shoot)
            fire(game);

        updateGunCoolDown();
        chargeEnergy();
        counter--;
    }
}
