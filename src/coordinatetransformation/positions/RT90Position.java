
package coordinatetransformation.positions;

import coordinatetransformation.GaussKreuger;
import coordinatetransformation.Position;


public class RT90Position extends Position {

public enum RT90Projection {

    rt90_7_5_gon_v,
    rt90_5_0_gon_v,
    rt90_2_5_gon_v,
    rt90_0_0_gon_v,
    rt90_2_5_gon_o,
    rt90_5_0_gon_o
}


    private RT90Projection projection;

    public RT90Position(double x, double y) {
        super(x, y, Grid.RT90);
        this.projection = RT90Projection.rt90_2_5_gon_v;
    }

    public RT90Position(double x, double y, RT90Projection projection) {
        super(x, y, Grid.RT90);
        this.projection = projection;
    }

    public RT90Position(WGS84Position position, RT90Projection rt90projection) {
        super(Grid.RT90);

        GaussKreuger gkProjection = new GaussKreuger();
        gkProjection.swedish_params(getProjectionString(rt90projection));
        double[] lat_lon = gkProjection.geodetic_to_grid(position.getLatitude(), position.getLongitude());
        this.latitude = lat_lon[0];
        this.longitude = lat_lon[1];
        this.projection = rt90projection;
    }

    public WGS84Position toWGS84() {
        GaussKreuger gkProjection = new GaussKreuger();
        gkProjection.swedish_params(getProjectionString());
        double[] lat_lon = gkProjection.grid_to_geodetic(this.latitude, this.longitude);

        WGS84Position newPos = new WGS84Position(lat_lon[0], lat_lon[1]);
        return newPos;
    }

    public String getProjectionString() {
        return getProjectionString(this.projection);
    }

    private String getProjectionString(RT90Projection projection) {
        String retVal = "";
         switch (projection)
            {
                case rt90_7_5_gon_v:
                    retVal = "rt90_7.5_gon_v";
                    break;
                case rt90_5_0_gon_v:
                    retVal = "rt90_5.0_gon_v";
                    break;
                case rt90_2_5_gon_v:
                    retVal = "rt90_2.5_gon_v";
                    break;
                case rt90_0_0_gon_v:
                    retVal = "rt90_0.0_gon_v";
                    break;
                case rt90_2_5_gon_o:
                    retVal = "rt90_2.5_gon_o";
                    break;
                case rt90_5_0_gon_o:
                    retVal = "rt90_5.0_gon_o";
                    break;
                default:
                    retVal = "rt90_2.5_gon_v";
                    break;
            }

            return retVal;
    }

    @Override
    public String toString() {
        return String.format("X: %f Y: %f, Projection %s", this.latitude, this.longitude, this.getProjectionString());
    }
}
