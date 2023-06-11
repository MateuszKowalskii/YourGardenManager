package com.example.yourgardenmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlotsAdapter(
    private val plots: MutableList<Plot>
) : RecyclerView.Adapter<PlotsAdapter.PlotViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(index: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class PlotViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        //changed ta klasa nie miała ciała - bez{}
        init{
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlotViewHolder {
        return PlotViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_plot,
                parent,
                false
            ), mListener
        )
    }

    fun addPlot(plot: Plot){
        plots.add(plot)
        notifyItemInserted(plots.size - 1)
    }

    fun deletePlot(plot: Plot){
        plots.remove(plot)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PlotViewHolder, position: Int) {
        val curPlot = plots[position]

        holder.itemView.findViewById<TextView>(R.id.plotNameTextView).text = curPlot.name
        holder.itemView.findViewById<TextView>(R.id.plotPlantsTextView).text = curPlot.plantType
        holder.itemView.findViewById<TextView>(R.id.plotAreaTextView).text = curPlot.area.toString()
        holder.itemView.findViewById<TextView>(R.id.planIdTextView).text = curPlot.id.toString()
    }

    override fun getItemCount(): Int {
        return plots.size
    }

    fun getItem(index: Int): Plot{
        val curPlot = plots[index]
        return curPlot
    }
}