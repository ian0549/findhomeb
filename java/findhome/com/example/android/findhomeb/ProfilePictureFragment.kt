package findhome.com.example.android.findhomeb

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.button.MaterialButton
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_profile_picture.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.io.IOError
import java.io.IOException


class ProfilePictureFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    val SELECT_PICTURE=1

    val uri:Uri?=null

    val myKitchen:MyKitchen=MyKitchen()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = activity?.actionBar
        actionBar?.setHomeButtonEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_picture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val toolbar = view.findViewById<Toolbar>(R.id.my_toolbar) as Toolbar


        val progressBar: ProgressBar?=view.findViewById<ProgressBar>(R.id.progressBar)

        val uploadPhoto:MaterialButton=view.findViewById<MaterialButton>(R.id.addphoto)



        uploadPhoto.setOnClickListener {


            launch {

                async {

                    val intent= Intent()
                    intent.type="image/*"
                    intent.action= Intent.ACTION_GET_CONTENT
                    startActivityForResult(Intent.createChooser(intent, "Select Image"),SELECT_PICTURE)

                }
            }




        }



        launch(UI) {
            myKitchen.SetProgress(progressBar!!,60)
        }


        toolbar.setNavigationOnClickListener {

            Navigation.findNavController(it).navigate(R.id.overviewFragment, null)
        }


        val buttonnext: MaterialButton?= view.findViewById<MaterialButton>(R.id.button_next)

        buttonnext?.setOnClickListener{

            Navigation.findNavController(it).navigate(R.id.addPlacePicturesFragment, null)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode==SELECT_PICTURE ){
            Log.e("ACTIVITYRESULT", "onActivityResult on Activity A: CODE")
            try {
                val uri=data?.data

                profile_pic.setImageURI(uri)


            }catch (e:IOException){
                e.printStackTrace()
            }

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
                ProfilePictureFragment()
    }
}
