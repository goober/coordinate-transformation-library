/**
 *  CoordinateTransformationLibrary - Mathias Åhsberg 2009
 *
 *  RT90, SWEREF99 and WGS84 coordinate transformation library
 *
 * Visit my repository at http://github.com/goober
 *
 * This library is a java port of the .NET library by Björn Sållarp.
 *  calculations are based entirely on the excellent
 *  javscript library by Arnold Andreassons.
 *
 * Source: http://www.lantmateriet.se/geodesi/
 * Source: Arnold Andreasson, 2007. http://mellifica.se/konsult
 * Source: Björn Sållarp. 2009. http://blog.sallarp.com
 * Author: Mathias Åhsberg, 2009. http://github.com/goober/
 *
 * License: http://creativecommons.org/licenses/by-nc-sa/3.0/
 */
package coordinatetransformation.positions;


import coordinatetransformation.Position;
import java.text.ParseException;


public class WGS84Position extends Position {

public enum WGS84Format {Degrees,DegreesMinutes,DegreesMinutesSeconds}

    /**
     * Create a new WGS84 position with empty coordinates
     */
    public WGS84Position() {
        super(Grid.WGS84);
    }

    /**
     * Create a new WGS84 position with latitude and longitude
     * @param latitude
     * @param longitude
     */
    public WGS84Position(double latitude, double longitude) {
        super(latitude,longitude,Grid.WGS84);
    }

    /**
     * Create a new WGS84 position from a String containing both latitude
     * and longitude. The string is parsed based on the supplied format.
     * @param positionString
     * @param format
     * @throws java.lang.Exception
     */
    public WGS84Position(String positionString, WGS84Format format) throws ParseException{
        super(Grid.WGS84);

        if(format.equals(WGS84Format.Degrees)) {
            positionString = positionString.trim();
            String[] lat_lon = positionString.split(" ");
            if (lat_lon.length == 2) {
               this.latitude = Double.parseDouble(lat_lon[0].replace(",", "."));
               this.longitude = Double.parseDouble(lat_lon[1].replace(",", "."));
            }
            else {
                throw new ParseException("The position string is invalid",0);

            }
        }
        else if (format.equals(WGS84Format.DegreesMinutes) || format.equals(WGS84Format.DegreesMinutesSeconds)) {
            int firstValueEndPos = 0;

            if(format == WGS84Format.DegreesMinutes)
                firstValueEndPos = positionString.indexOf("'");
            else if (format == WGS84Format.DegreesMinutesSeconds)
                firstValueEndPos = positionString.indexOf("\"");

            String lat = positionString.substring(0,firstValueEndPos +1).trim();
            String lon = positionString.substring(firstValueEndPos +1).trim();

            setLatitudeFromString(lat, format);
            setLongitudeFromString(lon,format);
        }
    }

    /**
     * Set the latitude value from a string. The string is parsed based on given format.
     * @param value
     * @param format
     */
    public void setLatitudeFromString(String value, WGS84Format format) {
        value = value.trim();

        if(format == WGS84Format.DegreesMinutes)
            this.latitude = parseValueFromDmString(value,"S");
        else if (format == WGS84Format.DegreesMinutesSeconds)
            this.latitude = parseValueFromDmsString(value,"S");
        else if (format == WGS84Format.Degrees)
            this.latitude = Double.parseDouble(value);

    }

