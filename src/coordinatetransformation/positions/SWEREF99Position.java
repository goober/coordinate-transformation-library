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

import coordinatetransformation.GaussKreuger;
import coordinatetransformation.Position;

public class SWEREF99Position extends Position {

    public enum SWEREFProjection {

    sweref_99_tm,
    sweref_99_12_00,
    sweref_99_13_30,
    sweref_99_15_00,
    sweref_99_16_30,
    sweref_99_18_00,
    sweref_99_14_15,
    sweref_99_15_45,
    sweref_99_17_15,
    sweref_99_18_45,
    sweref_99_20_15,
    sweref_99_21_45,
    sweref_99_23_15
}

    private SWEREFProjection projection;

    /**
     * Create a SWEREF99 position from double values with SWEEREF 99 TM as default projection
     * @param n North value
     * @param e East value
     */
    public SWEREF99Position(double n, double e) {
        super(n, e, Grid.SWEREF99);

        this.projection = SWEREFProjection.sweref_99_tm;
    }

    /**
     * Create a SWEREF99 position from double values. Suplly the projection for values
     * other than SWEREF 99 TM
     * @param n North value
     * @param e East value
     * @param projection Projection type
     */
    public SWEREF99Position(double n, double e, SWEREFProjection projection) {
        super(n, e, Grid.SWEREF99);
        this.projection = projection;
    }

    /**
     * Create a SWEREF99 position by converting a WGS84 position
     * @param position WGS84 position to convert
     * @param projection Projection to convert to
     */
    public SWEREF99Position(WGS84Position position, SWEREFProjection projection) {

        super(Grid.SWEREF99);
        GaussKreuger gkProjection = new GaussKreuger();
        gkProjection.swedish_params(getProjectionString(projection));
        double[] lat_lon = gkProjection.geodetic_to_grid(position.getLatitude(), position.getLongitude());
        this.latitude = lat_lon[0];
        this.longitude = lat_lon[1];

        this.projection = projection;
    }

    /**
     * Convert the position to WGS84 format
     * @return
     */
    public WGS84Position toWGS84() {
        GaussKreuger gkProjection = new GaussKreuger();
        gkProjection.swedish_params(getProjectionString(this.projection));
        double[] lat_lon = gkProjection.grid_to_geodetic(this.latitude, this.longitude);

        WGS84Position newPos = new WGS84Position(lat_lon[0], lat_lon[1]);

        return newPos;
    }

    /**
     * Get projection type as String
     * @return
     */
    public String getProjectionString() {
        return getProjectionString(this.projection);
    }

    private String getProjectionString(SWEREFProjection projection) {

        String retVal = "";
        switch (projection) {

            case sweref_99_tm:
                retVal = "sweref_99_tm";
                break;
            case sweref_99_12_00:
                retVal = "sweref_99_1200";
                break;
            case sweref_99_13_30:
                retVal = "sweref_99_1330";
                break;
            case sweref_99_14_15:
                retVal = "sweref_99_1415";
                break;
            case sweref_99_15_00:
                retVal = "sweref_99_1500";
                break;
            case sweref_99_15_45:
                retVal = "sweref_99_1545";
                break;
            case sweref_99_16_30:
                retVal = "sweref_99_1630";
                break;
            case sweref_99_17_15:
                retVal = "sweref_99_1715";
                break;
            case sweref_99_18_00:
                retVal = "sweref_99_1800";
                break;
            case sweref_99_18_45:
                retVal = "sweref_99_1845";
                break;
            case sweref_99_20_15:
                retVal = "sweref_99_2015";
                break;
            case sweref_99_21_45:
                retVal = "sweref_99_2145";
                break;
            case sweref_99_23_15:
                retVal = "sweref_99_2315";
                break;
            default:
                retVal = "sweref_99_tm";
                break;
        }
        return retVal;
    }
    @Override
    public String toString() {
        return String.format("N: %f E: %f Projection: %s", this.latitude, this.longitude,getProjectionString());
    }
}
