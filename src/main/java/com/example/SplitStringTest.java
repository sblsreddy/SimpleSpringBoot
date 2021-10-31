package sunlife.us.dc.messagecenter.plugin.gcsdocgengateway.sample.processor;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.beans.XMLDecoder;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SplitStringTest {
    String completeString = "1.0~96449~[EventResponse]";
    //String alpha = "{policy=[[218445,865231]], party=[115], doctype=Claim Approval Letter, user=null, caseNumber=[NTN-19-DI-01]}";

    private static String serialize(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.encodeBase64String(baos.toByteArray());
    }

    private static Object deserialize(String s) throws IOException,
            ClassNotFoundException {
        byte[] data = Base64.decodeBase64(s);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;//(o.toString().substring(1, o.toString().length()-1));
    }

    /**
     * To get a value from string of hashmap by passing key that existed in Hashmap before converting to String.
     * Sample string: {fld_category=Principal category, test=test 1, fld_categoryID=1}
     *
     * @param string
     * @param key
     * @return value
     */
    public static String getValueFromStringOfHashMap(String string, String key) {


        int start_index = string.indexOf(key) + key.length() + 1;
        int end_index = string.indexOf(",", start_index);
        if (end_index == -1) { // because last key value pair doesn't have trailing comma (,)
            end_index = string.indexOf("}");
        }
        String value = string.substring(start_index, end_index);

        return value;
    }

    /*@Test
    public void testStringHelper() {
        InteractiveCreateDocumentRequestContract contract = StringHelper.translateStringToObject(alpha);
        System.out.println( " print contract : Policies " + contract.getPolicy().get(0) + ":::" + contract.getPolicy().get(1));
        System.out.println( " print contract : Party " + contract.getParty().get(0));
        System.out.println( " print contract : docType " + contract.getDoctype());
        System.out.println( " print contract : user " + contract.getUser());
        System.out.println( " print contract : case " + contract.getCaseNumber().get(0));
    }*/

    @Test
    public void testStringSplits() {
        String versionNum = completeString.split("~")[0];
        System.out.println(versionNum);
        String docId = completeString.split("~")[1];
        System.out.println(docId);

    }

    @Test
    public void testFirstNameSplit() {
        String firstName = null;
        // String name = "SRIDHAR REDDY SANGALA";
        String name = "DIIntaketext5 Approval6";
        String[] parts = name.trim().split( "\\s+" );
        System.out.println( "FirstName out of name tag is : " + parts[0] );
        System.out.println( "FirstName out of name tag is : " + parts[1] );
        firstName = parts[0];
        System.out.println( firstName );

    }

    @Test
    public void testCaseNumberSplit() {
        //String caseNumber1 = "NTN-100423-ABS-01";
        //String caseNumber1 = "NTN-57930-DI-02-SA-01";
        String caseNumber1 = "NTN-57930";
        String[] parts1 = caseNumber1.split( "-" );
        //String[] parts2 = caseNumber2.split("-");
        //String[] parts3 = caseNumber3.split("-");
        System.out.println( parts1.length );
        //System.out.println( parts2.length);
        //System.out.println( parts3.length);
        if (parts1.length > 2) {
            System.out.println( parts1[0] );
            String case1 = caseNumber1.substring( caseNumber1.indexOf( caseNumber1.split( "-" )[0] ), caseNumber1.indexOf( caseNumber1.split( "-" )[2] ) - 1 );
            System.out.println( case1 );
        } else {
            String case1 = caseNumber1;
            System.out.println( case1 );
        }
    }

    @Test
    public void testEmailIdSplit() {
        String emailid = "CHRIS.KERR@SUNLIFE.COM";
        //String emailid = "SYSADMIN";
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
        //Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(emailid);
        if (!(StringUtils.isAlphanumeric(emailid))) {
            System.out.println("Inside the block ");
            String email2UserId = StringUtils.remove(emailid, "@SUNLIFE.COM");
            System.out.println(email2UserId);
        } else {
            System.out.println("Outside the block ");
            System.out.println(emailid);
        }

    }

    @Test
    public void testXMLGregorianCalendar() {
        /* Create Date Object */
        Date date = new Date();
        XMLGregorianCalendar xmlDate = null;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);

        try {
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("XMLGregorianCalendar :- " + xmlDate);
    }

    @Test
    public void testMap() {
        ObjectMapper oMapper = new ObjectMapper();
        HashMap<String, Object> mapQueryParams = new HashMap<>();
        mapQueryParams.put("doctype", "doc");
        mapQueryParams.put("party", "[[218454,267858]]");
        mapQueryParams.put("policy", "[[115,112]]");
        mapQueryParams.put("caseNumber", "[100]");
        mapQueryParams.put("user", "user");
        try {
            String serstring = serialize(mapQueryParams);

            //Map<String,Object> map = oMapper.convertValue(deserialize("rO0ABXQAjntwb2xpY3k9W1syMTg0NDUsMjA0MjQ1XV0sIHBhcnR5PVtbMTE1LDQ3XV0sIGRvY3R5cGU9Q2hhbmdlIGluIERlZmluaXRpb24gb2YgRGlzYWJpbGl0eSBBcHByb3ZhbCBMZXR0ZXIsIHVzZXI9bnVsbCwgY2FzZU51bWJlcj1bTlROLTE5LURJLTAxXX0="),new TypeReference<Map<String, Object>>() {});
            XMLDecoder xmlDecoder = new XMLDecoder(new ByteArrayInputStream(serstring.getBytes()));
            Map<String, Object> parsedMap = (Map<String, Object>) xmlDecoder.readObject();
            for (Map.Entry<String, Object> entry : parsedMap.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue().toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRegexStartEndCharacter() {
        String party = "[115,111,200,300]";
        if (party.startsWith("[")) {
            StringBuilder sb = new StringBuilder(party); // starts with [ ends with ]
            String result = sb.deleteCharAt(0).toString();
            result = sb.deleteCharAt(result.lastIndexOf(']')).toString();
            System.out.println(result);
            List<String> items = Arrays.asList(result.split("\\s*,\\s*"));
            System.out.println(items.size());
            for (String s : items) {
                System.out.println(s);
            }

        } else {
            System.out.println("hello");
        }
    }
    /*
    public void testMap1() {
       *//* HashMap<String, Object> mapQueryParams = new HashMap<>();
        mapQueryParams.put("doctype", "doc");
        mapQueryParams.put("party", "[[218454,267858]]");
        mapQueryParams.put("policy", new ArrayList<>().add("115"));
        mapQueryParams.put("caseNumber", "[100]");
        mapQueryParams.put("user", "user");
        String string = "rO0ABXQAjntwb2xpY3k9W1syMTg0NDUsMjA0MjQ1XV0sIHBhcnR5PVtbMTE1LDQ3XV0sIGRvY3R5cGU9Q2hhbmdlIGluIERlZmluaXRpb24gb2YgRGlzYWJpbGl0eSBBcHByb3ZhbCBMZXR0ZXIsIHVzZXI9bnVsbCwgY2FzZU51bWJlcj1bTlROLTE5LURJLTAxXX0";
        String result = getValueFromStringOfHashMap(string, "doctype");
        String party = getValueFromStringOfHashMap(string, "party");
        String policy = getValueFromStringOfHashMap(string, "policy");
        System.out.println(result);
        System.out.println(party);
        System.out.println(policy);*//*

        try {
            JSONObject jsonObject = new JSONObject("{policy=[[218445,204245]], party=[[115,266,57]], doctype=Claim Approval Letter, user=null, caseNumber=[NTN-19-DI-01]}");
           // JSONObject jObject  = new JSONObject(output); // json
            JSONObject data = jObject.getJSONObject("data");
            System.out.println(jsonObject.get("policy"));
        }catch (JSONException err){
            System.out.println(err.getMessage());
        }
    }*/

    @Test
    public void testListItems() {
        List<String> list1 = new ArrayList<>();
        //list1.add("218454");
        list1.add(" ");
        list1.add("267858");
        list1.add("267958");

        for (String eachParty : list1) {
            if (eachParty.equals(list1.get(0))) {
                System.out.println(list1.get(0));
            } else {
                System.out.println(" not first in the list " + eachParty);
            }
        }

    }

   /* @Test
    public void testjsonString(){
        String jsonString = null;
        try {
            jsonString = new JSONObject()
                    .put("JSON1", "Hello World!")
                    .put("JSON2", "Hello my World!")
                    .put("JSON3", new JSONObject()
                            .put("key1", "value1")).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(jsonString);
    }*/

    /*@Test
    public void testmapjson() {
        JSONObject jsonObject = null;
        JSONArray array = null;
        try {
            List<Integer> list1 = new ArrayList<>();
            list1.add(218454);
            list1.add(267858);
            list1.add(267958);
            array = new JSONArray();
                jsonObject = new JSONObject();

            jsonObject.put("doctype", "doc");
            jsonObject .put("policy", "218454, 267858, 267958");
            jsonObject.put("party", "115");
            jsonObject.put("caseNumber", "100,101");
            jsonObject.put("user", "user");

            //jsonObject.put("pluginMap", mapQueryParams);
            //System.out.println(jsonObject.toString());
            ObjectMapper mapper = new ObjectMapper();
            JSONParser parser = new JSONParser();
            System.out.println(parser.parse(jsonObject.toString()));
            Map<String, String> map = mapper.readValue(jsonObject.toString(), Map.class);
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    @Test
    public void testStringList() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("");
        list.add(" ");
        list.add("three");
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            if (sb.length() != 0 && s.isEmpty()) {
                sb.append(",");
            }
            sb.append(s);
        }
        System.out.println(sb.toString());
    }

    @Test
    public void testRemovingCommaSplchars() {
        List<String> s1 = getListFromString("[ ,\"212\"]");
        for (String s : s1) {
            if (s.isEmpty()) {
                System.out.println(" s is empty " + s);
            } else {
                System.out.println(" s is empty " + s);
            }
        }
    }

    private List<String> getListFromString(String alphaNumeric) {
        StringBuilder sb = new StringBuilder(alphaNumeric); // starts with [ ends with ]
        String result = sb.deleteCharAt(0).toString();
        result = sb.deleteCharAt(result.lastIndexOf(']')).toString();
        return Arrays.asList(result.split("\\s*,\\s*"));
    }

    @Test
    public void testMultiple() {
        List<String> s3 = getListFromString("[\"212\"]");
        System.out.println(" size is " + s3.size());
        for (String s : s3) {
            if (s.isEmpty()) {
                System.out.println(" s is empty " + s);
            } else {
                System.out.println(" s is empty " + s);
            }
        }

        List<String> s4 = getListFromString("[\"212\",\"215\"]");
        System.out.println(" size is " + s4.size());
        for (String s : s4) {
            if (s.isEmpty()) {
                System.out.println(" s is empty " + s);
            } else {
                System.out.println(" s is empty " + s);
            }
        }
    }


    @Test
    public void testRemoveSpecialChars() {
        String party = "[212]";

        if (party.startsWith("[")) {
            StringBuilder sb = new StringBuilder(party); // starts with [ ends with ]
            String result = sb.deleteCharAt(0).toString();
            result = sb.deleteCharAt(result.lastIndexOf(']')).toString();
            System.out.println(result);
        }
    }


    @Test
    public void testRegex() {
        String partyKey = "55f2fd19-1f38-4569-aa96-22b1e2aa3b69";
        Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
        Matcher matcher = pattern.matcher(partyKey);

        boolean b = matcher.find();
        if (b == true)
            System.out.println("There is a special character in my string:- " + partyKey);
        else
            System.out.println("There is no special character in my String :-  " + partyKey);
    }

    @Test
    public void testPrefixRemove() {
        String documentId = "DG_12345678";
        if (documentId.matches("[0-9]+")) {
            System.out.println(" yes it is Number" + documentId);
        } else {
            /*System.out.println(" No it is not a Number " + documentId);
            documentId = documentId.replaceAll("DG_", "");
            System.out.println("  Number " + documentId);*/
            if (documentId.startsWith("DG_")) {
                String ddocumentId = documentId.replaceAll("DG_", "");
                System.out.println("Inside condition " + ddocumentId);
            } else {
                System.out.println(" what's up ");
            }
        }
    }

    @Test
    public void testKeyValuePairSplit() {
        String jsonObjectString = "{status=completed, documentid=DG_1234}";
        Map<String, String> map = new HashMap<String, String>();
        String removeBrackets = jsonObjectString.substring(1, jsonObjectString.length() - 1);
        String[] keyValueStrings = removeBrackets.split(",");
        /*for(int i =0; i < keyValueStrings.length ; i++) {
            String[] items =keyValueStrings[i].split("=");
            System.out.println("Key : " + items[0] + " and Value : " + items[1]);
        }*/

        for (String eachString : keyValueStrings) {
            String[] keyValue = eachString.split(" *= *");
            map.put(keyValue[0], keyValue[1]);
            System.out.println("Key : " + keyValue[0] + " - Value : " + keyValue[1]);
        }

    }

    @Test
    public void testEscapeCharcs() throws MalformedURLException, UnsupportedEncodingException {
        String mcurl = "http://dev-dgpapps.us.sunlife/doc-manager/index.xhtml?source-system=GCSDI&amp;requester-code=GCSDI&amp;requester-token=EKgPuy8lsKNH%2FBiywQLWDkyIxA2IYFOSHjrxKbJe3JOr&amp;user-id=k344&amp;doc-id=DG_98800";
        String pluginUrl1 = "http://dev-dgpapps.us.sunlife/doc-manager/index.xhtml?source-system=GCSDI&requester-code=GCSDI&requester-token=EKgPuy8lsKNH%2FBiywQLWDkyIxA2IYFOSHjrxKbJe3JOr&user-id=k344&doc-id=DG_98800";
        String mc = StringEscapeUtils.unescapeXml(mcurl);
        //String plugin = StringEscapeUtils.escapeJava(pluginUrl1);
        System.out.println(new URL(mc));
        //System.out.println(plugin);

        String token = "EKgPuy8lsKNH%2FBiywQLWDkyIxA2IYFOSHjrxKbJe3JOr";
        String convertToken1 = StringEscapeUtils.unescapeXml(token);
        // System.out.println(convertToken1);
        String result = java.net.URLDecoder.decode(mcurl, StandardCharsets.UTF_8.name());
        // System.out.println(result);
        Map<String, String> myMap = new HashMap<>();

        String nextTest = "{requester-code=GCSDI, source-system=GCSDI,  contentid=DG_98801, doc-id=98801,  status=null, user-id=k344, requester-token=EKgPuy8lsKNH/FBiywQLWDkyIxA2IYFOSHjrxKbJe3JOr,  appid=GCSDI}";
        String[] pairs = nextTest.split(",");
        for (String pair : pairs) {
            //String pair = pairs[i];
            String[] keyValue = pair.split("=");
            myMap.put(keyValue[0], keyValue[1]);
        }
        System.out.println(myMap);

        for (String entry : myMap.keySet()) {
            if (entry.equalsIgnoreCase(myMap.get("doc-id"))) {
                String conId = myMap.get("doc-id");
                System.out.println(conId);
            }
        }

    }


    @Test
    public void testList() {
        List<String> stringList = new ArrayList<>();
        stringList.add("913");
        stringList.add("708");
        // stringList.add("2748");
        if (stringList.size() == 1) {
            System.out.println(" only 1");
        } else if (stringList.size() > 1) {
            System.out.println(" two items");
        } else {
            System.out.println(" only 2");
        }
    }

    private XMLGregorianCalendar getXMLGregorianCalendar() {
        /* Create Date Object */
        Date date = new Date();
        XMLGregorianCalendar xmlDate = null;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( date );

        try {
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar( gc );
            xmlDate.setTimezone( -2147483648 );
            xmlDate.setMillisecond( -2147483648 );
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println( "XMLGregorianCalendar :- " + xmlDate );
        return xmlDate;
    }
}
