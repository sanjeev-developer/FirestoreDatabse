package com.firestoredatabse

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        but_save.setOnClickListener(this)
        but_view.setOnClickListener(this)
    }

    override fun onClick(v: View) {

        when(v.id)
        {
            R.id.but_save ->
            {
                if (edt_name.text.toString() == "") {
                    Toast.makeText(this@MainActivity, "name is empty", Toast.LENGTH_SHORT).show()
                }
                else if(edt_phone.text.toString() == "")
                {
                    Toast.makeText(this@MainActivity, "phone is empty", Toast.LENGTH_SHORT).show()
                }
                else if(edt_email.text.toString() == "")
                {
                    Toast.makeText(this@MainActivity, "email is empty", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Adddata()
                }
            }

            R.id.but_view ->
            {
               // readdata()

                intent = Intent(this@MainActivity, UserList::class.java)
                startActivity(intent)
            }
        }
    }

    fun Adddata()
    {
        // Create a new user with a first and last name
        val user = hashMapOf(
                "name" to edt_name.text.toString(),
                "phone" to edt_phone.text.toString(),
                "email" to edt_email.text.toString()
        )

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    println( "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(this@MainActivity, "DocumentSnapshot added with ID: ${documentReference.id}", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    println( "Error adding document"+e)
                    Toast.makeText(this@MainActivity,  "Error adding document"+e, Toast.LENGTH_SHORT).show()
                }
    }

    fun Deletedata()
    {

    }

    fun updatedelete()
    {

    }
}
