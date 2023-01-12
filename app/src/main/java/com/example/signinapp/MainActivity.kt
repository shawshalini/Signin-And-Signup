package com.example.signinapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
//    private var emailId=findViewById<EditText>(R.id.emailId)
//    var passid=findViewById<EditText>(R.id.passid)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth= FirebaseAuth.getInstance()
        var emailId=findViewById<EditText>(R.id.emailId)
        var passid=findViewById<EditText>(R.id.passid)
//        var email=emailId.text.toString()
//        var password=emailId.text.toString()
        var loginbtn=findViewById<Button>(R.id.Loginbutton)
        var registerbtn=findViewById<Button>(R.id.Signupbutton)
    registerbtn.setOnClickListener {
        var intent= Intent(this,SignUp::class.java)
        startActivity(intent)
        finish()

   }
        loginbtn.setOnClickListener{
            if(checking()){
                var email=emailId.text.toString()
              var password=passid.text.toString()
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            //Toast.makeText(this,"login Successful",Toast.LENGTH_LONG).show()
//                            Log.d(TAG, "signInWithEmail:success")
//                            val user = auth.currentUser
//                            updateUI(user)
                            var intent= Intent(this,SignedIn::class.java)
                            intent.putExtra("email",email)
                            startActivity(intent)
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this,"Wrong details",Toast.LENGTH_LONG).show()

//                            Log.w(TAG, "signInWithEmail:failure", task.exception)
//                            Toast.makeText(baseContext, "Authentication failed.",
//                                Toast.LENGTH_SHORT).show()
//                            updateUI(null)
                        }
                    }

                    }


            else{
                Toast.makeText(this,"Enter the Details",Toast.LENGTH_LONG).show()
            }
        }

    }
    private fun checking():Boolean{
        var emailId=findViewById<EditText>(R.id.emailId)
        var passid=findViewById<EditText>(R.id.passid)
        if(emailId.text.toString().trim{it<=' '}.isNotEmpty() &&
            passid.text.toString().trim{it<=' '}.isNotEmpty())
        {
            return true
        }
        return false

    }
}