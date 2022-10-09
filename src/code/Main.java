package code;

import java.util.logging.Logger;

import static code.chapter11.Util.compareSyncMethodWithParallelMethodAndAsyncMethod;

public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
//        compareSyncMethodWithAsyncMethod();
        compareSyncMethodWithParallelMethodAndAsyncMethod();

    }


}