package findhome.com.example.android.findhomeb

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.design.button.MaterialButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.navigation.Navigation


class EntryFormoneFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    val preference_file_key="MYDESTINATION"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entry_formone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val toolbar = view.findViewById<Toolbar>(R.id.my_toolbar) as Toolbar
        


        toolbar.setNavigationOnClickListener {

            Navigation.findNavController(it).navigate(R.id.profileFragment, null)
        }

        val buttonnext:MaterialButton?= view.findViewById<MaterialButton>(R.id.button_next)


        val radioGroup:RadioGroup?=view.findViewById<RadioGroup>(R.id.facility_group)



        buttonnext?.setOnClickListener {


            if(radioGroup?.checkedRadioButtonId == -1) {

                Snackbar.make(
                        view, // Parent view
                        "Please Choose one of the Faclities to Continue", // Message to show
                        Snackbar.LENGTH_LONG // How long to display the message.
                ).show()

            }else{



                when (radioGroup?.checkedRadioButtonId) {

                    R.id.facility_hostel -> {


                        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                                ?: return@setOnClickListener
                        with(sharedPref.edit()) {
                            putString(preference_file_key, "hostel")
                            apply()
                        }


                        Navigation.findNavController(it).navigate(R.id.hostelRoomTypeFragment, null)
                    }

                    R.id.facility_house -> {


                        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                                ?: return@setOnClickListener
                        with(sharedPref.edit()) {
                            putString(preference_file_key, "house")
                            apply()
                        }


                        Navigation.findNavController(it).navigate(R.id.generalRoomTypeFragment, null)
                    }

                    R.id.facility_apartment -> {

                        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                                ?: return@setOnClickListener
                        with(sharedPref.edit()) {
                            putString(preference_file_key, "apartment")
                            apply()
                        }



                        Navigation.findNavController(it).navigate(R.id.generalRoomTypeFragment, null)
                    }

                    R.id.facility_hotel -> {

                        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                                ?: return@setOnClickListener
                        with(sharedPref.edit()) {
                            putString(preference_file_key, "hotel")
                            apply()
                        }


                        Navigation.findNavController(it).navigate(R.id.generalRoomTypeFragment, null)
                    }


                }


            }

        }


    }


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
        fun newInstance() =EntryFormoneFragment()
    }
}
