package findhome.com.example.android.findhomeb

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.gridadaptorview.view.*


class GridImageAdaptor(context: Context?, uri: ArrayList<Uri>) : BaseAdapter() {


    var mContext: Context? = context
    var mimages = uri

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {


        val img=mimages[position]

        val inflator = mContext!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val imgView=inflator.inflate(R.layout.gridadaptorview,null)


        imgView.grid_imageview.setOnClickListener {

           Log.v("CLICKED ID",it.toString())

        }


        imgView.grid_imageview.setImageURI(img)

        return imgView
    }

    override fun getItem(position: Int): Any {
       return mimages[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
      return mimages.size
    }
}