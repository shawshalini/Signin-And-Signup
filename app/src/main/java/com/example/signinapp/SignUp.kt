package com.example.signinapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        db=FirebaseFirestore.getInstance()
        var Name=findViewById<EditText>(R.id.NameId)
        var phone=findViewById<EditText>(R.id.phonesignupId)
        var Email=findViewById<EditText>(R.id.email1Id)
        var Password=findViewById<EditText>(R.id.passwordId)
        var signupbtn=findViewById<Button>(R.id.signup_button)
        signupbtn.setOnClickListener{
            if(checking()){
                val email=Email.text.toString()
                val password=Password.text.toString()
                val name=Name.text.toString()
                val phone=phone.text.toString()
                val user= hashMapOf(
                    "Name" to name,
                    "Phone" to phone,
                    "email" to email
                )
                val Users=db.collection("USERS")
                val query=Users.whereEqualTo("email",email).get()
                    .addOnSuccessListener { it->
                        if(it.isEmpty()){
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this){
                                    task->
                                    if(task.isSuccessful){
                                        Users.document(email).set(user)
                                        val intent=Intent(this,SignedIn::class.java)
                                        intent.putExtra("email",email)
                                        startActivity(intent)
                                        finish()


                                    }
                                    else{
                                        Toast.makeText(this,"Authentication Failed", Toast.LENGTH_LONG).show()
                                    }
                                }
                        }
                        else{
                            Toast.makeText(this,"Users already registered", Toast.LENGTH_LONG).show()
                            val intent= Intent(this,MainActivity::class.java)
                            startActivity(intent)

                        }
                    }


            }
            else{
                Toast.makeText(this,"Enter the Details", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun checking():Boolean{
        var Name=findViewById<EditText>(R.id.NameId)
        var phone=findViewById<EditText>(R.id.phonesignupId)
        var Email=findViewById<EditText>(R.id.email1Id)
        var Password=findViewById<EditText>(R.id.passwordId)


        if(Name.text.toString().trim{it<=' '}.isNotEmpty()
            && phone.text.toString().trim{it<=' '}.isNotEmpty()
            && Email.text.toString().trim{it<=' '}.isNotEmpty()
            && Password.text.toString().trim{it<=' '}.isNotEmpty() )
        {
            return true
        }
        return false


    }
}