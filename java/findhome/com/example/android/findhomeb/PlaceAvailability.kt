package findhome.com.example.android.findhomeb

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.button.MaterialButton
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.Navigation
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.cleverpumpkin.calendar.CalendarView


class PlaceAvailability : Fragment() {


    private var listener: OnFragmentInteractionListener? = null

    val myKitchen:MyKitchen=MyKitchen()
    val preference_file_key="MYDESTINATION"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = activity?.actionBar
        actionBar?.setHomeButtonEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_availability, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val progressBar: ProgressBar?=view.findViewById<ProgressBar>(R.id.progressBar)

        launch(UI) {
            myKitchen.SetProgress(progressBar!!,30)
        }


        val prefs= activity?.getPreferences(Context.MODE_PRIVATE) ?: return


        val destin = prefs.getString(preference_file_key,"none")

        val toolbar = view.findViewById<Toolbar>(R.id.my_toolbar) as Toolbar



        toolbar.setNavigationOnClickListener {

           if (destin.toString()=="hostel"){

               Navigation.findNavController(it).navigate(R.id.hostelRoomTypeFragment, null)
           }else{

               Navigation.findNavController(it).navigate(R.id.generalRoomTypeFragment, null)

           }


        }


        val buttonnext: MaterialButton?= view.findViewById<MaterialButton>(R.id.button_next_place_avail)
        val alwaysbtn: MaterialButton?= view.findViewById<MaterialButton>(R.id.always)
        val rangebtn: MaterialButton?= view.findViewById<MaterialButton>(R.id.range)
        val oncebtn: MaterialButton?= view.findViewById<MaterialButton>(R.id.one_time)




        buttonnext?.setOnClickListener{
            when(destin!!.toString()) {
                "hostel" -> Navigation.findNavController(it).navigate(R.id.priceHostelFragment, null)
                "hotel" -> Navigation.findNavController(it).navigate(R.id.hotelGeneralPriceFragment, null)
                "house" -> Navigation.findNavController(it).navigate(R.id.generalPriceFragment, null)
                "apartment" -> Navigation.findNavController(it).navigate(R.id.generalPriceFragment, null)
            }



        }





        alwaysbtn?.setOnClickListener {

            val fm = fragmentManager
            val mDialogFragment = CalenderDialogFragment().newInstance("Heello", CalenderDialogFragment.DemoMode.MULTIPLE_SELECTION)

            mDialogFragment.show(fm, "fragment_edit_name")
        }

        rangebtn?.setOnClickListener {
            val fm = fragmentManager
            val mDialogFragment = CalenderDialogFragment().newInstance("Heello", CalenderDialogFragment.DemoMode.RANGE_SELECTION)

            mDialogFragment.show(fm, "fragment_edit_name")

        }
        oncebtn?.setOnClickListener {

            val fm = fragmentManager
            val mDialogFragment = CalenderDialogFragment().newInstance("Heello", CalenderDialogFragment.DemoMode.SINGLE_SELECTION)

            mDialogFragment.show(fm, "fragment_edit_name")
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
                PlaceAvailability()
    }
}
