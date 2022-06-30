package com.qentelli.automation.utilities;

import com.qentelli.automation.common.World;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class CustomResourceBundle extends ResourceBundle {

    private ResourceBundle bundle;
    private World world;

    public CustomResourceBundle(String baseName, Locale locale, World world){
        bundle = getBundle(baseName, locale);
        this.world = world;
    }

    @Override
    protected Object handleGetObject(String s) {
        Object val = null;
        if(world.isMobile()){
            try {
                val = bundle.getObject(s + "_Mobile");
            }catch (MissingResourceException ignore){}
        }
        if(val == null) val = bundle.getObject(s);
        return val;
    }

    @Override
    public Enumeration<String> getKeys() {
        return bundle.getKeys();
    }
}
