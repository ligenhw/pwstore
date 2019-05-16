package cn.bestlang.pwstore.activity

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import cn.bestlang.pwstore.R


import cn.bestlang.pwstore.activity.PwListFragment.OnListFragmentInteractionListener
import cn.bestlang.pwstore.core.Pw

import kotlinx.android.synthetic.main.pwlist_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyPwListRecyclerViewAdapter(
    var mValues: List<Pw>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyPwListRecyclerViewAdapter.ViewHolder>() {

    var tracker: SelectionTracker<Long>? = null
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Pw
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pwlist_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mNameView.text = item.name
        holder.mAccountView.text = item.account

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
            tracker?.let {
                holder.mView.isActivated = it.isSelected(position.toLong())
            }
        }
    }

    override fun getItemCount(): Int = mValues.size

    override fun getItemId(position: Int) = position.toLong()

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mNameView: TextView = mView.nameTextView
        val mAccountView: TextView = mView.accountTextView

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }

        override fun toString(): String {
            return super.toString() + " '" + mAccountView.text + "'"
        }
    }
}
