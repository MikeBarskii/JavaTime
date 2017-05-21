package com.itmo.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CodeService {

    public Map<String, String> findTask(String level, int id) {
        Document doc = this.setDocument();
        XPath xpath = this.setPath();
        String task = null;

        try {
            XPathExpression expr =
                    xpath.compile("/JAVATIME/" + level.toUpperCase() + "/TASK[@id='" + id + "']");
            task = (String) expr.evaluate(doc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        if (task == null){
            throw new RuntimeException("Task with level: " + level + " and ID: " + id + " was not found");
        }

        String[] testValues = task.split("\n");

        Map<String, String> taskMap = new HashMap<>();
        taskMap.put("name", testValues[1].trim());
        taskMap.put("description", testValues[2].trim());
        taskMap.put("input", testValues[3].trim());
        taskMap.put("output", testValues[4].trim());

        return taskMap;
    }

    private Document setDocument() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        DocumentBuilder builder;
        Document doc = null;

        String fileName = "Tests.xml";
        ClassLoader classLoader = getClass().getClassLoader();

        try {
            builder = factory.newDocumentBuilder();
            File file = new File(classLoader.getResource(fileName).getFile());
            doc = builder.parse(file);
        } catch (ParserConfigurationException | SAXException | IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return doc;
    }

    private XPath setPath() {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        return xpathFactory.newXPath();
    }
}