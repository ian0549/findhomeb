package findhome.com.example.android.findhomeb

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.button.MaterialButton
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation


class OverviewFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null



    val preference_file_key="MYDESTINATION"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = activity?.actionBar
        actionBar?.setHomeButtonEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        val prefs= activity?.getPreferences(Context.MODE_PRIVATE) ?: return


        val destin = prefs.getString(preference_file_key,"none")

        val toolbar = view.findViewById<Toolbar>(R.id.my_toolbar) as Toolbar



        toolbar.setNavigationOnClickListener {

            when(destin.toString()) {
                "hostel" -> Navigation.findNavController(it).navigate(R.id.priceHostelFragment, null)
                "hotel" -> Navigation.findNavController(it).navigate(R.id.hotelGeneralPriceFragment, null)
                "house" -> Navigation.findNavController(it).navigate(R.id.generalPriceFragment, null)
                "apartment" -> Navigation.findNavController(it).navigate(R.id.generalPriceFragment, null)
            }


        }







        val buttonnext: MaterialButton?= view.findViewById<MaterialButton>(R.id.button_next)

        buttonnext?.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.profilePictureFragment, null)
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
                OverviewFragment()
    }
}
