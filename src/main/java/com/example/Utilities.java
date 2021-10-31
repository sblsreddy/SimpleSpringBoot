/**
 * @author : Sridhar Reddy Sangala
 * @Date : 10/31/2021
 * @Time : 10:38 AM
 * @project : learning
 */
public class Utilities {
    final static String hey = " Hello ";
  
    public static void main(String[] args) {
        Animal aDog = new Dog();
        aDog.eat();
        aDog.makeSound();
        aDog.sleep();

        Animal aCat = new Cat();
        aCat.eat();
        aCat.makeSound();
        aCat.sleep();

        Vehicle car = new Car();
        car.start();
        car.stop();

        Vehicle aero = new Aeroplane();
        aero.start();
        aero.stop();
      String[] recipientDataWithDataRootXml = stringData.split( Pattern.quote( "$$" ) );

    }
  
      // To handle special character in sender Name, profileName and senderDepartment field etc
    private static String getSpecialCharInFieldValueHandled(String exRecepientInfoDataString) {
        exRecepientInfoDataString = handleSpecialCharacterForFieldName(exRecepientInfoDataString, "firstName", "\"firstName\":", "\"firstName\":\"");
        exRecepientInfoDataString = handleSpecialCharacterForFieldName(exRecepientInfoDataString, "lastName", "\"lastName\":", "\"lastName\":\"");
    }
  
  public static String handleSpecialCharacterForFieldName(String exRecepientInfoDataString, String fieldName, String s, String s1) {
        if (exRecepientInfoDataString.contains(fieldName)) {
            String cdcJsonExtractString1 = exRecepientInfoDataString.substring(0, exRecepientInfoDataString.indexOf(s));
            String cdcJsonExtractString2 = exRecepientInfoDataString.substring(exRecepientInfoDataString.indexOf(s));
            String fieldNameSectionValue = StringUtils.substringBetween(cdcJsonExtractString2, s, ",\"");
            if (!StringHelper.isEmptyString(fieldNameSectionValue) && !fieldNameSectionValue.equalsIgnoreCase("null")) {
                exRecepientInfoDataString = cdcJsonExtractString1 + s1 + getCharacterConverted(fieldNameSectionValue.replace("\"", ""))
                        + cdcJsonExtractString2.substring(cdcJsonExtractString2.indexOf("\",\""));
            }
        }
        return exRecepientInfoDataString;
    }
  
  // get special character converted to character sequence
    public static String getCharSequenceForSpecialCharatcer(String  recipientInfoString) {
        String recipientInfoStringUpdated = recipientInfoString;
        try {
            recipientInfoStringUpdated = recipientInfoString.replaceAll("&(?![a-z]{1,6};)", "&amp;").replaceAll("'(?![a-z]{1,6};)", "&apos;");
            MCUtilities.logIfDebug( LOGGER, "Special character converted for just &  and ' string looks like  : " + recipientInfoStringUpdated );
        } catch (Exception e) {
            MCUtilities.logIfDebug( LOGGER, "exception is : " + e );
        }
        return recipientInfoStringUpdated;
    }
  
  // get special character allowed ( 1!2"34$5%6^7&8*9()10_11+12}{13{}14:15@16?17>18<#19@20]21^_~á ñ ò ú & ' "- ) converted for any string
    public static String getCharacterConverted(String  inputString) {
        String convertedString = inputString;
        try {
           // MCUtilities.logIfDebug( LOGGER, "Special character received  looks like  : " + convertedString );
            convertedString = StringEscapeUtils.escapeXml(new String((inputString.getBytes("ISO-8859-1")), "UTF-8"));
           // MCUtilities.logIfDebug( LOGGER, "Special character after conversion  looks like  : " + convertedString );
        } catch (Exception e) {
            MCUtilities.logIfDebug( LOGGER, "exception is : " + e );
        }
        return convertedString;
    }
  
  
  @Test
    public void removeNS2andXMlTag() {
        String request = " <?xml version='1.0' encoding='utf-8'?>\n" +
                "<p:DocumentRESTBroadcastRequest xmlns:p=\"http://www.fineos.com/wscomposer/DocumentRESTBroadcast\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "  <config-name>DocumentRESTBroadcast</config-name>\n" +
                "  <OCDocument>hello</OCDocument></p:DocumentRESTBroadcastRequest>";
        String afterRemoval = removeInvalidCharacters(request);
        System.out.println(afterRemoval);
    }

    private String removeInvalidCharacters(String input) {
        String x = input.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
        return x;
    }
  
  

}
