package com.jiayusoft.mobile.kenli.utils.webservice;

import android.util.Log;
import android.util.Xml;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

/**
 * Created by ASUS on 2014/4/23.
 */
public class WebServiceUtil {
    public static String buildXml(String message){
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?><root><request>" +
                message +
                "</request></root>";
    }
    public static String buildXml(String methodName, String message){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><root><request>")
                .append(message)
                .append("</request></root>");
        return stringBuilder.toString();
    }
    public static String connectToWebService(String SERVICE_NS, String SERVICE_URL, String methodName, String xmlString) throws IOException, XmlPullParserException {
//        String SERVICE_NS = Init.SERVICE_NS;
//        String SERVICE_URL = Init.SERVICE_URL;
////        String SERVICE_URL = "http://221.1.64.214:8082/HealthAssistant/webservice/healthAssistant?wsdl";
//        String methodName = Init.methodName;
        Log.e("Tag", xmlString);
        try {
            SoapObject request=new SoapObject(SERVICE_NS, methodName);
//            request.addProperty("code", "2");
//            request.addProperty("xmlString",xmlString);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(SERVICE_URL);
            transport.debug = true;
            envelope.bodyOut = request;

            transport.call(null, envelope);

            SoapObject response = (SoapObject) envelope.bodyIn;
            String returnString = response.getPropertyAsString("return");
            if (returnString == null) {
                //TODO:
            } else {
                //TODO:
            }
        } catch (Exception e) {
            e.printStackTrace();
            //TODO:
        }

        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject request=new SoapObject(SERVICE_NS, methodName);
        request.addProperty("xmlString",xmlString);
        envelope.bodyOut=request;
        (new MarshalBase64()).register(envelope);
        HttpTransportSE transport=new HttpTransportSE(SERVICE_URL);
        transport.call(null, envelope);
        if(envelope.getResponse()!=null){
            SoapObject result = (SoapObject) envelope.bodyIn;
            String responseString = result.getProperty(0).toString();
            Log.e("TAG", responseString);
            return responseString;
        }
        return null;
    }
    public static String getErrorMsgFromXml(String xmlStr) throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(new StringReader(xmlStr));
//            parser.setInput(new StringReader("<?xml version='1.0' encoding='utf-8'?><Body><Response ErrMsg=''><D_GRDABH>371625060160000204</D_GRDABH></Response></Body>"));
        int event = parser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("Response")) {
                        return parser.getAttributeValue(null, "ErrMsg");
                    }
                    break;
                case XmlPullParser.TEXT:
                    break;
                case XmlPullParser.END_TAG:
                    break;
                default:
                    break;
            }
            event = parser.next();
        }
        return null;
    }

    public static HashMap<String,String> getErrorAndGsonFromXml(String xmlStr){
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(new StringReader(xmlStr));
//            parser.setInput(new StringReader("<?xml version='1.0' encoding='utf-8'?><Body><Response ErrMsg=''><D_GRDABH>371625060160000204</D_GRDABH></Response></Body>"));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("Response")) {
                            HashMap<String,String> result = new HashMap<String, String>();
                            result.put("gsonString",parser.nextText());
                            result.put("errMsg",parser.getAttributeValue(null, "ErrMsg"));
                            return result;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                event = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
