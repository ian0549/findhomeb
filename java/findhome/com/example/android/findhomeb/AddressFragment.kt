package findhome.com.example.android.findhomeb

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.support.design.button.MaterialButton
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.Navigation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.*
import com.google.android.gms.location.places.PlaceDetectionClient
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.model.Marker
import findhome.com.example.android.findhomeb.MyKitchen.Companion.TAG
import findhome.com.example.android.findhomeb.MyKitchen.Companion.locationListeners
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.withContext
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class AddressFragment : Fragment(), OnMapReadyCallback,GoogleMap.OnMyLocationButtonClickListener,GoogleMap.OnMyLocationClickListener {

    private var listener: OnFragmentInteractionListener? = null
    val myKitchen:MyKitchen= MyKitchen()
    private lateinit var mMap: GoogleMap
    private lateinit var mMapView: MapView
    private val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey2"
    private  var mLocationManager:LocationManager?=null
    private var mAdress: ArrayList<String>?=null



    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {

        if (ActivityCompat.checkSelfPermission(this.context!!,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.context!!,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),myKitchen.REQUEST_LOCATION_PERMISSION)
            ActivityCompat.requestPermissions(this.activity!!, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),myKitchen.REQUEST_LOCATION_PERMISSION)
        }else{

            mMap = googleMap as GoogleMap

            val location = mLocationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            val gps  =LatLng(location.latitude, location.longitude)


            val mymarker: Marker = mMap.addMarker(MarkerOptions()
                    .position(gps).title("Current Location"))
            mymarker.showInfoWindow()
            mMap.moveCamera(CameraUpdateFactory.newLatLng(gps))


            mMap.isMyLocationEnabled = true
            mMap.setOnMyLocationButtonClickListener(this)
            mMap.setOnMyLocationClickListener(this)
            mMap.uiSettings.isScrollGesturesEnabled = true

        }




    }






    @SuppressLint("MissingPermission")
    override fun onMyLocationButtonClick(): Boolean {
        return false
    }


    override fun onMyLocationClick(location: Location) {


        Toast.makeText(this.context, "MyLocation is"+location.latitude.toString()+" "+location.longitude.toString(), Toast.LENGTH_SHORT).show()


        drawMarker(location)

    }


private fun drawMarker(location:Location ) {


			val gps  =LatLng(location.latitude, location.longitude)
			mMap.addMarker(MarkerOptions()
					.position(gps)
					.title("Current Position"))
			//mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 12f))


	}



    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launch(UI) {

            mLocationManager = this@AddressFragment.context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager



            myKitchen.CheckPermission(this@AddressFragment.context!!, this@AddressFragment.activity!!)
            myKitchen.initLocationManager(mLocationManager!!, this@AddressFragment.context!!)


            val location = mLocationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if(location!=null){
                mAdress=myKitchen.GeocodeData(this@AddressFragment.context!!,location)

                Log.v("MY ADDRESS",mAdress.toString())
            }



        }


    }










    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view:View=inflater.inflate(R.layout.fragment_address, container, false)
        mMapView=view.findViewById<MapView>(R.id.mapviewaddress)

        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }

        mMapView.onCreate(mapViewBundle)
        mMapView.getMapAsync(this)

        return view
    }







    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val progressBar: ProgressBar?=view.findViewById<ProgressBar>(R.id.progressBar)

        launch(UI) {
            myKitchen.SetProgress(progressBar!!,95)
        }



        val toolbar = view.findViewById<Toolbar>(R.id.my_toolbar) as Toolbar



        toolbar.setNavigationOnClickListener {

            Navigation.findNavController(it).navigate(R.id.amenitiesFragment, null)
        }

        val buttonnext: MaterialButton?= view.findViewById<MaterialButton>(R.id.button_finish)

        buttonnext?.setOnClickListener{

            Navigation.findNavController(it).navigate(R.id.profileFragment, null)
        }

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)


        var mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
        }

        mMapView.onSaveInstanceState(mapViewBundle)

    }


    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mMapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMapView.onStop()
    }

    override fun onPause() {
        mMapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (mLocationManager != null)
            for (locationListener in locationListeners) { // <- fix
                try {
                    mLocationManager?.removeUpdates(locationListener)
                } catch (e: Exception) {
                    Log.w(TAG, "Failed to remove location listeners")
                }
            }

        mMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }





    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                AddressFragment()

    }






}
