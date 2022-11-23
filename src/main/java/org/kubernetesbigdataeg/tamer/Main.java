package org.kubernetesbigdataeg.tamer;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main {
    public static void main(String... args) {
        Quarkus.run(MyApp.class, args);
    }

    public static class MyApp implements QuarkusApplication {
        @Override
        public int run(String... args) throws Exception {
            System.out.println("Tamer Application bootstrapping ...");
            /*
              This method will wait until a shutdown is requested
              (either from an external signal like when you press
              Ctrl+C or because a thread has called Quarkus.asyncExit().
             */
            Quarkus.waitForExit();
            return 0;
        }
    }
}