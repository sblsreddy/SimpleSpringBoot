package utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;



import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

/**
 * String helper class.
 */
public final class StringHelper {
    /**
     * EMPTY_STRING.
     */
    private static final String EMPTY_STRING = "";
    /**
     * The LOGGER.
     */
    private static Logger LOGGER = LogFactory.getLogger(StringHelper.class);

    /**
     * Build a comma separated String for a java.util.List of String objects.
     * 
     * @param stringList java.util.List of String objects
     * @return comma separated String
     */
    public static String buildCSStringFromList(final List<String> stringList) {
        StringBuilder csString = new StringBuilder("");
        if (stringList != null && !stringList.isEmpty()) {
            for (String string : stringList) {
                if (csString.length() > 0) {
                    csString.append(", ");
                }
                csString.append(string.trim());
            }
        }
        return csString.toString();
    }

    /**
     * Build a full name from first name and last name in first last format.
     * 
     * @param firstName First name
     * @param lastName Last name
     * @return Full name in first last format
     */
    public static String buildFirstLastName(final String firstName, final String lastName) {
        String name = null;
        if (firstName != null || lastName != null) {
            final StringBuilder builder = new StringBuilder("");
            if (firstName != null && !firstName.trim().equals("")) {
                builder.append(firstName.trim());
            }
            if (lastName != null && !lastName.trim().equals("")) {
                if (builder.length() > 0) {
                    builder.append(" ");
                }
                builder.append(lastName.trim());
            }
            name = builder.toString();
        }
        return name;
    }

    /**
     * Build a full name from first name and last name in first last format.
     * 
     * @param firstName First name
     * @param middleName Middle name
     * @param lastName Last name
     * @return Full name in first last format
     */
    public static String buildFirstMiddleLastName(final String firstName, final String middleName, final String lastName) {
        String name = null;
        if (firstName != null || lastName != null) {
            final StringBuilder builder = new StringBuilder("");
            if (firstName != null && !firstName.trim().equals("")) {
                builder.append(firstName.trim());
            }
            if (middleName != null && !middleName.trim().equals("")) {
                if (builder.length() > 0) {
                    builder.append(" ");
                }
                builder.append(middleName.trim());
            }
            if (lastName != null && !lastName.trim().equals("")) {
                if (builder.length() > 0) {
                    builder.append(" ");
                }
                builder.append(lastName.trim());
            }
            name = builder.toString();
        }
        return name;
    }

    /**
     * Build a full name from first name and last name in last, first format.
     * 
     * @param firstName First name
     * @param lastName Last name
     * @return Full name in last, first format
     */
    public static String buildLastFirstName(final String firstName, final String lastName) {
        String name = null;
        if (firstName != null || lastName != null) {
            final StringBuilder builder = new StringBuilder("");
            if (lastName != null && !lastName.trim().equals("")) {
                builder.append(lastName.trim());
            }
            if (firstName != null && !firstName.trim().equals("")) {
                if (builder.length() > 0) {
                    builder.append(", ");
                }
                builder.append(firstName.trim());
            }
            name = builder.toString();
        }
        return name;
    }

    /**
     * Format object as a currency.
     * 
     * @param obj Object
     * @return Object formatted as currency
     */
    public static String formatCurrency(final Object obj) {
        return NumberFormat.getCurrencyInstance().format(obj);
    }

    /**
     * Format a SSN String 123456789 as 123-45-6789 format.
     * 
     * @param ssnString SSN String to format
     * @return formatted SSN String or null if ssnString argument is null
     */
    /*public static String formatSSN(final String ssnString) {
        String string = null;
        if (ssnString != null) {
            final StringBuilder builder = new StringBuilder("");
            builder.append(ssnString.substring(0, 3)).append('-').append(ssnString.substring(3, StopLossClaimsConstants.INT_5)).append('-').append(ssnString.substring(StopLossClaimsConstants.INT_5));
            string = builder.toString();
        }
        return string;
    }
*/
    /**
     * Determine if a java.lang.String is empty or not.
     * 
     * @param stringIn String to test
     * @return true if stringIn is null or length is 0, false otherwise
     */
    public static boolean isEmptyString(final String stringIn) {
        boolean result = true;
        if (stringIn != null && !stringIn.trim().equals(EMPTY_STRING) && !stringIn.equalsIgnoreCase("null")) {
            result = false;
        }
        return result;
    }

    /**
     * Parse a Base64 encoded String.
     * 
     * @param stringIn Base64 encoded String
     * @return parse value
     */
    public static String parseBase64Binary(final String stringIn) {
       return new String(DatatypeConverter.parseBase64Binary(stringIn));
    }

    /**
     * Trims the String value if not null.
     * 
     * @param s String to be trimmed
     * @return null or trimmed string value
     */
    public static String safeTrim(final String s) {
        String str = s;
        if (s != null) {
            str = s.trim();
        }
        return str;
    }

