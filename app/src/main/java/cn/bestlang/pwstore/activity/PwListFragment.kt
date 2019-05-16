package cn.bestlang.pwstore.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bestlang.pwstore.R
import cn.bestlang.pwstore.activity.dummy.DummyContent

import cn.bestlang.pwstore.core.Pw
import cn.bestlang.pwstore.core.PwStore
import androidx.recyclerview.widget.DividerItemDecoration


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

        if (savedInstanceState == null) {
            setLayoutAnimation(view, R.anim.slide_and_fade_in_layout_animation)
        }

        view.addItemDecoration(
            DividerItemDecoration(
                activity!!, DividerItemDecoration.HORIZONTAL
            )
        )

        // Set the adapter
        with(view) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }

            val recyclerViewAdapter = MyPwListRecyclerViewAdapter(DummyContent.ITEMS, listener)
            adapter = recyclerViewAdapter
            recyclerViewAdapter.tracker = SelectionTracker.Builder<Long>(
                "mySelection",
                view,
                StableIdKeyProvider(view),
                MyDetailsLookup(view),
                StorageStrategy.createLongStorage()
            ).withSelectionPredicate(
                SelectionPredicates.createSelectAnything()
            ).build()
        }

        return view
    }

    fun setLayoutAnimation(view: ViewGroup,animationId: Int) {
        view.layoutAnimationListener = object: AnimationListener {
            override fun onAnimationStart( animation: Animation) {
            }

            override fun onAnimationEnd( animation: Animation) {
                view.layoutAnimation = null
            }

            override fun onAnimationRepeat(animation: Animation) {
            }
        }
        view.layoutAnimation = AnimationUtils.loadLayoutAnimation(activity, animationId);
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
