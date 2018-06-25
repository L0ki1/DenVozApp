package app.lk.denunciacd

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Fragment_reporte.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class Fragment_reporte : Fragment(),OnMapReadyCallback {

    private var mListener: OnFragmentInteractionListener? = null
    private var mapView: MapView? = null
    private val gmap: GoogleMap? = null
    private var btn_repo:Button? = null

    private val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_fragment_reporte, container, false)
btn_repo=view.findViewById(R.id.repo_ubi) as Button
        btn_repo!!.setOnClickListener{
            val intent = Intent(context, Tipos_reportes::class.java)
            startActivity(intent)

        }
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {


            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }
        mapView = view.findViewById(R.id.map_view)
        mapView?.onCreate(mapViewBundle)
        mapView?.getMapAsync(this)

return view
    }



    override fun onMapReady(map: GoogleMap) {
        // Posicionar el mapa en una localización y con un nivel de zoom
        val latLng = LatLng(20.061560, -97.054559)
        // Un zoom mayor que 13 hace que el emulador falle, pero un valor deseado para
        // callejero es 17 aprox.
        val zoom = 13f
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))

        // Colocar un marcador en la misma posición
        map.addMarker(MarkerOptions().position(latLng))
        // Más opciones para el marcador en:
        // https://developers.google.com/maps/documentation/android/marker

        // Otras configuraciones pueden realizarse a través de UiSettings
        // UiSettings settings = getMap().getUiSettings();
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle = outState?.getBundle(MAP_VIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState?.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
        }

        mapView?.onSaveInstanceState(mapViewBundle)
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


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

     override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

     override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

     override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

     override fun onPause() {
        mapView?.onPause()
        super.onPause()
    }

     override fun onDestroy() {
        mapView?.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
}// Required empty public constructor
