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

/**
 * Signals that an error has been reached unexpectedly while parsing or formatting. 
 * 
 */
public class FormatException extends Exception {

    /**
     * Creates a new instance of <code>FormatException</code> without detail message.
     */
    public FormatException() {
    }


    /**
     * Constructs an instance of <code>FormatException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public FormatException(String msg) {
        super(msg);
    }
}
