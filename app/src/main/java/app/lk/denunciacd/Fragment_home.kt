package app.lk.denunciacd

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Fragment_home.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class Fragment_home : Fragment() {
lateinit var mPager:ViewPager
    private var mListener: OnFragmentInteractionListener? = null
    val path2: ArrayList<String?> = ArrayList()
    var timer: Timer? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_fragment_home, container, false)
        var database = FirebaseDatabase.getInstance()
        var myRef = database.getReference("img_princi")

        mPager=view.findViewById(R.id.pager)

        var adapter:PagerAdapter = PageView(context!!,path2)
        mPager.adapter=adapter

        val timerTask = object : TimerTask() {
           override fun run() {
                mPager.post(Runnable { mPager.setCurrentItem((mPager.getCurrentItem() + 1) % path2.size) })
            }
        }
        timer = Timer()
        timer!!.schedule(timerTask, 10000, 6000)
        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
            Toast.makeText(context,path2[position],Toast.LENGTH_LONG)

            }

        })
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
               path2.clear()
                for (snapshot in dataSnapshot.children) {
                    val img = snapshot.getValue(Img_princi::class.java)

                    path2.add(img!!.url)
                    adapter.notifyDataSetChanged()


                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                // ...
            }
        }
        myRef.child("img_princi").addValueEventListener(postListener)



        return view
}


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onDestroy() {
        super.onDestroy()
        timer!!.cancel();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }



}// Required empty public constructor
