
package com.freespace.xmlxsdvalidate;

import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class XsdValidateXmlUtil {

    private static final String SCHEMALANG = "http://www.w3.org/2001/XMLSchema";

    /**
     * Schema校验xml文件
     * @param xmlPath xml字符串
     * @param xsdPath xsd文件路径
     * @return xml文件是否符合xsd定义的规则
     */
    public static boolean xmlStringValidate(String xmlStr, String xsdPath) {
        boolean flag = false;
        try {
            SchemaFactory factory = SchemaFactory.newInstance(SCHEMALANG);
            File schemaLocation = new File(xsdPath);
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            InputStream is = new ByteArrayInputStream(xmlStr.getBytes());
            Source source = new StreamSource(is);
            try {
                validator.validate(source);
                flag = true;
            } catch (SAXException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * Schema校验xml文件
     * @param xmlPath xml文件路径
     * @param xsdPath xsd文件路径
     * @return xml文件是否符合xsd定义的规则
     */
    public static boolean xmlFileValidate(String xmlPath, String xsdPath) {
        boolean flag = false;
        try {
            SchemaFactory factory = SchemaFactory.newInstance(SCHEMALANG);
            File schemaLocation = new File(xsdPath);
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(xmlPath);

            try {
                validator.validate(source);
                flag = true;
            } catch (SAXException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void main(String[] args) {
        xmlStringValidate("d://workspace.xml", "d://test.xsd");
//        xmlStringValidate("d://test.xml", "d://test.xsd");
    }
}
