package com.freespace.xmlxsdvalidate;

import org.xml.sax.helpers.DefaultHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.StringWriter;

/**
 * Created by Administrator on 2018/11/24 0024.
 */
public class XmlUtils {
    private static XmlUtils instance = null;
    public static XmlUtils getInstance(){
        if(instance==null){
            instance = new XmlUtils();
        }
        return instance;
    }

    public <T> String objToXml(T t) throws Exception{
        JAXBContext context = JAXBContext.newInstance(t.getClass());
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_ENCODING,"utf-8");
        StringWriter sw = new StringWriter();
        m.marshal(t,sw);
        return sw.toString();
    }

    public <T> boolean validate(T t, String schemaFile) throws Exception{
        JAXBContext context = JAXBContext.newInstance(t.getClass());
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema = factory.newSchema(new File(schemaFile));
        Marshaller m = context.createMarshaller();
        m.setSchema(schema);
        m.marshal(t, new DefaultHandler());
        return true;
    }

    public static void main(String[] args) {
        B2CReq bq = new B2CReq();
        bq.setMerId("s");
        bq.setOrderNo("s");
        bq.setOrderAmt("1.00");
        //bq.setOrderAmt(1.00);

        try {
           boolean s = XmlUtils.getInstance().validate(bq,"d://test.xsd");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
