package coffeefox.model.Utils;

/**
 * Created by Ryder Stancescu on 4/16/2017.
 */
public class Vector2D {
    private float x,y;

    public Vector2D(int x, int y)
    {
        this.x = (float)x;
        this.y = (float)y;
    }
    public Vector2D(float angle)
    {
        this.x = (float)Math.cos( Math.toRadians(angle));
        this.y = (float)Math.sin( Math.toRadians(angle));
    }

    public float getX()
    {return x;}
    public float getY()
    {return y;}
    public void setX(float val)
    {x=val;}
    public void setY(float val)
    {y=val;}

    public void add(Vector2D vector)
    {
        this.x += vector.x;
        this.y += vector.y;
    }
    public void sub(Vector2D vector)
    {
        this.x -= vector.x;
        this.y -= vector.y;
    }

    public float dot(Vector2D vector)
    {
        this.x *= vector.x;
        this.y *= vector.y;

        return this.x + this.y;
    }


    public void mul(float val)
    {
        this.x*=val;
        this.y*=val;
    }


}
