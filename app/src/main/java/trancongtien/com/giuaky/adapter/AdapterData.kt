package trancongtien.com.giuaky.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import trancongtien.com.giuaky.MainActivity
import trancongtien.com.giuaky.R
import trancongtien.com.giuaky.activity.EditActivity
import trancongtien.com.giuaky.apis.APIRequestData
import trancongtien.com.giuaky.apis.RetroServer
import trancongtien.com.giuaky.models.DataModel
import trancongtien.com.giuaky.models.ResponseModel
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.logging.Handler

class AdapterData( ctx : Context,private val mList: List<DataModel>) :
    RecyclerView.Adapter<AdapterData.ViewHolder>() {
    lateinit var id: String
      var ctx: Context= ctx
    lateinit var listData: List<DataModel>
    lateinit var listClear: List<DataModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        holder.name.text = item.name
        holder.age.text = item.age
        holder.address.text = item.address
        holder.phone.text = item.phone
        holder.id.text = item.id
        holder.itemView.setOnLongClickListener(
            object : View.OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    val dialogText: AlertDialog.Builder = AlertDialog.Builder(ctx)
                    dialogText.setIcon(R.mipmap.ic_launcher_round)
                    dialogText.setCancelable(true)
                    id = holder.id.text.toString()
                    dialogText.setPositiveButton(
                        "Chuc mung",
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            deleteDt()
                            dialogInterface.dismiss()
                            val backgroundExecutor: ScheduledExecutorService =
                                Executors.newSingleThreadScheduledExecutor()
                            backgroundExecutor.schedule({
                                (ctx as MainActivity).retrieveData()
                            }, 3, TimeUnit.SECONDS)

                        })
                    dialogText.setNegativeButton("Edit",
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            getDta()
                            dialogInterface.dismiss()



                        })
                    dialogText.show()

                    return false
                }


            }
        )
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun deleteDt() {
        var data: APIRequestData =
            RetroServer().connectRetrofit().create(APIRequestData::class.java)
        var getDt: Call<ResponseModel> = data.deleteData(id)

        getDt.enqueue(object : Callback<ResponseModel> {


            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(ctx, "Error", Toast.LENGTH_SHORT).show()
                // Handle failure case
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                val message = response.body()?.message
                val id = response.body()?.uid
                Toast.makeText(ctx, "Success", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getDta() {
        var data: APIRequestData =
            RetroServer().connectRetrofit().create(APIRequestData::class.java)
        var getData: Call<ResponseModel> = data.getData(id)
        getData.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                var uid = response.body()?.uid
                var message = response.body()?.message
                 listClear= response.body()?.getData()!!


                var id = listClear.get(0).id
                var name = listClear.get(0).name
                var age = listClear.get(0).age
                var address = listClear.get(0).address
                var phone = listClear.get(0).phone


                var d: Intent = Intent(ctx, EditActivity::class.java)
                d.putExtra("id", id)
                d.putExtra("name", name)
                d.putExtra("age", age)
                d.putExtra("address", address)
                d.putExtra("phone", phone)
                ctx.startActivities(arrayOf(d))
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(ctx, "Error", Toast.LENGTH_SHORT).show()
                // Handle failure case
            }
        })


    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val name: TextView = ItemView.findViewById(R.id.tv_name)
        val age: TextView = ItemView.findViewById(R.id.tv_age)
        val address: TextView = ItemView.findViewById(R.id.tv_address)
        val phone: TextView = ItemView.findViewById(R.id.tv_phone)
        val id: TextView = ItemView.findViewById(R.id.tv_id)


    }


}