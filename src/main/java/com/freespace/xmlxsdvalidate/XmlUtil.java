package com.freespace.xmlxsdvalidate;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class XmlUtil {

    private final static int VALIDATION_FAIL = 1;
    private final static int ERROR_READING_SCHEMA = 2;
    private final static int ERROR_READING_XML = 3;

    private static String mXSDFile = "test.xsd";
    private static String mXMLFileError = "test_error.xml";
    private static String mXMLFile = "test.xml";
    public static void main(String[] args) {
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        File XMLFile = new File(mXMLFile);

        try {
            URL url = testURL(mXSDFile);

            try {
                Schema schema = factory.newSchema(url);
                Validator validator = schema.newValidator();

                Source source = new StreamSource(XMLFile);


                try {
                    validator.validate(source);
                    System.out.println(mXMLFile + " validates");
                }
                catch (SAXParseException ex) {
                    System.out.println(mXMLFile+ " fails to validate because: \n");
                    System.out.println(ex.getMessage());
                    System.out.println("At: " + ex.getLineNumber()
                            + ":" + ex.getColumnNumber());
                    System.out.println();
                    System.exit(VALIDATION_FAIL);
                }
                catch (SAXException ex) {
                    System.out.println(mXMLFile + " fails to validate because: \n");
                    System.out.println(ex.getMessage());
                    System.out.println();
                    System.exit(VALIDATION_FAIL);
                }
                catch (IOException io) {
                    System.err.println("Error reading XML source: " + mXMLFile);
                    System.err.println(io.getMessage());
                    System.exit(ERROR_READING_XML);
                }

            } catch (SAXException sch) {
                System.err.println("Error reading XML Schema: " + mXSDFile);
                System.err.println(sch.getMessage());
                System.exit(ERROR_READING_SCHEMA);
            }
        } catch (MalformedURLException e) {
            System.err.println("Error URL syntax: " + mXSDFile);
            System.err.println(e.getMessage());
            System.exit(ERROR_READING_SCHEMA);
        }

    }

    private static URL testURL(final String url) throws MalformedURLException {
        try {
            return new URL(url);
        } catch (final MalformedURLException e) { }
        return new URL("file:" + url);
    }

}