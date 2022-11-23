package org.kubernetesbigdataeg.tamer.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class LoadInitTablesCustomProperties {
    private Properties properties;

    public LoadInitTablesCustomProperties() {
        properties = new Properties();
        properties = loadPropertiesFile();
    }

    // This method is used to load the properties file
    private Properties loadPropertiesFile(){
        InputStream iStream = null;
        try {
            // Loading properties file from resources internal folder
            iStream = getClass().getClassLoader().getResourceAsStream("inittables.properties");
            properties.load(iStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(iStream != null){
                    iStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return properties;
    }

    public Properties getInitValuesUsersTable() {
        Properties typesProperties = new Properties();
        Enumeration<?> e = properties.propertyNames();

        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            if (key.startsWith("users.init")) {
                typesProperties.setProperty(key, properties.getProperty(key));
            }
        }
        return typesProperties;
    }

    public Properties getInitValuesUsersRoleTable() {
        Properties usersRoleProperties = new Properties();
        Enumeration<?> e = properties.propertyNames();

        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            if (key.startsWith("user_roles.init")) {
                usersRoleProperties.setProperty(key, properties.getProperty(key));
            }
        }
        return usersRoleProperties;
    }

    public Properties getInitValuesCatalogTable() {
        Properties servicesProperties = new Properties();
        Enumeration<?> e = properties.propertyNames();

        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            if (key.startsWith("catalog.init")) {
                servicesProperties.setProperty(key, properties.getProperty(key));
            }
        }
        return servicesProperties;
    }

    public void printProperties() {
        Enumeration<?> e = properties.propertyNames();

        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            System.out.println(key + " -- " + properties.getProperty(key));
        }
    }
}
