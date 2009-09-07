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

package coordinatetransformation;


public abstract class Position {

    public enum Grid {RT90,WGS84,SWEREF99}

    protected double latitude;
    protected double longitude;
    protected Grid gridFormat;

    public Position(double lat, double lon,Grid format) {
        latitude = lat;
        longitude = lon;
        gridFormat = format;
    }
    public Position(Grid format) {
        gridFormat = format;
    }
    public Position() {
        
    }

    public double getLatitude() {
        return this.latitude;
    }
    public double getLongitude() {
        return this.longitude;
    }
}
