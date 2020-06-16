package com.stefan.cluster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ResourceReader {

    private Document doc;

    public ResourceReader() {
        try {
            doc = parseXML("properties.xml");
        } 
        catch (Exception e) {
            System.out.println("ERROR: cannot parse XML properties file");
            doc = null;
        }
    }
    // get file from classpath, resources folder
    private InputStream getFileFromResources(String fileName) {
        System.out.println("Getting class loader...");
        ClassLoader classLoader = getClass().getClassLoader();

        System.out.println("Locating resource...");
        URL resource = classLoader.getResource(fileName);
        System.out.println("Resource URL: " + resource.getPath());
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            try {
                System.out.println("File path resolved successfully");
                return (resource.openStream());
            } catch (IOException e) {
                System.out.println("ERROR: while opening file: IO");
                return null;
            }
        }
    }

    private Document parseXML(String filename) {
        try {
            System.out.println("Creating factories and XML parsers...");
            //an instance of factory that gives a document builder  
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
            //an instance of builder to parse the specified xml file  
            DocumentBuilder db = dbf.newDocumentBuilder();  
            System.out.println("Parsing XML property configuration...");
            Document doc = db.parse(getFileFromResources(filename));  
            doc.getDocumentElement().normalize();  
            return doc;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getProperty(String propertyName, String defaultValue) {
        System.out.println("Searching for property " + propertyName);
        if (System.getenv(propertyName) != null) return System.getenv(propertyName);
        if (doc == null) return defaultValue;
        System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
        NodeList nodeList = doc.getElementsByTagName("property");  
        // nodeList is not iterable, so we are using for loop  
        for (int itr = 0; itr < nodeList.getLength(); itr++)   
        {  
            Node node = nodeList.item(itr);  
            System.out.println("\nNode Name :" + node.getNodeName());  
            if (node.getNodeType() == Node.ELEMENT_NODE)   
            {  
                Element eElement = (Element) node;
                String name = eElement.getAttribute("name");
                String value = eElement.getAttribute("value");
                if (name.equals(propertyName)) {
                    return value;
                }
            }
        }
        return defaultValue;
    }

    private static void printFile(File file) throws IOException {

        if (file == null) return;

        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)) {

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

}