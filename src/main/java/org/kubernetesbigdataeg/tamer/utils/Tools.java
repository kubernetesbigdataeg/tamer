package org.kubernetesbigdataeg.tamer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import io.vertx.ext.web.handler.sockjs.impl.StringEscapeUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Tools {
    public static String ThrowableToJson(Throwable throwable){
        try {
            ArrayList<String> classViolations = new ArrayList<String>();
            StackTraceElement elements[] = throwable.getStackTrace();
            for (int i = 0, n = 5; i < n; i++) {
                String a = elements[i].toString();
                classViolations.add(a);
            }
            classViolations.add("...");
            return String.format("{\"exception\": %s, \"propertyViolations\": [], \"classViolations\": %s, \"parameterViolations\": [], \"returnValueViolations\": []}", new ObjectMapper().writeValueAsString(throwable.getMessage()), new ObjectMapper().writeValueAsString(classViolations));
        } catch (JsonProcessingException e) {
            return e.toString();
        }
    }

    public static Map<String,String> sanitizeMap(Map<String,String> inputMap) {
        Map<String,String> outputMap = new HashMap<>();
        for (Map.Entry<String,String> entry: inputMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            try {
                key = StringEscapeUtils.escapeJava(key);
            } catch (Exception e) {}
            try {
                value = StringEscapeUtils.escapeJava(value);
            } catch (Exception e) {}
            outputMap.put(key, value);
        }
        return outputMap;
    }

    /*
    Read a YAML from the internal JVM "resources" folder.
   */
    public static List<Object> convertYamlToJson(String yaml) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(yaml);

        //File file = new File(classLoader.getResource(yaml).toExternalForm());
        //System.out.println(file.getPath());

        YAMLFactory yamlFactory = new YAMLFactory();
        ObjectMapper mapper = new ObjectMapper();

        YAMLParser yamlParser = yamlFactory.createParser(inputStream);
        List<Object> docs = mapper.readValues(yamlParser, Object.class).readAll();

        return docs;
    }

    public static List<String> findDuplicatesInList(List<String> listContainingDuplicates) {
        final List<String> duplicatesSet = new ArrayList<>();
        final Set<String> validateSet = new HashSet<String>();
        for (String elem : listContainingDuplicates) {
            if (!validateSet.add(elem)) {
                duplicatesSet.add(elem);
            }
        }
        return duplicatesSet;
    }
}
