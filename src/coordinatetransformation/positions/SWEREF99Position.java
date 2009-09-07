/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinatetransformation.positions;

import coordinatetransformation.GaussKreuger;
import coordinatetransformation.Position;

/**
 *
 * @author goober
 */


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

    public SWEREF99Position(double n, double e) {
        super(n, e, Grid.SWEREF99);

        this.projection = SWEREFProjection.sweref_99_tm;
    }

    public SWEREF99Position(double n, double e, SWEREFProjection projection) {
        super(n, e, Grid.SWEREF99);
        this.projection = projection;
    }

    public SWEREF99Position(WGS84Position position, SWEREFProjection projection) {

        super(Grid.SWEREF99);
        GaussKreuger gkProjection = new GaussKreuger();
        gkProjection.swedish_params(getProjectionString(projection));
        double[] lat_lon = gkProjection.geodetic_to_grid(position.getLatitude(), position.getLongitude());
        this.latitude = lat_lon[0];
        this.longitude = lat_lon[1];

        this.projection = projection;
    }

    public WGS84Position toWGS84() {
        GaussKreuger gkProjection = new GaussKreuger();
        gkProjection.swedish_params(getProjectionString(this.projection));
        double[] lat_lon = gkProjection.grid_to_geodetic(this.latitude, this.longitude);

        WGS84Position newPos = new WGS84Position(lat_lon[0], lat_lon[1]);

        return newPos;
    }

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
