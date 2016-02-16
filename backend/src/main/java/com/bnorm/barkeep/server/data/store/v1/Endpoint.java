package com.bnorm.barkeep.server.data.store.v1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a Google Cloud Endpoints RESTful API
 * with an Objectify entity. It provides no data access restrictions and no data validation.
 *
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(name = "endpoint",
     version = "v1",
     namespace = @ApiNamespace(ownerDomain = "server.barkeep.bnorm.com",
                               ownerName = "BNORM Software",
                               packagePath = "data/store/v1"))
public class Endpoint {

    private static final Logger log = LoggerFactory.getLogger(Endpoint.class);

    private static final int DEFAULT_LIST_LIMIT = 20;

    /**
     * Returns the {@link Book} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Book} with the provided ID.
     */
    @ApiMethod(name = "getBook",
               path = "book/{id}",
               httpMethod = ApiMethod.HttpMethod.GET)
    public Book getBook(@Named("id") String id) throws NotFoundException {
        log.info("Getting Recipe with ID: " + id);
        Book book = OfyService.ofy().load().type(Book.class).id(id).now();
        if (book == null) {
            throw new NotFoundException("Could not find Book with ID: " + id);
        }
        return book;
    }

    /**
     * Inserts a new {@code Books}.
     */
    @ApiMethod(name = "insertBook",
               path = "book",
               httpMethod = ApiMethod.HttpMethod.POST)
    public Book insertBook(Book book) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that Recipe.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        OfyService.ofy().save().entity(book).now();
        log.info("Created Book with ID: " + book.getName());

