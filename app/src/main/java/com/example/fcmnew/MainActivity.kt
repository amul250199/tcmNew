package com.example.fcmnew

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TOPIC="/topics/myTopic"

class MainActivity : AppCompatActivity() {
    lateinit var edtTitle: EditText
    lateinit var edtMessage: EditText
    lateinit var edtToken: EditText

    lateinit var btnSend: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        edtTitle=findViewById(R.id.edtTitle)
        edtMessage=findViewById(R.id.edtMessage)
        edtToken=findViewById(R.id.edtToken)

        btnSend=findViewById(R.id.btnSend)
        btnSend.setOnClickListener {
            val title=edtTitle.text.toString()
            val message=edtMessage.text.toString()

            PushNotification(NotificationData(title,message), TOPIC).also {
                sendNotification(it)
            }


        }
    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response=RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful)
            {
                Log.e("response","response: ${Gson().toJson(response)}")
            }
            else
            {
                Log.e("response error","response error="+response.errorBody().toString())
            }

        }catch(ex: Exception)
        {
            Log.e("Error","error="+ex.toString())
        }
    }
}