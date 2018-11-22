package rmnvich.apps.familybudget.presentation.adapter.incometypes

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import rmnvich.apps.familybudget.R

class IncomeTypesAdapter(private val names: Array<String>) : RecyclerView.Adapter<IncomeTypesAdapter.ViewHolder>() {

    private lateinit var mClickListener: () -> Unit
    var checkedPosition: MutableList<Int> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_income_type, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvIncomeType.text = names[position]

        holder.switchEnabled.isChecked =
                checkedPosition.contains(position)
    }

    override fun getItemCount(): Int {
        return names.size
    }

    fun setOnClickListener(onClickListener: () -> Unit) {
        mClickListener = onClickListener
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

        @BindView(R.id.tv_income_type)
        lateinit var tvIncomeType: TextView

        @BindView(R.id.switch_enabled)
        lateinit var switchEnabled: Switch

        init {
            itemView?.let {
                ButterKnife.bind(this, it)

                switchEnabled.isClickable = false
                it.setOnClickListener(this)
            }
        }

        override fun onClick(view: View) {
            val isChecked: Boolean
            if (switchEnabled.isChecked) {
                isChecked = false
                switchEnabled.isChecked = isChecked
                checkedPosition.remove(adapterPosition)
            } else {
                isChecked = true
                switchEnabled.isChecked = isChecked
                checkedPosition.add(adapterPosition)
            }
            mClickListener()
        }
    }
}