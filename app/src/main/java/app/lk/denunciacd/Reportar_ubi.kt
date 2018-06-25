package app.lk.denunciacd

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Button

import kotlinx.android.synthetic.main.activity_reportar_ubi.*
import kotlinx.android.synthetic.main.fragment_fragment_reporte.*

class Reportar_ubi : AppCompatActivity() {
    private val cons = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reportar_ubi)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val boton1: Button
        boton1 = findViewById(R.id.camara) as Button
        boton1.setOnClickListener {
            init()

        }
        startActivityForResult(intent,cons)
    }
    fun init() {


        val intent:Intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,cons)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(resultCode == Activity.RESULT_OK){

            val ext:Bundle= data!!.extras
            val bit: Bitmap = ext.get("data") as Bitmap
            imageView.setImageBitmap(bit)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
