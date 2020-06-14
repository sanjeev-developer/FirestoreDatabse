package com.firestoredatabse

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.firestoredatabse.adapter.UserAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_user_list.*
import kotlinx.android.synthetic.main.option_dialog.*

class UserList : AppCompatActivity()
{
    val db = Firebase.firestore
    var layoutManager: LinearLayoutManager? = null
    var userAdapter: UserAdapter ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        readdata()
    }

    fun readdata()
    {
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (i in result) {
                    println( "${i.id} => ${i.data}")
                    Toast.makeText(this@UserList, "${i.id} => ${i.data}", Toast.LENGTH_SHORT).show()
                }

                userAdapter = UserAdapter(this@UserList, result)
                layoutManager = LinearLayoutManager(this@UserList)
                rec_user!!.layoutManager = layoutManager
                rec_user!!.setHasFixedSize(true)
                rec_user!!.itemAnimator = DefaultItemAnimator()
                rec_user!!.adapter = userAdapter
            }
            .addOnFailureListener { exception ->
                println( "Error getting documents."+ exception)
                Toast.makeText(this@UserList,  "Error getting documents."+ exception, Toast.LENGTH_SHORT).show()
            }
    }

    fun showoption(userid: String) {
        val dialog = Dialog(this@UserList)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.option_dialog)
        dialog.setCancelable(true)

        var edt_update: EditText = dialog.findViewById(R.id.edt_update)

        var but_yes: Button = dialog.findViewById(R.id.but_yes)
        but_yes.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                db.collection("users").document(userid)
                    .delete().addOnSuccessListener {
                        Toast.makeText(this@UserList, "Data deleted !", Toast.LENGTH_SHORT).show()
                    }
            }
        })

        var but_update: Button = dialog.findViewById(R.id.but_update)
        but_update.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val updateRef = db.collection("users").document(userid)

                     updateRef
                    .update("name", edt_update.text.toString())
                    .addOnSuccessListener {  Toast.makeText( this@UserList,"DocumentSnapshot successfully updated!", Toast.LENGTH_SHORT).show() }
                    .addOnFailureListener {  Toast.makeText( this@UserList,"Error updating document", Toast.LENGTH_SHORT).show() }
            }
        })
        dialog.show()
    }
}
