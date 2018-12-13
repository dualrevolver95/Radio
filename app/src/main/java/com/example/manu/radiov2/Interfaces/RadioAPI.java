package com.example.manu.radiov2.Interfaces;

import com.example.manu.radiov2.Classes.AProgram;
import com.example.manu.radiov2.Classes.StreamingInfo;
import com.example.manu.radiov2.Classes.Track;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface RadioAPI {

    @GET("/json")
    Call<AProgram> getProgram();

    @GET("streaminfo.get")
    Call<StreamingInfo> getInfo();

}
