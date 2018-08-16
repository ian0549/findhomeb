package findhome.com.example.android.findhomeb

import android.content.Context
import android.content.Intent
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
import kotlinx.android.synthetic.main.fragment_add_place_pictures.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import android.widget.ImageView


class AddPlacePicturesFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private val SELECTPICTURES=2
    val myKitchen:MyKitchen=MyKitchen()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = activity?.actionBar
        actionBar?.setHomeButtonEnabled(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_place_pictures, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar: ProgressBar?=view.findViewById<ProgressBar>(R.id.progressBar)

        launch(UI) {
            myKitchen.SetProgress(progressBar!!,70)
        }

        val toolbar = view.findViewById<Toolbar>(R.id.my_toolbar) as Toolbar

        toolbar.setNavigationOnClickListener {

            Navigation.findNavController(it).navigate(R.id.profilePictureFragment, null)
        }
        val buttonnext: MaterialButton?= view.findViewById<MaterialButton>(R.id.button_next)

        buttonnext?.setOnClickListener{

            Navigation.findNavController(it).navigate(R.id.amenitiesFragment, null)
        }

        picbutton.setOnClickListener {

            val intent= Intent()
            intent.type="image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action= Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Images"),SELECTPICTURES)

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)



        if (requestCode==SELECTPICTURES && data!=null){



            launch(UI){

                val imgList:ArrayList<Uri>?= ArrayList()


                val clipData = data.clipData
                if (clipData != null)
                { // handle multiple photo

                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        imgList?.add(uri!!)
                    }
                } else { // handle single photo
                    val uri = data.data
                    imgList?.add(uri!!)
                }

                Log.v("ARRAYS PICTURES",imgList.toString())
                val imagesAdapter = GridImageAdaptor(this@AddPlacePicturesFragment.context, imgList!!)
                grid_view.adapter=imagesAdapter



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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                AddPlacePicturesFragment()
    }
}
