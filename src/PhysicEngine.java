import java.util.ArrayList;

public class PhysicEngine implements Engine {
    private ArrayList<DynamicSprite> movingSpriteList;
    private ArrayList<Sprite> environment;
    private ArrayList<TrapSprite> traps;

    public PhysicEngine() {
        movingSpriteList = new ArrayList<>();
        environment = new ArrayList<>();
        traps = new ArrayList<>();
    }

    public void addToEnvironmentList(Sprite sprite) {
        if (!environment.contains(sprite)) {
            environment.add(sprite);
        }
    }

    public void setEnvironment(ArrayList<Sprite> environment) {
        this.environment = environment;
    }

    public void addToMovingSpriteList(DynamicSprite sprite) {
        if (!movingSpriteList.contains(sprite)) {
            movingSpriteList.add(sprite);
        }
    }

    public void addTrap(TrapSprite trap) {
        if (!traps.contains(trap)) {
            traps.add(trap);
        }
    }

    @Override
    public void update() {
        if (Main.currentState == GameState.PLAYING) {
            for (DynamicSprite sprite : movingSpriteList) {
                sprite.moveIfPossible(environment);


                for (TrapSprite trap : traps) {
                    trap.applyDamageIfColliding(sprite);
                }
            }
        }
    }
}