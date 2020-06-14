package com.firestoredatabse.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firestoredatabse.R
import com.firestoredatabse.UserList
import com.google.firebase.firestore.QuerySnapshot

class UserAdapter(
    public val context: Context,
    public val result: QuerySnapshot) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        holder.txt_name.text = result.documents[position].data?.get("name").toString()
        holder.txt_phone.text = result.documents[position].data?.get("phone").toString()
        holder.txt_email.text = result.documents[position].data?.get("email").toString()

        holder.ll_option.setOnLongClickListener(object: View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                (context as UserList).showoption(result.documents[position].id.toString())
                return true  }
        })
    }

    override fun getItemCount(): Int {
        return result.size()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txt_name: TextView
        var txt_phone: TextView
        var txt_email: TextView
        var ll_option: LinearLayout

        init {
            ll_option = view.findViewById(R.id.ll_option)
            txt_name = view.findViewById(R.id.txt_name)
            txt_phone = view.findViewById(R.id.txt_phone)
            txt_email = view.findViewById(R.id.txt_email)
        }
    }
}