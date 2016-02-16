package com.bnorm.barkeep.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.LogManager;
import javax.xml.bind.JAXBException;

import com.bnorm.barkeep.measurement.UnitType;
import com.bnorm.barkeep.server.data.store.IngredientFactory;
import com.bnorm.barkeep.server.data.store.v1.Amount;
import com.bnorm.barkeep.server.data.store.v1.Bar;
import com.bnorm.barkeep.server.data.store.v1.Book;
import com.bnorm.barkeep.server.data.store.v1.Endpoint;
import com.bnorm.barkeep.server.data.store.v1.Ingredient;
import com.bnorm.barkeep.server.msg.xml.v2.Component;
import com.bnorm.barkeep.server.msg.xml.v2.Recipe;
import com.bnorm.barkeep.server.msg.xml.v2.RecipeSerializer;
import com.bnorm.barkeep.server.msg.xml.v2.Root;
import com.bnorm.barkeep.server.wrapper.ApiWrapper;
import com.bnorm.barkeep.server.wrapper.LocalWrapper;
import com.google.appengine.repackaged.com.google.common.collect.Sets;

public class LoadData {
    public static void main(String[] args) throws Exception {
        LogManager.getLogManager().reset();
        ApiWrapper wrapper = new LocalWrapper();
        wrapper.initialize();
        try {
            run();
        } finally {
            wrapper.terminate();
        }
    }

    public static Path getResource(String stringPath) {
        URL resource = ClassLoader.getSystemResource(stringPath);
        try {
            URI uri = resource.toURI();
            try {
                return Paths.get(uri);
            } catch (FileSystemNotFoundException e) {
                try {
                    FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
                } catch (IOException e1) {
                    throw new AssertionError(e1);
                }
                return Paths.get(uri);
            }
        } catch (URISyntaxException e) {
            throw new AssertionError("", e);
        }
    }

    public static void run() throws IOException, JAXBException {
        Endpoint endpoint = new Endpoint();
        IngredientFactory ingredientFactory = new IngredientFactory(endpoint);

        Path path = getResource("ultimate_bar_book_v2.xml");
        InputStream stream = Files.newInputStream(path);
        Root root = RecipeSerializer.instance.unmarshal(stream);

        SortedSet<Ingredient> allIngredients = new TreeSet<>(new Comparator<Ingredient>() {
            @Override
            public int compare(Ingredient o1, Ingredient o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        Book emptyBook = new Book();
        emptyBook.setName("Empty Book 1");
        endpoint.insertBook(emptyBook);

        emptyBook.setName("Empty Book 2");
        endpoint.insertBook(emptyBook);

        Book storeBook = new Book();
        storeBook.setName("Ultimate Bar Book");
        List<com.bnorm.barkeep.server.data.store.v1.Recipe> bookRecipes = new ArrayList<>();
        storeBook.setRecipes(bookRecipes);

        for (Recipe msgRecipe : root.getRecipe()) {
            System.out.println(msgRecipe.getName());

            String picture = msgRecipe.getPicture();

            String description = msgRecipe.getDescription();
            if (description != null) {
                description = description.replaceAll("\n            ", " ");
                description = description.replaceAll("\n        ", "");
                if (!description.isEmpty()) {
                    System.out.println("   Description : " + description);
                }
            }

            String directions = msgRecipe.getDirections();
            if (directions != null) {
                directions = directions.replaceAll("\n            ", " ");
                directions = directions.replaceAll("\n        ", " ");
                if (!directions.isEmpty()) {
                    System.out.println("   Directions : " + directions);
                }
            }

            List<com.bnorm.barkeep.server.data.store.v1.Component> components = new ArrayList<>();
            for (Component msgComponent : msgRecipe.getComponent()) {
                StringBuilder builder = new StringBuilder("   ");
                boolean first = true;
                List<com.bnorm.barkeep.server.data.store.v1.Ingredient> ingredients = new ArrayList<>();
                for (com.bnorm.barkeep.server.msg.xml.v2.Ingredient msgIngredient : msgComponent.getIngredient()) {
                    if (!first) {
                        builder.append(" or ");
                    }
                    builder.append(msgIngredient.getName());
                    first = false;

                    Ingredient ingredient = ingredientFactory.create(msgIngredient.getName(),
                                                                     UnitType.valueOf(msgIngredient.getType()));
                    ingredients.add(ingredient);
                    allIngredients.add(ingredient);
                }
                builder.append(" : ");

                if (msgComponent.getRecommended() != null) {
                    builder.append(msgComponent.getRecommended());
                } else {
                    builder.append(msgComponent.getMin()).append(" to ").append(msgComponent.getMax());
                }

                builder.append(" ").append(msgComponent.getUnit());
                System.out.println(builder);

                Amount amount = new Amount();
                amount.setRecommended(msgComponent.getRecommended());
                amount.setMin(msgComponent.getMin());
                amount.setMax(msgComponent.getMax());

                com.bnorm.barkeep.server.data.store.v1.Component storeComponent = new com.bnorm.barkeep.server.data.store.v1.Component();
                storeComponent.setAmount(amount);
                storeComponent.setIngredients(ingredients);
                storeComponent.setUnit(msgComponent.getUnit());

                components.add(storeComponent);
            }

            com.bnorm.barkeep.server.data.store.v1.Recipe storeRecipe = new com.bnorm.barkeep.server.data.store.v1.Recipe();
            storeRecipe.setName(msgRecipe.getName());
            storeRecipe.setNameWords(Sets.newHashSet(msgRecipe.getName().toLowerCase().split(" ")));
            storeRecipe.setPicture(picture);
            storeRecipe.setDescription(description);
            storeRecipe.setComponents(components);
            storeRecipe.setDirections(directions);

            endpoint.insertRecipe(storeRecipe);
            bookRecipes.add(storeRecipe);
        }

        endpoint.insertBook(storeBook);


        Bar bar = new Bar();

        bar.setName("Empty Bar 1");
        endpoint.insertBar(bar);

        bar.setName("Empty Bar 2");
        endpoint.insertBar(bar);

        bar.setName("Ultimate Bar");
        bar.setIngredients(new ArrayList<>(allIngredients));
        endpoint.insertBar(bar);
    }
}
