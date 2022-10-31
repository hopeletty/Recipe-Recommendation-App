package com.example.mydiet.Listeners;

import com.example.mydiet.Models.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse response, String message);
    void didError(String message); //message from api
}
