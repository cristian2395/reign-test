package com.example.reigntest.Activities

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reigntest.Data.BD.Entity.Hit
import com.example.reigntest.R
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*


class HitsAdapter(private var localData: ArrayList<Hit>): RecyclerView.Adapter<HitsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textTitle: TextView = view.findViewById(R.id.textTitle)
        var textAuthor: TextView = view.findViewById(R.id.textAuthor)
        var card: LinearLayout = view.findViewById(R.id.carHist)
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.car_hits, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.textTitle.text = ""
        viewHolder.textTitle.text = ""
        viewHolder.textAuthor.text = ""

        viewHolder.textTitle.text = localData[position].title
        viewHolder.textTitle.text = localData[position].storyTitle
        "${localData[position].author} - ${difDate(position)}".also { viewHolder.textAuthor.text = it }

        viewHolder.card.setOnClickListener {
            localData[position].storyUrl?.let {
                val intent = Intent(viewHolder.itemView.context, WebViewActivity::class.java)
                intent.putExtra("storyUrl", it)
                viewHolder.itemView.context.startActivity(intent)
            } ?: run {
                Snackbar.make(viewHolder.itemView, "No existe el sitio", Snackbar.LENGTH_LONG).show();
            }
        }
        Log.e("iddddddd", localData[position].objectID.toString())
    }

    fun difDate(index: Int): String = localData[index].createdAt?.let {
        val date1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(it.replace("Z$".toRegex(), "+0000"))
        val date2 = Date()
        date1?.let { dateIt ->
            val diffMs: Long = date2.time - dateIt.time
            val difWeeks = diffMs / 604800000
            val diffDays = (diffMs % 604800000) / 86400000
            val diffHrs = ((diffMs % 604800000) % 86400000) / 3600000
            val diffMins = (((diffMs % 604800000) % 86400000) % 3600000) / 60000
            val diffSecs = ((((diffMs % 604800000) % 86400000) % 3600000) % 60000) / 1000

            return when {
                difWeeks.toInt() > 2 -> {
                    it
                }
                diffDays.toInt() == 1 -> {
                    "yesterday"
                }
                else -> {
                    var date = if (difWeeks.toInt() > 0) "$difWeeks week(s) " else ""
                    date += if (diffDays.toInt() > 0) " $diffDays day(s)" else ""
                    date += if (diffHrs.toInt() > 0) " ${diffHrs}h" else ""
                    date += if (diffMins.toInt() > 0) " ${diffMins}m" else ""
                    date += if (diffSecs.toInt() > 0) " ${diffSecs}s" else ""
                    date
                }
            }
        } ?:run {
            return ""
        }
    } ?:run {
        return ""
    }

    override fun getItemCount(): Int = localData.size

    fun getArrayList(): ArrayList<Hit> {
        return localData
    }

    fun addItem(item: Hit) {
        localData.add(item)
        notifyItemInserted(localData.size)
    }

    fun removeAt(position: Int) {
        localData.removeAt(position)
        notifyItemRemoved(position)
    }
}