    /**
     * Set the longitude value from a string. The string is parsed based on given format.
     * @param value
     * @param format
     */
        public void setLongitudeFromString(String value, WGS84Format format) {
        value = value.trim();

        if(format == WGS84Format.DegreesMinutes)
            this.longitude = parseValueFromDmString(value,"W");
        else if (format == WGS84Format.DegreesMinutesSeconds)
            this.longitude = parseValueFromDmsString(value,"W");
        else if (format == WGS84Format.Degrees)
            this.longitude = Double.parseDouble(value);

    }
        /**
         * Returns a string representation in the given format
         * @param format
         * @return
         */
        public String latitudeToString(WGS84Format format) {
                             if (format == WGS84Format.DegreesMinutes)
                return convToDmString(this.latitude,"N","S");
            else if (format == WGS84Format.DegreesMinutesSeconds)
                return convToDmsString(this.latitude,"N","S");
            else
                return Double.toString(this.latitude);
        }
        /**
         * Returns a string represenation in the given format
         * @param format
         * @return
         */
           public String longitudeToString(WGS84Format format) {
                                 if (format == WGS84Format.DegreesMinutes)
                return convToDmString(this.longitude,"E","W");
            else if (format == WGS84Format.DegreesMinutesSeconds)
                return convToDmsString(this.longitude,"E","W");
            else
                return Double.toString(this.longitude);
        }

           private String convToDmString(double value, String positiveValue, String negativeValue) {
               if (value == Double.MIN_VALUE) {
                   return "";
               }
               double degrees = Math.floor(Math.abs(value));
               double minutes = (Math.abs(value) - degrees) * 60;

               return String.format("%s %.0fº %.0f'", value >=0 ? positiveValue : negativeValue, degrees, ((double)Math.floor(minutes * 10000) / 10000));
           }

           private String convToDmsString(double value, String positiveValue,String negativeValue) {

               if(value == Double.MIN_VALUE) {
                   return "";
               }
               double degrees = Math.floor(Math.abs(value));
               double minutes = Math.floor((Math.abs(value) - degrees) * 60);
               double seconds = (Math.abs(value) - degrees - minutes / 60) * 3600;

               return String.format("%s %.0fº %.0f' %.5f\"",value >=0 ? positiveValue : negativeValue, degrees, minutes, ((double) Math.round(seconds * 100000) / 100000));
           }

           private double parseValueFromDmString(String value, String positiveChar) {
                   double retVal = 0;
                   if (!(value == null)) {
                       if(!value.isEmpty()) {
                           String direction = value.substring(0,1);
                           value = value.substring(1).trim();

                           String degree = value.substring(0,value.indexOf("º"));
                           value = value.substring(value.indexOf("º") +1).trim();

                           String minutes = value.substring(0,value.indexOf("'"));
                           value = value.substring(value.indexOf("'") + 1).trim();

                           retVal = Double.parseDouble(degree);
                           retVal += Double.parseDouble(minutes.replace(",", ".")) / 60;

                           if(retVal > 90) {
                               retVal = Double.MIN_VALUE;
                           }
                           if(direction.equals(positiveChar) || direction.equals("-")) {
                               retVal *= -1;
                           }

                       }
                   }
                   else {
                       retVal = Double.MIN_VALUE;
                   }
                   return retVal;
           }

           private double parseValueFromDmsString(String value, String positiveChar) {
               double retVal = 0;
               if(!(value == null)) {
                   if(!value.isEmpty()) {
                    String direction = value.substring(0,1);
                           value = value.substring(1).trim();

                           String degree = value.substring(0,value.indexOf("º"));
                           value = value.substring(value.indexOf("º") +1).trim();

                           String minutes = value.substring(0,value.indexOf("'"));
                           value = value.substring(value.indexOf("'") + 1).trim();

                           String seconds = value.substring(0,value.indexOf("\""));

                           retVal = Double.parseDouble(degree);
                           retVal += Double.parseDouble(minutes) / 60;
                          
                           retVal += Double.parseDouble(seconds.replace(",", ".")) / 3600;
                           
                           if (retVal > 90) {
                               retVal = Double.MIN_VALUE;
                               return retVal;
                           }
                          if(direction.equals(positiveChar) || direction.equals("-")) {
                               retVal *= -1;
                           }

                   }
               }
               else {
                   retVal = Double.MIN_VALUE;
               }
               return retVal;

           }
           @Override
           public String toString() {
               return String.format("Latitude: %s  Longitude: %s", latitudeToString(WGS84Format.DegreesMinutesSeconds), longitudeToString(WGS84Format.DegreesMinutesSeconds));
           }
}
