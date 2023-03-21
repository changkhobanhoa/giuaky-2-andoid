package trancongtien.com.giuaky.models

class ResponseModel {
    lateinit var uid: String
    lateinit var message: String
    lateinit var data: List<DataModel>
    fun getData(): List<DataModel> {
        return data
    }
}