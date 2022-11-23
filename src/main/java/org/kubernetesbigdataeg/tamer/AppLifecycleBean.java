package org.kubernetesbigdataeg.tamer;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.kubernetesbigdataeg.tamer.bootstrap.FirstBootDatabaseInit;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

@ApplicationScoped
public class AppLifecycleBean {
    private static final Logger LOGGER = Logger.getLogger("ListenerBean");

    void onStart(@Observes StartupEvent ev) {
        System.out.println("Tamer Application starting...");

        firstBootInitializeTables();

        LOGGER.info("The application is starting...");
    }

    @Transactional
    void firstBootInitializeTables(){
        FirstBootDatabaseInit firstBootDatabaseInit = new FirstBootDatabaseInit();
        firstBootDatabaseInit.initializeTables();
    }

    void onStop(@Observes ShutdownEvent ev) {
        System.out.println("Tamer Application stopping...");
        LOGGER.info("The application is stopping...");
    }

}