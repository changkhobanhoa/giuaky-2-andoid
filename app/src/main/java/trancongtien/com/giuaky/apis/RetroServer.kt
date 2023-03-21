package trancongtien.com.giuaky.apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroServer {
    var  baseUrl :String ="https/trancongtien/"
    lateinit var retro: Retrofit
    fun connectRetrofit(): Retrofit {
        retro = Retrofit.Builder()
            .baseUrl("http://dev.2dev4u.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retro
    }
}