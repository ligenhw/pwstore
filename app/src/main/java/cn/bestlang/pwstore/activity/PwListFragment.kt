package cn.bestlang.pwstore.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bestlang.pwstore.R
import cn.bestlang.pwstore.activity.dummy.DummyContent

import cn.bestlang.pwstore.core.Pw
import cn.bestlang.pwstore.core.PwStore

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [PwListFragment.OnListFragmentInteractionListener] interface.
 */
class PwListFragment : Fragment() {

    private lateinit var store: PwStore
    private lateinit var view: RecyclerView

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
        store = PwStore.getInstance(context!!.applicationContext)
    }

    override fun onStart() {
        super.onStart()
        val liveData = store.repo.all()

        liveData.observe(this, Observer<List<Pw>> { pws ->
            if (pws != null) {
                Log.d(TAG, pws.toString())
                val adapter = view.adapter as MyPwListRecyclerViewAdapter
                adapter.mValues = pws
                adapter.notifyDataSetChanged()
            } else {

            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_pwlist_list, container, false) as RecyclerView

        // Set the adapter
        with(view) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = MyPwListRecyclerViewAdapter(DummyContent.ITEMS, listener)
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        if (context is OnListFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
//        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: Pw?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        val TAG = "PwStoreUI"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            PwListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
