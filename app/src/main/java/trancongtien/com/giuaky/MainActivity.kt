package trancongtien.com.giuaky

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import trancongtien.com.giuaky.activity.CreatActivity
import trancongtien.com.giuaky.adapter.AdapterData
import trancongtien.com.giuaky.apis.APIRequestData
import trancongtien.com.giuaky.apis.RetroServer
import trancongtien.com.giuaky.models.DataModel
import trancongtien.com.giuaky.models.ResponseModel

class MainActivity : AppCompatActivity() {
    lateinit var rvData: RecyclerView
    lateinit var adData: RecyclerView.Adapter<*>
    lateinit var lmData: RecyclerView.LayoutManager
    var listData: List<DataModel> = ArrayList<DataModel>()
    lateinit var srlData: SwipeRefreshLayout
    lateinit var pbdData: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvData = findViewById(R.id.rv_data)
        srlData = findViewById(R.id.srl_data)
        pbdData = findViewById(R.id.pb_data)
        var btnAdd = findViewById<FloatingActionButton>(R.id.btn_add)
        with(rvData) {
            this.layoutManager = lmData
        }
        with(srlData) {
            this.setOnRefreshListener(
                OnRefreshListener {
                    setRefreshing(true)
                    retrieveData()
                    setRefreshing(false)
                }
            )
        }
        btnAdd.setOnClickListener {
            startActivity(Intent(this, CreatActivity::class.java))
        }


    }

    override fun onResume() {
        super.onResume()
        retrieveData()
    }

    fun retrieveData() {
        pbdData.visibility = View.VISIBLE
        val ardData: APIRequestData =
            RetroServer().connectRetrofit().create(APIRequestData::class.java)
        val showData: Call<ResponseModel> = ardData.retrieve()
        showData.equals(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
              listData= response.body()?.getData()!!
                adData =AdapterData(applicationContext,listData)
                rvData.adapter=adData
                adData.notifyDataSetChanged()
                pbdData.visibility=View.INVISIBLE
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
               Toast.makeText(this@MainActivity,"error",Toast.LENGTH_SHORT).show()
                pbdData.visibility=View.INVISIBLE

            }
        })
    }
}