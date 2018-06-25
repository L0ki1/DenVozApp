package app.lk.denunciacd

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class Tipos_reportes : AppCompatActivity() {
private var siguiente:Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipos_reportes)

        siguiente=findViewById(R.id.button2)

        siguiente?.setOnClickListener { val intent = Intent(this, Tipos_reportes2::class.java)
            startActivity(intent) }

    }
}
