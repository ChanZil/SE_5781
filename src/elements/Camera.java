package elements;
import primitives.*;

import static primitives.Util.isZero;

/**
 * Camera class represent the camera of the scene
 *
 * @author Chani & Sara Lea
 */
public class Camera {
    private Point3D p0;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double width;
    private double height;
    private double distance;

    /**
     * camera constructor- build a camera, create the vector vRight
     * @param p0 the location of the camera
     * @param vTo vector to
     * @param vUp vector up
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        //check if vectors vUP and vTo are verticals
        if (isZero(vTo.dotProduct(vUp))) {
            this.p0 = p0;
            this.vTo = vTo;
            this.vTo.normalized(); //normalize vTo
            this.vUp = vUp;
            this.vUp.normalized(); //normalize vUp
            this.vRight = this.vTo.crossProduct(this.vUp); //create vector vRight
        }
    }

    public Point3D getP0() {
        return p0;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvRight() {
        return vRight;
    }

    /**
     * set the view plane size
     * @param width the width of the view plane
     * @param height the height of the view plane
     * @return the current camera
     */
    public Camera setViewPlaneSize(double width, double height){
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * set the distance of the view plane
     * @param distance the distance of the view plane
     * @return the current camera
     */
    public Camera setDistance(double distance){
        this.distance = distance;
        return this;
    }

    /**
     * Construct a ray to pass through a certain pixel in the view plane
     *
     * @param nX amount of columns in the view plane
     * @param nY amount of rows in the view plane
     * @param j the column of the pixel we want
     * @param i the row of the pixel we want
     * @return the constructed ray
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        double wP = width / nX; //the width of the pixel
        double hP = height / nY; //the height of the pixel
        Point3D pTo = p0.add(vTo.scale(distance)); //the point that the vector vTo gets to
        double dJ = (j - (nX - 1) / 2d) * wP; //calculate the distance of the pixel from the center of the view plane in the direction of vRight
        double dI = -(i - (nY - 1) / 2d) * hP; //calculate the distance of the pixel from the center of the view plane in the direction of Up
        //if the pixel is not the center of the rows and not the center of the columns
        if (!isZero(dJ) && !isZero(dI))
            pTo = pTo.add(vRight.scale(dJ).add(vUp.scale(dI)));
        //if the pixel is the center of the rows and not the center of the columns
        if(isZero(dJ) && !isZero(dI))
            pTo = pTo.add(vUp.scale(dI));
        //if the pixel is not the center of the rows and the center of the columns
        if(!isZero(dJ) && isZero(dI))
            pTo = pTo.add(vRight.scale(dJ));
        return new Ray(p0, pTo.subtract(p0));
    }
}
