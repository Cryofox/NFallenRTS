package coffeefoxstudios.model.utils;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ryder Stancescu on 4/16/2017.
 */
public class SpriteVault {
    private Map<String,Texture> textureVault;

    private static SpriteVault instance;
    public static SpriteVault getInstance()
    {
        if(instance==null)
            instance=new SpriteVault();
        return instance;
    }
    private SpriteVault()
    {
        storeImages();
    }

    private void storeImages()
    {
        textureVault = new HashMap<>();
        textureVault.put("CoffeeFox", new Texture("coffeefox.jpg"));
        textureVault.put("HeroPH", new Texture("Hero_PH.jpg"));
    }

    public Texture getTexture(String key)
    {
        if(textureVault.containsKey(key))
            return textureVault.get(key);

        return null;
    }

}
