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

    //private static final double DELTA = 0.1;
    private static final double INITIAL_K = 1.0;
    //stop conditions of reflection recursion:
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

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
     * find the closest intersection geoPoint between the ray and the object
     * @param ray the intersected ray
     * @return the closest geoPoint- if there is not, null
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> pointsList =  scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(pointsList);
    }


    /**
     * Check if there is a shadow on the geometry object
     * @param light the kind of light source on the object
     * @param l the direction of the ray that intersect with the object
     * @param n the normal of the ray
     * @param point the intersection point of the ray with the object
     * @return true if there is no shadow, otherwise- false
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, Point3D point) {
        Vector dir = l.scale(-1);
        Ray lightRay = new Ray(point, dir, n);
        //search for intersections point only between the position of the light source and the object
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(point));
        if( intersections == null)
            return true;
        double lightDistance = light.getDistance(point);
        for (GeoPoint gp : intersections) {
            if(alignZero(gp.point.distance(point) - lightDistance) <= 0 && gp.geometry.getMaterial().kt == 0)
                return false;
        }
        return true;
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

    /**
     * create the refracted ray of a given ray
     * @param point the start of the given ray
     * @param v the direction of the given ray
     * @param n the normal of the given ray
     * @return the refracted ray
     */
    private Ray constructRefractedRay(Point3D point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    /**
     * create the reflected ray of a given ray
     * @param point the start of the ray
     * @param v the direction of the given ray
     * @param n the normal of the given ray
     * @return the reflected ray
     */
    private Ray constructReflectedRay(Point3D point, Vector v, Vector n) {
        //𝒓 = 𝒗 − 𝟐 ∙ (𝒗 ∙ 𝒏 ) ∙ 𝒏
        Vector r = v.subtract(n.scale(v.dotProduct(n)*2));
        return new Ray(point, r, n);
    }

    /**
     * find the color of a given intersection point
     * @param geoPoint the point on the object
     * @param ray the ray intersecting with the point
     * @return the color of the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     *
     * @param geoPoint
     * @param ray
     * @param level
     * @param k
     * @return
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, double k) {
        Color color = geoPoint.geometry.getEmission();
        color = color.add(calcLocalEffects(geoPoint, ray, k));
        if(level == 1){
            return color;
        }
        return  color.add(calcGlobalEffects(geoPoint, ray.getDir(), level, k));
    }

    /**
     * get a GeoPoint on a certain object and a ray and find the color in this point by considering all the effects (lights) in the scene
     * @param intersection the intersection point in an object
     * @param ray the ray that was sending to the object
     * @return the color of the receiving geoPoint
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
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
                double ktr = transparency(lightSource, l, n, intersection);
                if (ktr * k > MIN_CALC_COLOR_K) {
                //if (unshaded(lightSource, l, n, intersection.point)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr); //calculate the intensity in the intersection point
                    //calculate the color using the Phong model formula
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity)); //add the color of this light to the main color
                }
            }
        }
        return color;
    }

    /**
     *
     * @param gp
     * @param v
     * @param level
     * @param k
     * @return
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        double kkr = k * material.kr;
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kr, kkr);
        double kkt = k * material.kt;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kt, kkt));
        return color;
    }

    /**
     *
     * @param ray
     * @param level
     * @param kx
     * @param kkx
     * @return
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection (ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)
        ).scale(kx);
    }

    /**
     *
     * @param light
     * @param l
     * @param n
     * @param geopoint
     * @return
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Point3D point = geopoint.point;
        Ray lightRay = new Ray(point, lightDirection, n);

        double lightDistance = light.getDistance(point);
        var intersections = scene.geometries.findGeoIntersections(lightRay, lightDistance);
        if (intersections == null) return 1.0;

        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(point) - lightDistance) <= 0) {
                ktr *= gp.geometry.getMaterial().getKt();
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
            }
        }
        return ktr;
    }
}
