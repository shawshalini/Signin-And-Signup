package com.example.signinapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class SignedIn : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signed_in)
        val sharepref=this?.getPreferences(Context.MODE_PRIVATE)?:return
        val email=intent.getStringExtra("email")
        val isLogin=sharepref.getString("Email","1")
        if(isLogin=="1") {
            var email=intent.getStringExtra("email")
            if(email!=null) {
                setText(email)
                with(sharepref.edit()) {
                    putString("Email", email)
                    apply()

                }
            }
            else{
                var intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        else{
                setText(isLogin)
//            val intent= Intent(this,MainActivity::class.java)
//            startActivity(intent)
//            finish()
        }
        val logout=findViewById<Button>(R.id.LogOut)
        logout.setOnClickListener {
            sharepref.edit().remove("Email").apply()
            var intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun setText(email:String?){
        val name=findViewById<TextView>(R.id.NameLog)
        val phone=findViewById<TextView>(R.id.PhoneLog)
        val emailLog=findViewById<TextView>(R.id.EmailLog)

        db=FirebaseFirestore.getInstance()
        if(email!=null) {
            db.collection("USERS").document(email).get()
                .addOnSuccessListener { task ->
                    name.text = task.get("Name").toString()
                    phone.text = task.get("Phone").toString()
                    emailLog.text = task.get("email").toString()

                }
        }

    }
}