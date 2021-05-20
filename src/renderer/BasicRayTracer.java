package renderer;

import elements.LightSource;
import elements.Material;
import geometries.Geometry;
import geometries.Intersectable;
import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

/**
 * RayTracerBasic class represent a ray scanner on a scene
 *
 * @author Chani & Sara Lea
 */
public class BasicRayTracer extends RayTracerBase{

    /**
     * the constructor of RayTracerBase
     * @param scene the scene that the ray tracer scan
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * trace ray- get a ray and return the color of the closest intersection point with the ray
     * @param ray the ray
     * @return color
     */
    @Override
    public Color traceRay(Ray ray) {
        //find all the intersections of the ray with the geometries
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        //if there are intersections
        if (intersections != null) {
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersections); //find the closest intersection point with the ray
            return calcColor(closestPoint, ray); //return the color of the closest intersection point
        }//else- if there are no intersection points
        return scene.background; //return the background of the scene
    }

    /**
     * get a point on an object and return the color of this point
     * @param geoPoint the point on the object
     * @param ray the ray intersecting with the point
     * @return the color of the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        Color emissionColor = geoPoint.geometry.getEmission();
        Color basicColor = scene.ambientLight.getIntensity().add(emissionColor);
        return basicColor.add(calcLocalEffects(geoPoint, ray));
    }

    /**
     * get a GeoPoint on a certain object and a ray and find the color in this point by considering all the effects (lights) in the scene
     * @param intersection the intersection point in an object
     * @param ray the ray that was sending to the object
     * @return the color of the receiving geoPoint
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir (); //the direction of the ray
        Vector n = intersection.geometry.getNormal(intersection.point); //the normal vector of the geometry
        double nv = alignZero(n.dotProduct(v)); //the rate between the normal of the geometry and the direction of the ray
        if (nv == 0) return Color.BLACK; //if the ray doesn't hit the geometry, return black
        Material material = intersection.geometry.getMaterial(); //the material of the geometry
        int nShininess = material.getShininess();
        double kd = material.getKd(), ks = material.getKs();
        Color color = Color.BLACK;
        //pass the list of the lights of the scene
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point); //get the direction of the current light
            double nl = alignZero(n.dotProduct(l)); //the rate between the normal of the geometry and the direction of the light
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point); //calculate the intensity in the intersection point
                //calculate the color using the Phong model formula
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity)); //add the color of this light to the main color
            }
        }
        return color;
    }

    /**
     * the specular part in the Phong model formula
     * @return the color created in this part
     */
    private Color calcSpecular(double Ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity){
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        double vrMinus = v.scale(-1).dotProduct(r);
        double vrn = Math.pow(vrMinus, nShininess);
        return lightIntensity.scale(Ks * vrn);
    }

    /**
     * the diffusive part in the Phong model formula
     * @return the color created in this part
     */
    private Color calcDiffusive(double Kd, Vector l, Vector n, Color lightIntensity){
        double ln = Math.abs(l.dotProduct(n));
        return lightIntensity.scale(Kd * ln);
    }
}
