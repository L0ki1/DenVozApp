package app.lk.denunciacd

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide

class PageView:PagerAdapter {
    var con:Context
    //var path:IntArray
    var path2: ArrayList<String?> = ArrayList()
    lateinit var inflator:LayoutInflater

    constructor(con: Context, path2: ArrayList<String?>) : super() {
        this.con = con
        this.path2 = path2
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object` as RelativeLayout
    }

    override fun getCount(): Int {
return path2.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
var img:ImageView
        var txt:TextView
        inflator=con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var rv:View=inflator.inflate(R.layout.swipe_fragment,container,false)
        img=rv.findViewById(R.id.img) as ImageView
        txt=rv.findViewById(R.id.textView3)
        Glide.with(con).load(path2[position]).into(img)
        if(position==0){
            txt.setText("   Por Arleth")

        }else{
            if(position==1){
                txt.setText("   Por Jessi")

            }else{
                txt.setText("   Por Jesus Alexis")

            }
        }
       // img.setImageResource(path2[position])
        container!!.addView(rv)
        img.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                Toast.makeText(con,"Selecciono: $position",Toast.LENGTH_LONG).show()

            }
        })
        return rv
}

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container!!.removeView(`object` as RelativeLayout)
    }
}