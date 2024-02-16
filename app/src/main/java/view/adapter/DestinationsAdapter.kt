package view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vistawise.R
import model.Destination

class DestinationsAdapter(private val destinations: List<Destination>) :
    RecyclerView.Adapter<DestinationsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_destination, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val destination = destinations[position]
        holder.destinationName.text = destination.name
        // Set image using Glide, Picasso, or setImageResource
        holder.destinationImage.setImageResource(destination.imageResId)
    }

    override fun getItemCount(): Int {
        return destinations.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val destinationImage: ImageView = itemView.findViewById(R.id.destination_image)
        val destinationName: TextView = itemView.findViewById(R.id.destination_name)
    }
}