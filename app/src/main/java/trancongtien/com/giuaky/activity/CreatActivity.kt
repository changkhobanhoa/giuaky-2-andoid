package trancongtien.com.giuaky.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import trancongtien.com.giuaky.R

class CreatActivity : AppCompatActivity() {
    lateinit var btnSave:Button
    lateinit var name :String
    lateinit var age :String
    lateinit var address :String
    lateinit var phone :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creat)
        val jName=findViewById<TextView>(R.id.name)
        val jAdress=findViewById<TextView>(R.id.address)
        val jAge=findViewById<TextView>(R.id.age)
        val jPhone=findViewById<TextView>(R.id.phone)
        btnSave=findViewById(R.id.btn_create)

        btnSave.run {
            setOnClickListener{
                
            }
        }

    }
}