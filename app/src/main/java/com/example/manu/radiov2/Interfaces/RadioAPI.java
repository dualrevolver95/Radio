package com.example.manu.radiov2.Interfaces;


import com.example.manu.radiov2.Classes.Program.Program;
import com.example.manu.radiov2.Classes.StreamInfo.StreamingInfo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RadioAPI {


    @GET("streaminfo.get")
    Call<StreamingInfo> getInfo();

    @GET("json")
    Call<Program> getProgram();

}
