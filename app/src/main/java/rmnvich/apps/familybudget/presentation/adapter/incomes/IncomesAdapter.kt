package rmnvich.apps.familybudget.presentation.adapter.incomes

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.entity.Income
import rmnvich.apps.familybudget.databinding.ItemIncomeBinding
import java.util.*

class IncomesAdapter : RecyclerView.Adapter<IncomesAdapter.ViewHolder>() {

    private lateinit var mListener: OnClickIncomeListener
    private var mIncomesList: List<Income> = LinkedList()

    fun setData(data: List<Income>) {
        val diffUtilCallback = IncomesDiffUtilCallback(mIncomesList, data)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback, true)

        mIncomesList = data
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemIncomeBinding = DataBindingUtil.inflate(inflater,
                R.layout.item_income, parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mIncomesList[position])
        setFadeAnimation(holder.itemView)
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 450
        view.startAnimation(anim)
    }

    override fun getItemCount(): Int {
        return mIncomesList.size
    }

    interface OnClickIncomeListener {
        fun onClick(incomeId: Int)
    }

    fun setListener(listener: OnClickIncomeListener) {
        mListener = listener
    }

    inline fun setOnClickListener(
            crossinline onClickIncome: (Int) -> Unit) {

        setListener(object : OnClickIncomeListener {
            override fun onClick(incomeId: Int) {
                onClickIncome(incomeId)
            }
        })
    }

    inner class ViewHolder(val binding: ItemIncomeBinding) :
            RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(income: Income) {
            binding.income = income
            binding.executePendingBindings()
        }

        override fun onClick(view: View?) {
            mListener.onClick(mIncomesList[adapterPosition].incomeId)
        }
    }
}