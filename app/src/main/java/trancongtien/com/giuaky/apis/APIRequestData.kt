package trancongtien.com.giuaky.apis

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import  trancongtien.com.giuaky.models.ResponseModel

interface APIRequestData {

    @GET("retrieve.php")
    fun retrieve(): Call<ResponseModel>

    @FormUrlEncoded
    @POST("create.php")
    fun createData(
        @Field("id") id:String,
        @Field("name") name: String,
        @Field("age") age: String,
        @Field("address") address: String,
        @Field("phone") phone: String
    ): Call<ResponseModel>

    @FormUrlEncoded
    @POST("delete.php")
    fun deleteData(@Field("id") id: String): Call<ResponseModel>

    @FormUrlEncoded
    @POST("get.php")
    fun getData(@Field("id") id: String): Call<ResponseModel>


    @FormUrlEncoded
    @POST("update.php")
    fun updateData(
        @Field("id") id :String,
        @Field("name") name: String,
        @Field("age") age: String,
        @Field("address") address: String,
        @Field("phone") phone: String
    ): Call<ResponseModel>
}