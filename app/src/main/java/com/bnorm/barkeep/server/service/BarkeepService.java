package com.bnorm.barkeep.server.service;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

// htts://barkeep.beefyhost.com/api/v1/
public interface BarkeepService {

    @GET("ingredients/{ingredient}")
    Call<ResponseBody> getIngredient(@Path("ingredient") String ingredient);

    @PUT("ingredients/{ingredient}")
    Call<ResponseBody> updateIngredient(@Path("ingredient") String ingredient, @Body RequestBody body);

    @POST("ingredients/{ingredient}")
    Call<ResponseBody> createIngredient(@Path("ingredient") String ingredient, @Body RequestBody body);

    @DELETE("ingredients/{ingredient}")
    Call<ResponseBody> deleteIngredient(@Path("ingredient") String ingredient);

    @GET("ingredients")
    Call<ResponseBody> getIngredients();


    @GET("books/{book}")
    Call<ResponseBody> getBook(@Path("book") String book);

    @PUT("books/{book}")
    Call<ResponseBody> updateBook(@Path("book") String book, @Body RequestBody body);

    @POST("books/{book}")
    Call<ResponseBody> createBook(@Path("book") String book, @Body RequestBody body);

    @DELETE("books/{book}")
    Call<ResponseBody> deleteBook(@Path("book") String book);

    @GET("books}")
    Call<ResponseBody> getBooks();


    @GET("books/{book}/recipes/{recipe}")
    Call<ResponseBody> getRecipe(@Path("book") String book, @Path("recipe") String recipe);

    @PUT("books/{book}/recipes/{recipe}")
    Call<ResponseBody> updateRecipe(@Path("book") String book, @Path("recipe") String recipe, @Body RequestBody body);

    @POST("books/{book}/recipes/{recipe}")
    Call<ResponseBody> createRecipe(@Path("book") String book, @Path("recipe") String recipe, @Body RequestBody body);

    @DELETE("books/{book}/recipes/{recipe}")
    Call<ResponseBody> deleteRecipe(@Path("book") String book, @Path("recipe") String recipe);

    @GET("books/{book}/recipes")
    Call<ResponseBody> getRecipes(@Path("book") String book);

    @GET("books/{book}/recipes")
    Call<ResponseBody> getRecipes(@Path("book") String book, @Query("name") String name);


    @GET("bars/{bar}")
    Call<ResponseBody> getBar(@Path("bar") String bar);

    @PUT("bars/{bar}")
    Call<ResponseBody> updateBar(@Path("bar") String bar, @Body RequestBody body);

    @POST("bars/{bar}")
    Call<ResponseBody> createBar(@Path("bar") String bar, @Body RequestBody body);

    @DELETE("bars/{bar}")
    Call<ResponseBody> deleteBar(@Path("bar") String bar);

    @GET("bars")
    Call<ResponseBody> getBars();
}
