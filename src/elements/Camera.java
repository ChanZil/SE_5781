package elements;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;
import static primitives.Util.random;

/**
 * Camera class represent the camera of the scene
 *
 * @author Chani & Sara Lea
 */
public class Camera {
    private Point3D p0; //the position of the camera
    //the vectors of the camera:
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    public int beamOfRay ; //number of rays
    public Point3D _pTo;
    private double width; //the width of the view plane
    private double height; //the height of the view plane
    private double distance; //the distance between the camera and the view plane

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

    //getters:

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

    //chaining setters:

    public Camera setViewPlaneSize(double width, double height){
        this.width = width;
        this.height = height;
        return this;
    }

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
        _pTo=pTo;
        return new Ray(p0, pTo.subtract(p0));
    }

    public Camera setBeamOfRay(int beamOfRay) {
        this.beamOfRay = beamOfRay;
        return this;
    }

    public List<Ray> constructRayThroughPixelSuperSample(int nX, int nY, int j, int i) {

        List<Ray> rays = new LinkedList<>();

        rays.add(constructRayThroughPixel( nX,  nY,  j,  i));//the point that the vector vTo gets to

        Point3D pc = _pTo;

        //Two random variables from which we get a dot within the pixel range
        double randomNumber1,randomNumber2;

        //We get 2 numbers and create a new dot from
        // it that comes out inside the pixel and is
        // different from the center of the pixel and
        // create a new beam for that dot

        for (int k = 0; k < beamOfRay; k++) {
            randomNumber1 = random(-1*width / nX/2,width / nX/2);
            randomNumber2= random(-1*height / nY/2,height / nY/2);
            Point3D P = new Point3D(pc.getX()+randomNumber1,pc.getY()+randomNumber2,pc.getZ());
            rays.add(new Ray(p0, P.subtract(p0)));
        }
        return rays;
    }
}
