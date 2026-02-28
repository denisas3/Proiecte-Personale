package curs3.io.singleton;

public class GovernmentLazy {
    private static GovernmentLazy instance = null;

    private GovernmentLazy(){}

    public static GovernmentLazy getInstance(){
        if (instance == null) {
            instance = new GovernmentLazy();
        }
        return instance;
    }
}
