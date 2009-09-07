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
import coordinatetransformation.positions.RT90Position;
import coordinatetransformation.positions.SWEREF99Position;
import coordinatetransformation.positions.WGS84Position;
import java.text.ParseException;
import org.junit.Assert;
import org.junit.Test;


public class CoordinateTransformationJUnit4Test {

    @Test
    public void testRT90ToWGS84() {
      RT90Position position = new RT90Position(6583052, 1627548);
            WGS84Position wgsPos = position.toWGS84();

            // Values from Hitta.se for the conversion
            double latFromHitta = 59.3489;
            double lonFromHitta = 18.0473;

            double lat = ((double) Math.round(wgsPos.getLatitude() * 10000)) / 10000;
            double lon = ((double)Math.round(wgsPos.getLongitude() * 10000)) / 10000;

            Assert.assertEquals(latFromHitta, lat,0.00001d);
            Assert.assertEquals(lonFromHitta, lon,0.00001d);

            // String values from Lantmateriet.se, they convert DMS only.
            // Reference: http://www.lantmateriet.se/templates/LMV_Enkelkoordinattransformation.aspx?id=11500
            String latDmsStringFromLM = "N 59º 20' 56,09287\"";
            String lonDmsStringFromLM = "E 18º 2' 50,34806\"";

            Assert.assertEquals(latDmsStringFromLM, wgsPos.latitudeToString(WGS84Position.WGS84Format.DegreesMinutesSeconds));
            Assert.assertEquals(lonDmsStringFromLM, wgsPos.longitudeToString(WGS84Position.WGS84Format.DegreesMinutesSeconds));

    }
    @Test
    public void testWGS84ToRT90() {
        WGS84Position wgsPos = null;
        RT90Position rtPos = null;
        try {
        wgsPos = new WGS84Position("N 59º 58' 55.23\" E 017º 50' 06.12\"", WGS84Position.WGS84Format.DegreesMinutesSeconds);
        rtPos = new RT90Position(wgsPos, RT90Position.RT90Projection.rt90_2_5_gon_v);
        }
        catch( ParseException e) {
        Assert.fail(e.getMessage());
        }
            // Conversion values from Lantmateriet.se, they convert from DMS only.
            // Reference: http://www.lantmateriet.se/templates/LMV_Enkelkoordinattransformation.aspx?id=11500
            double xPosFromLM = 6653174.343;
            double yPosFromLM = 1613318.742;

            double lat = ((double) Math.round(rtPos.getLatitude() * 1000) / 1000);
            double lon = ((double) Math.round(rtPos.getLongitude() * 1000) / 1000);

            Assert.assertEquals(lat, xPosFromLM,0.0001d);
            Assert.assertEquals(lon, yPosFromLM,0.0001d);

    }
    @Test
    public void testWGS84ToSweref() {
      WGS84Position wgsPos = new WGS84Position();

            wgsPos.setLatitudeFromString("N 59º 58' 55.23\"", WGS84Position.WGS84Format.DegreesMinutesSeconds);
            wgsPos.setLongitudeFromString("E 017º 50' 06.12\"", WGS84Position.WGS84Format.DegreesMinutesSeconds);

            SWEREF99Position rtPos = new SWEREF99Position(wgsPos, SWEREF99Position.SWEREFProjection.sweref_99_tm);

            // Conversion values from Lantmateriet.se, they convert from DMS only.
            // Reference: http://www.lantmateriet.se/templates/LMV_Enkelkoordinattransformation.aspx?id=11500
            double xPosFromLM = 6652797.165;
            double yPosFromLM = 658185.201;

            double lat = ((double)Math.round(rtPos.getLatitude() * 1000) / 1000);
            double lon = ((double) Math.round(rtPos.getLongitude() * 1000) / 1000);
            Assert.assertEquals(lat, xPosFromLM,0.0001d);
            Assert.assertEquals(lon, yPosFromLM,0.0001d);

    }

    @Test
    public void SwerefToWGS84() {
      SWEREF99Position swePos = new SWEREF99Position(6652797.165, 658185.201);
            WGS84Position wgsPos = swePos.toWGS84();

            // String values from Lantmateriet.se, they convert DMS only.
            // Reference: http://www.lantmateriet.se/templates/LMV_Enkelkoordinattransformation.aspx?id=11500
            String latDmsStringFromLM = "N 59º 58' 55,23001\"";
            String lonDmsStringFromLM = "E 17º 50' 6,11997\"";

            Assert.assertEquals(latDmsStringFromLM, wgsPos.latitudeToString(WGS84Position.WGS84Format.DegreesMinutesSeconds));
            Assert.assertEquals(lonDmsStringFromLM, wgsPos.longitudeToString(WGS84Position.WGS84Format.DegreesMinutesSeconds));

    }

    @Test
    public void testWGS84Parse() {
        // Values from Eniro.se
        WGS84Position wgsPosDM = null;
        WGS84Position wgsPosDMs = null;
        try {
             wgsPosDM = new WGS84Position("N 62º 10.560' E 015º 54.180'", WGS84Position.WGS84Format.DegreesMinutes);
            wgsPosDMs = new WGS84Position("N 62º 10' 33.60\" E 015º 54' 10.80\"", WGS84Position.WGS84Format.DegreesMinutesSeconds);
        }
        catch(ParseException e) {
        Assert.fail(e.getMessage());
        }
            double lat = ((double) Math.round(wgsPosDM.getLatitude() * 1000) / 1000);
            double lon = ((double) Math.round(wgsPosDM.getLongitude() * 1000) / 1000);

            Assert.assertEquals(62.176, lat,0.0001d);
            Assert.assertEquals(15.903, lon,0.0001d);

            double lat_s = ((double) Math.round(wgsPosDMs.getLatitude() * 1000) / 1000);
            double lon_s = ((double) Math.round(wgsPosDMs.getLongitude() * 1000) / 1000);

            Assert.assertEquals(62.176, lat_s,0.0001d);
            Assert.assertEquals(15.903, lon_s,0.0001d);

    }
}