    /**
     * Check if two String objects are equal.
     * 
     * @param stringOne First String to compare
     * @param stringTwo Second String to compare
     * @return true if equal, false otherwise
     */
    public static boolean stringEquals(final String stringOne, final String stringTwo) {
        boolean result = false;
        if (stringOne == null) {
            if (stringTwo == null) {
                result = true;
            }
        }
        else if (stringTwo != null) {
            result = stringOne.trim().equalsIgnoreCase(stringTwo.trim());
        }
        return result;
    }

    /**
     * Check if a string exists in a var arg of Strings.
     * 
     * @param string String object to look for
     * @param strings Vararg of String objects to search
     * @return true if string found, otherwise false
     */
    public static boolean stringIn(final String string, final String... strings) {
        boolean found = false;
        if (strings != null) {
            for (String stringsString : strings) {
                if (stringsString != null && StringHelper.stringEquals(string, stringsString)) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    /**
     * Attempt to translate a Boolean to a String literal.
     * 
     * @param booleanObject Boolean to translate
     * @return "Y" if booleanObject is not null and equals Boolean.TRUE, "N" otherwise
     */
    public static String translateBooleanToString(final Boolean booleanObject) {
        if (booleanObject != null && booleanObject.equals(Boolean.TRUE)) {
            return "Y";
        }
        return "N";
    }

    /**
     * Attempt to translate a String literal into a Boolean value.
     * 
     * @param string String to translate
     * @return Boolean.TRUE if String can be translated and value is "Y" or "YES", Boolean.FALSE otherwise
     */
    public static Boolean translateStringToBoolean(final String string) {
        if (string != null && (string.trim().equalsIgnoreCase("Y") || string.trim().equalsIgnoreCase("YES"))) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * Translate Object to String.
     * 
     * @param obj Object to be translated
     * @return string value
     */
    public static String translateObjectToString(final Object obj) {
        return (obj == null) ? EMPTY_STRING : obj.toString();
    }

    /**
     * Jira ACC-1821
     */
    public static String trimUserID(String loggedinUser) {
        String name = "";
        if (!(StringUtils.isAlphanumeric(loggedinUser))) {
            String email2UserId = StringUtils.remove(loggedinUser, "@SUNLIFE.COM");
            System.out.println(email2UserId);
        } else {
            System.out.println(loggedinUser);
        }
        return name;
    }

    /**
     * Check if a user id passed  contains @ and is more than 30 character.
     *
     * @param userId String object to look for
     * @return user id without @SUNLIFE.COM or GCSUser if its still more than 30 character
     */
    public static String syncEventTableUserIdValidation(String userId) {
        String user =  userId;
        if (userId != null && userId.contains("@")) {
            // Remove the @sunlife.com from user name as we have limit on userId column length in GCS database
            user = userId.split(Pattern.quote("@"))[0];
        }
         if (user != null && user.length()>30){
                MCUtilities.logIfDebug(LOGGER,"Setting User ID as : GCSUser. Since user id : " + user  + " is still grater than 30 character "  );
                user = "GCS";
         }

        return user;
    }

    /**
     * Convert creation date string to XMLGregorianCalendar format .
     *
     * @param dateString creation date string object to convert
     * @return XMLGregorianCalendar for the given date string.
     */
    public static XMLGregorianCalendar getXMLGregorianCalendarFromCreationDate(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        XMLGregorianCalendar xmlDate = null;
        try {
            Date date = dateFormat.parse(dateString);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (Exception e) {
            MCUtilities.logIfDebug(LOGGER, "Exception in getXMLGregorianCalendarFromCreationDate :- " + e.getMessage());

        }
        MCUtilities.logIfDebug(LOGGER, "XMLGregorianCalendar :- " + xmlDate);
        return xmlDate;
    }

    public static String currentDateAndTimeWithAmPmmarker() {
        //Displaying current date and time in 12 hour format with AM/PM
        DateFormat dateFormat2 = new SimpleDateFormat( "MM/dd/yyyy hh.mm aa" );
        String dateString2 = dateFormat2.format( new Date() ).toString();
        MCUtilities.logIfDebug( LOGGER, "Displaying current date and time in AM/PM: " + dateString2 );
        return dateString2;
    }

    public static String getCurrentDateAndTimeString() {
        DateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:sss" );
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        XMLGregorianCalendar now = null;
        DatatypeFactory datatypeFactory = null;
        try {
            datatypeFactory = DatatypeFactory.newInstance();
            now = datatypeFactory.newXMLGregorianCalendar( gregorianCalendar );
        } catch (DatatypeConfigurationException e) {
            MCUtilities.logIfDebug( LOGGER, "Current date and time in DatatypeConfigurationException block : " + e );
        }
        GregorianCalendar gCalendar = now.toGregorianCalendar();
        //Converted to date object
        Date date = gCalendar.getTime();
        //Formatted to String value
        String dateString = df.format( date );
        MCUtilities.logIfDebug( LOGGER, "Current Date & time string is : " + dateString );
        return dateString;
    }
    /**
     * Private constructor.
     */
    private StringHelper() { }
}
