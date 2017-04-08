package com.bnorm.barkeep.data.api;

import com.bnorm.barkeep.data.api.model.Bar;
import com.bnorm.barkeep.data.api.model.Book;
import com.bnorm.barkeep.data.api.model.GaeList;
import com.bnorm.barkeep.data.api.model.Ingredient;
import com.bnorm.barkeep.data.api.model.Recipe;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BarkeepService {

    // ================= //
    // ***** Books ***** //
    // ================= //

    @GET("books")
    Single<Response<GaeList<Book>>> getBooks();

    @POST("book")
    Single<Response<Book>> createBook(@Body Book book);

    @GET("book/{id}")
    Single<Response<Book>> getBook(@Path("id") String id);

    @PUT("book/{id}")
    Single<Response<Book>> updateBook(@Path("id") String id, @Body Book book);

    @DELETE("book/{id}")
    Single<Response<Void>> deleteBook(@Path("id") String id);


    // ================= //
    // ***** Bars ***** //
    // ================= //

    @GET("bars")
    Single<Response<GaeList<Bar>>> getBars();

    @POST("bar")
    Single<Response<Bar>> createBar(@Body Bar bar);

    @GET("bar/{id}")
    Single<Response<Bar>> getBar(@Path("id") String id);

    @PUT("bar/{id}")
    Single<Response<Bar>> updateBar(@Path("id") String id, @Body Bar bar);

    @DELETE("bar/{id}")
    Single<Response<Void>> deleteBar(@Path("id") String id);


    // =================== //
    // ***** Recipes ***** //
    // =================== //

    @GET("recipes")
    Single<Response<GaeList<Recipe>>> getRecipes(@Query("name") String name);

    @GET("recipes")
    Single<Response<GaeList<Recipe>>> getRecipes();

    @POST("recipe")
    Single<Response<Recipe>> createRecipe(@Body Recipe recipe);

    @GET("recipe/{id}")
    Single<Response<Recipe>> getRecipe(@Path("id") String id);

    @PUT("recipe/{id}")
    Single<Response<Recipe>> updateRecipe(@Path("id") String id, @Body Recipe recipe);

    @DELETE("recipe/{id}")
    Single<Response<Void>> deleteRecipe(@Path("id") String id);


    // ======================= //
    // ***** Ingredients ***** //
    // ======================= //

    @GET("ingredients")
    Single<Response<GaeList<Ingredient>>> getIngredients(@Query("name") String name);

    @GET("ingredients")
    Single<Response<GaeList<Ingredient>>> getIngredients();

    @POST("ingredient")
    Single<Response<Ingredient>> createIngredient(@Body Ingredient ingredient);

    @GET("ingredient/{id}")
    Single<Response<Ingredient>> getIngredient(@Path("id") long id);

    @PUT("ingredient/{id}")
    Single<Response<Ingredient>> updateIngredient(@Path("id") long id, @Body Ingredient ingredient);

    @DELETE("ingredient/{id}")
    Single<Response<Void>> deleteIngredient(@Path("id") long id);
}
