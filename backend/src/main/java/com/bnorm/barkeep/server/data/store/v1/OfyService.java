package com.bnorm.barkeep.server.data.store.v1;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Objectify service wrapper so we can statically register our persistence classes More on Objectify here :
 * https://code.google.com/p/objectify-appengine/
 */
public class OfyService {

    static {
        ObjectifyService.register(Ingredient.class);
        ObjectifyService.register(Recipe.class);
        ObjectifyService.register(Book.class);
        ObjectifyService.register(Bar.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}