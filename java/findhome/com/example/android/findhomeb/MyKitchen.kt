package findhome.com.example.android.findhomeb

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.ProgressBar
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.PlaceDetectionClient
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MyKitchen {

    data class myPlace(val location:LatLng,
                       val id:String,
                       val name:String,
                       val placeId:String,
                       val vicinity:String,
                       val type: MutableList<Int>)


     val REQUEST_LOCATION_PERMISSION = 100

    suspend fun SetProgress(progressBar: ProgressBar, progInt: Int) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(progInt, true)
        } else {
            progressBar.progress = progInt
        }

    }


    suspend fun initLocationManager(locationManager: LocationManager, context: Context) {

        try {

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, INTERVAL, DISTANCE, locationListeners[1])
        } catch (e: SecurityException) {
            Log.e(TAG, "Fail to request location update", e)
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "Network provider does not exist", e)
        }

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, INTERVAL, DISTANCE, locationListeners[0])
        } catch (e: SecurityException) {
            Log.e(TAG, "Fail to request location update", e)
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "GPS provider does not exist", e)
        }

    }





    suspend fun GeocodeData(context: Context,location: Location):ArrayList<String>{

       val geocoder= Geocoder(context, Locale.getDefault())

        var address:List<Address>?=null
        val addressParts: ArrayList<String> = ArrayList()

        try {

            address = geocoder.getFromLocation(location.latitude, location.longitude, 1)


            if (address==null){

                Log.e(TAG, "Response is not Good")

            } else {
                // If an address is found, read it into resultMessage
                val myaddress: Address = address[0]


                for (i:Int in 0 until address.size){
                    addressParts.add(myaddress.getAddressLine(i))
                }

            }



        }catch (ioException: IOException){
            Log.e(TAG, "Response Message", ioException)
        }catch (illegalArgumentException:IllegalArgumentException ) {
            // Catch invalid latitude or longitude values

            Log.e(TAG,"Couldnt convert", illegalArgumentException)
        }


        return  addressParts
    }


    @SuppressLint("MissingPermission")
    suspend fun PlacesTask(placesDetectionClient: PlaceDetectionClient): myPlace {

        val placesResult: Task<PlaceLikelihoodBufferResponse>? =placesDetectionClient.getCurrentPlace(null)

        var currentPlace: Place?=null
        placesResult?.addOnCompleteListener { task ->

            task.addOnCompleteListener { task1 ->

                if (task1.isSuccessful){
                    val likelyPlaces:PlaceLikelihoodBufferResponse=task1.result


                   var maxLikelihood:Float=0.0f


                    likelyPlaces.forEach {
                        if (maxLikelihood<it.likelihood){
                            maxLikelihood=it.likelihood
                            currentPlace=it.place


                        }

                    }


                    if (currentPlace!=null){

                    }


                   likelyPlaces.release()
                }

            }



        }


        return  myPlace(currentPlace!!.latLng,currentPlace!!.id, currentPlace!!.name as String,currentPlace!!.id, currentPlace!!.address as String,currentPlace!!.placeTypes)
    }

    fun CheckPermission(context: Context, activity: Activity) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED   ) {

            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_LOCATION_PERMISSION)
        } else {
            Log.e("DB", "PERMISSION NOT GRANTED")
        }


    }


    companion object {


        val TAG = "LocationTrackingService"

        const val INTERVAL = 1000.toLong() // In milliseconds
        const val DISTANCE = 10.toFloat() // In meters

        val locationListeners = arrayOf(
                LTRLocationListener(LocationManager.GPS_PROVIDER),
                LTRLocationListener(LocationManager.NETWORK_PROVIDER)
        )

        class LTRLocationListener(provider: String) : android.location.LocationListener {

             val lastLocation = Location(provider)

            override fun onLocationChanged(location: Location?) {
                lastLocation.set(location)


            }

            override fun onProviderDisabled(provider: String?) {
            }

            override fun onProviderEnabled(provider: String?) {
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }


        }

    }



    enum class Amenities


}


