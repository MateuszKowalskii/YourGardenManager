package com.example.yourgardenmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlansAdapter(
    private val plans: MutableList<PlannedAction>
) : RecyclerView.Adapter<PlansAdapter.PlanViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(index: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    class PlanViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        return PlanViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_plan,
                parent,
                false
            ), mListener
        )
    }

    fun addPlan(plan: PlannedAction){
        plans.add(plan)
        notifyItemInserted(plans.size - 1)
    }

    fun deletePlan(plan: PlannedAction){
        plans.remove(plan)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val curPlan = plans[position]

        holder.itemView.findViewById<TextView>(R.id.plotNameTextView).text = curPlan.plotId.toString()
        holder.itemView.findViewById<TextView>(R.id.planRealisationTextView).text = curPlan.date
        holder.itemView.findViewById<TextView>(R.id.planDescriptionTextView).text = curPlan.description
    }

    override fun getItemCount(): Int {
        return plans.size
    }

    fun getItem(index: Int): PlannedAction{
        val curPlan = plans[index]
        return curPlan
    }

}