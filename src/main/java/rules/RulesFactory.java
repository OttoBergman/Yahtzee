package rules;

/**
 * Created by otto on 2016-01-04.
 */
public class RulesFactory {

    public IGameMode getGameMode() {
        return new NormalRules();
    }
}