        return OfyService.ofy().load().entity(book).now();
    }

    /**
     * Updates an existing {@code Book}.
     *
     * @param id the ID of the entity to be updated
     * @param book the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing {@code Book}
     */
    @ApiMethod(name = "updateBook",
               path = "book/{id}",
               httpMethod = ApiMethod.HttpMethod.PUT)
    public Book updateBook(@Named("id") String id, Book book) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkBookExists(id);
        OfyService.ofy().save().entity(book).now();
        log.info("Updated Book: " + book);
        return OfyService.ofy().load().entity(book).now();
    }

    /**
     * Deletes the specified {@code Book}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing {@code Book}
     */
    @ApiMethod(name = "removeBook",
               path = "book/{id}",
               httpMethod = ApiMethod.HttpMethod.DELETE)
    public void removeBook(@Named("id") String id) throws NotFoundException {
        checkBookExists(id);
        OfyService.ofy().delete().type(Book.class).id(id).now();
        log.info("Deleted Book with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(name = "listBooks",
               path = "books",
               httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Book> listBooks(@Nullable @Named("cursor") String cursor,
                                              @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Book> query = OfyService.ofy().load().type(Book.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Book> queryIterator = query.iterator();
        List<Book> bookList = new ArrayList<>(limit);
        while (queryIterator.hasNext()) {
            bookList.add(queryIterator.next());
        }

        CollectionResponse.Builder<Book> builder = CollectionResponse.builder();
        builder.setItems(bookList);
        if (bookList.size() == limit) {
            builder.setNextPageToken(queryIterator.getCursor().toWebSafeString());
        }
        return builder.build();
    }

    private void checkBookExists(String id) throws NotFoundException {
        try {
            OfyService.ofy().load().type(Book.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Book with ID: " + id);
        }
    }

    /**
     * Returns the {@link Bar} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Book} with the provided ID.
     */
    @ApiMethod(name = "getBar",
               path = "bar/{id}",
               httpMethod = ApiMethod.HttpMethod.GET)
    public Bar getBar(@Named("id") String id) throws NotFoundException {
        log.info("Getting Bar with ID: " + id);
        Bar bar = OfyService.ofy().load().type(Bar.class).id(id).now();
        if (bar == null) {
            throw new NotFoundException("Could not find Bar with ID: " + id);
        }
        return bar;
    }

    /**
     * Inserts a new {@code Bars}.
     */
    @ApiMethod(name = "insertBar",
               path = "bar",
               httpMethod = ApiMethod.HttpMethod.POST)
    public Bar insertBar(Bar bar) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that Recipe.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        OfyService.ofy().save().entity(bar).now();
        log.info("Created Bar with ID: " + bar.getName());

        return OfyService.ofy().load().entity(bar).now();
    }

    /**
     * Updates an existing {@code Bar}.
     *
     * @param id the ID of the entity to be updated
     * @param bar the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing {@code Bar}
     */
    @ApiMethod(name = "updateBar",
               path = "bar/{id}",
               httpMethod = ApiMethod.HttpMethod.PUT)
    public Bar updateBar(@Named("id") String id, Bar bar) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkBarExists(id);
        OfyService.ofy().save().entity(bar).now();
        log.info("Updated Bar: " + bar);
        return OfyService.ofy().load().entity(bar).now();
    }

    /**
     * Deletes the specified {@code Bar}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing {@code Bar}
     */
    @ApiMethod(name = "removeBar",
               path = "bar/{id}",
               httpMethod = ApiMethod.HttpMethod.DELETE)
    public void removeBar(@Named("id") String id) throws NotFoundException {
        checkBookExists(id);
        OfyService.ofy().delete().type(Bar.class).id(id).now();
        log.info("Deleted Bar with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(name = "listBars",
               path = "bars",
               httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Bar> listBars(@Nullable @Named("cursor") String cursor,
                                            @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Bar> query = OfyService.ofy().load().type(Bar.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Bar> queryIterator = query.iterator();
        List<Bar> barList = new ArrayList<>(limit);
        while (queryIterator.hasNext()) {
            barList.add(queryIterator.next());
        }

        CollectionResponse.Builder<Bar> builder = CollectionResponse.builder();
        builder.setItems(barList);
        if (barList.size() == limit) {
            builder.setNextPageToken(queryIterator.getCursor().toWebSafeString());
        }
        return builder.build();
    }

    private void checkBarExists(String id) throws NotFoundException {
        try {
            OfyService.ofy().load().type(Bar.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Bar with ID: " + id);
        }
    }

    /**
     * Returns the {@link Recipe} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Recipe} with the provided ID.
     */
    @ApiMethod(name = "getRecipe",
               path = "recipe/{id}",
               httpMethod = ApiMethod.HttpMethod.GET)
    public Recipe getRecipe(@Named("id") String id) throws NotFoundException {
        log.info("Getting Recipe with ID: " + id);
        Recipe recipe = OfyService.ofy().load().type(Recipe.class).id(id).now();
        if (recipe == null) {
            throw new NotFoundException("Could not find Recipe with ID: " + id);
        }
        return recipe;
    }

    /**
     * Inserts a new {@code Recipe}.
     */
    @ApiMethod(name = "insertRecipe",
               path = "recipe",
               httpMethod = ApiMethod.HttpMethod.POST)
    public Recipe insertRecipe(Recipe recipe) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that Recipe.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        OfyService.ofy().save().entity(recipe).now();
        log.info("Created Recipe with ID: " + recipe.getName());

        return OfyService.ofy().load().entity(recipe).now();
    }

    /**
     * Updates an existing {@code Recipe}.
     *
     * @param id the ID of the entity to be updated
     * @param recipe the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing {@code Recipe}
     */
    @ApiMethod(name = "updateRecipe",
               path = "recipe/{id}",
               httpMethod = ApiMethod.HttpMethod.PUT)
    public Recipe updateRecipe(@Named("id") String id, Recipe recipe) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkRecipeExists(id);
        OfyService.ofy().save().entity(recipe).now();
        log.info("Updated Recipe: " + recipe);
        return OfyService.ofy().load().entity(recipe).now();
    }

    /**
     * Deletes the specified {@code Recipe}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing {@code Recipe}
     */
    @ApiMethod(name = "removeRecipe",
               path = "recipe/{id}",
               httpMethod = ApiMethod.HttpMethod.DELETE)
    public void removeRecipe(@Named("id") String id) throws NotFoundException {
        checkRecipeExists(id);
        OfyService.ofy().delete().type(Recipe.class).id(id).now();
        log.info("Deleted Recipe with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(name = "listRecipes",
               path = "recipes",
               httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Recipe> listRecipes(@Nullable @Named("cursor") String cursor,
                                                  @Nullable @Named("limit") Integer limit,
                                                  @Nullable @Named("name") String name,
                                                  @Nullable @Named("ingredient") List<String> ingredients) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Recipe> query = OfyService.ofy().load().type(Recipe.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        Set<String> words = Collections.emptySet();
        if (name != null) {
            query = query.order("nameWords");
            words = new HashSet<>(Arrays.asList(name.toLowerCase().split(" ")));
            for (String word : words) {
                query = query.filter("nameWords >=", word);
            }
        }
        if (ingredients != null) {
            for (String ingredient : ingredients) {
                query = query.filter("components.ingredients", Key.create(Ingredient.class, ingredient));
            }
        }
        QueryResultIterator<Recipe> queryIterator = query.iterator();
        List<Recipe> recipeList = new ArrayList<>(limit);
        while (queryIterator.hasNext()) {
            Recipe next = queryIterator.next();
            if (matches(words, next.getNameWords())) {
                recipeList.add(next);
            }
        }

        CollectionResponse.Builder<Recipe> builder = CollectionResponse.builder();
        builder.setItems(recipeList);
        if (recipeList.size() == limit) {
            builder.setNextPageToken(queryIterator.getCursor().toWebSafeString());
        }
        return builder.build();
    }

    private void checkRecipeExists(String id) throws NotFoundException {
        try {
            OfyService.ofy().load().type(Recipe.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Recipe with ID: " + id);
        }
    }

    /**
     * Returns the {@link Ingredient} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Ingredient} with the provided ID.
     */
    @ApiMethod(name = "getIngredient",
               path = "ingredient/{id}",
               httpMethod = ApiMethod.HttpMethod.GET)
    public Ingredient getIngredient(@Named("id") String id) throws NotFoundException {
        log.info("Getting Ingredient with ID: " + id);
        Ingredient ingredient = OfyService.ofy().load().type(Ingredient.class).id(id).now();
        if (ingredient == null) {
            throw new NotFoundException("Could not find Ingredient with ID: " + id);
        }
        return ingredient;
    }

    /**
     * Inserts a new {@code Ingredient}.
     */
    @ApiMethod(name = "insertIngredient",
               path = "ingredient",
               httpMethod = ApiMethod.HttpMethod.POST)
    public Ingredient insertIngredient(Ingredient ingredient) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that Ingredient.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        OfyService.ofy().save().entity(ingredient).now();
        log.info("Created Ingredient with ID: " + ingredient.getName());

        return OfyService.ofy().load().entity(ingredient).now();
    }

    /**
     * Updates an existing {@code Ingredient}.
     *
     * @param id the ID of the entity to be updated
     * @param ingredient the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing {@code Ingredient}
     */
    @ApiMethod(name = "updateIngredient",
               path = "ingredient/{id}",
               httpMethod = ApiMethod.HttpMethod.PUT)
    public Ingredient updateIngredient(@Named("id") String id, Ingredient ingredient) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkIngredientExists(id);
        OfyService.ofy().save().entity(ingredient).now();
        log.info("Updated Ingredient: " + ingredient);
        return OfyService.ofy().load().entity(ingredient).now();
    }

    /**
     * Deletes the specified {@code Ingredient}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing {@code Ingredient}
     */
    @ApiMethod(name = "removeIngredient",
               path = "ingredient/{id}",
               httpMethod = ApiMethod.HttpMethod.DELETE)
    public void removeIngredient(@Named("id") String id) throws NotFoundException {
        checkIngredientExists(id);
        OfyService.ofy().delete().type(Ingredient.class).id(id).now();
        log.info("Deleted Ingredient with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(name = "listIngredients",
               path = "ingredient",
               httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Ingredient> listIngredients(@Nullable @Named("cursor") String cursor,
                                                          @Nullable @Named("limit") Integer limit,
                                                          @Nullable @Named("name") String name) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Ingredient> query = OfyService.ofy().load().type(Ingredient.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        Set<String> words = Collections.emptySet();
        if (name != null) {
            query = query.order("nameWords");
            words = new HashSet<>(Arrays.asList(name.toLowerCase().split(" ")));
            for (String word : words) {
                query = query.filter("nameWords >=", word);
            }
        }
        QueryResultIterator<Ingredient> queryIterator = query.iterator();
        List<Ingredient> ingredientList = new ArrayList<>(limit);
        while (queryIterator.hasNext()) {
            Ingredient next = queryIterator.next();
            if (matches(words, next.getNameWords())) {
                ingredientList.add(next);
            }
        }

        CollectionResponse.Builder<Ingredient> builder = CollectionResponse.builder();
        builder.setItems(ingredientList);
        if (ingredientList.size() == limit) {
            builder.setNextPageToken(queryIterator.getCursor().toWebSafeString());
        }
        return builder.build();
    }

    private void checkIngredientExists(String id) throws NotFoundException {
        try {
            OfyService.ofy().load().type(Ingredient.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Ingredient with ID: " + id);
        }
    }

    private boolean matches(Set<String> searchWords, Set<String> nameWords) {
        boolean match = true;
        for (String searchWord : searchWords) {
            match = false;
            for (String nameWord : nameWords) {
                if (nameWord.startsWith(searchWord)) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                break;
            }
        }
        return match;
    }
}