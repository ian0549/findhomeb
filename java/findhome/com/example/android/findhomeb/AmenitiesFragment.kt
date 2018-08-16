package findhome.com.example.android.findhomeb

import android.content.Context
import android.net.ConnectivityManager
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
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch


class AmenitiesFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    val myKitchen:MyKitchen=MyKitchen()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = activity?.actionBar
        actionBar?.setHomeButtonEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_amenities, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val progressBar: ProgressBar?=view.findViewById<ProgressBar>(R.id.progressBar)

        launch(UI) {
            myKitchen.SetProgress(progressBar!!,80)
        }


        val toolbar = view.findViewById<Toolbar>(R.id.my_toolbar) as Toolbar



        toolbar.setNavigationOnClickListener {

            Navigation.findNavController(it).navigate(R.id.addPlacePicturesFragment, null)
        }

      //  val connectivityManager: ConnectivityManager =context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager




        val buttonnext: MaterialButton?= view.findViewById<MaterialButton>(R.id.button_next)

        buttonnext?.setOnClickListener{

            Navigation.findNavController(it).navigate(R.id.addressFragment, null)
//            if (!connectivityManager.activeNetworkInfo.isConnected){
//                Navigation.findNavController(it).navigate(R.id.checkConnectionFragment, null)
//            }else{
//                Navigation.findNavController(it).navigate(R.id.addressFragment, null)
//            }




        }
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
                AmenitiesFragment()
    }
}
