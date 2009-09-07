/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coordinatetransformation;

/**
 *
 * @author goober
 */



public abstract class Position {

    public enum Grid {RT90,WGS84,SWEREF99}

    protected double latitude;
    protected double longitude;
    protected Grid gridFormat;

    //TODO Fix grid enum

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
