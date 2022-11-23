package org.kubernetesbigdataeg.tamer.bootstrap;

import org.jboss.logging.Logger;
import org.kubernetesbigdataeg.tamer.entities.catalog.Catalog;
import org.kubernetesbigdataeg.tamer.entities.catalog.CatalogRepository;
import org.kubernetesbigdataeg.tamer.entities.user.User;
import org.kubernetesbigdataeg.tamer.entities.user.UsersRepository;
import org.kubernetesbigdataeg.tamer.entities.userRole.UserRole;
import org.kubernetesbigdataeg.tamer.entities.userRole.UserRoleRepository;

import java.util.Enumeration;
import java.util.Properties;

public class FirstBootDatabaseInit {
    private static final Logger LOGGER = Logger.getLogger("ListenerBean");
    LoadInitTablesCustomProperties customProperties;

    public FirstBootDatabaseInit() {
        customProperties = new LoadInitTablesCustomProperties();
    }

    public void initializeTables() {
        initializeUsersTable();
        initializeUsersRoleTable();
        initializeCatalogTable();
    }

    private void initializeUsersTable(){
        new Properties();
        Properties p;
        p = customProperties.getInitValuesUsersTable();

        UsersRepository usersRepository = new UsersRepository();

        if (usersRepository.count() == 0) {
            LOGGER.info("Initializing Table -> Users");
            Enumeration<?> e = p.propertyNames();

            while (e.hasMoreElements()) {
                User users = new User();
                String key = (String) e.nextElement();
                String[] parts = p.getProperty(key).split(",");
                users.setUsername(parts[0]);
                users.setPassword(parts[1]);
                users.addRole(parts[2]);
                users.persist();
            }
        } else {
            LOGGER.info("Table -> Users already initializated");
        }
    }

    private void initializeUsersRoleTable(){
        new Properties();
        Properties p;
        p = customProperties.getInitValuesUsersRoleTable();

        UserRoleRepository usersRoleRepository = new UserRoleRepository();

        if (usersRoleRepository.count() == 0) {
            LOGGER.info("Initializing Table -> Users Role");
            Enumeration<?> e = p.propertyNames();

            while (e.hasMoreElements()) {
                UserRole userRole = new UserRole();
                String key = (String) e.nextElement();
                String[] parts = p.getProperty(key).split(",");
                userRole.setName(parts[0]);
                userRole.setDesc(parts[1]);
                userRole.persist();
            }
        } else {
            LOGGER.info("Table -> Users Role already initializated");
        }
    }

    private void initializeCatalogTable(){
        new Properties();
        Properties p;
        p = customProperties.getInitValuesCatalogTable();

        CatalogRepository catalogRepository = new CatalogRepository();

        if (catalogRepository.count() == 0) {
            LOGGER.info("Initializing Table -> Layers");
            Enumeration<?> e = p.propertyNames();

            while (e.hasMoreElements()) {
                Catalog catalog = new Catalog();

                String key = (String) e.nextElement();
                String[] parts = p.getProperty(key).split(",");
                catalog.setName(parts[0]);
                catalog.setDesc(parts[1]);
                catalog.setManifest(parts[2]);
                catalog.persist();
            }
        } else {
            LOGGER.info("Table -> Catalog already initializated");
        }
    }
}
