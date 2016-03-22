/**
 * This class implements the Basher space ship.
 */
public class BasherShip extends SpaceShip{

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        basherBehaviour(game);
    }
}
