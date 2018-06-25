package app.lk.denunciacd

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class InicioLogins : AppCompatActivity() {
private var registrar:Button?=null
    private var login_redes:Button?=null
    private var txt_login:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_logins)
        registrar=findViewById(R.id.btn_regis)
        login_redes=findViewById(R.id.btn_redes)
        txt_login=findViewById(R.id.txt_login)

        registrar?.setOnClickListener { registrar() }
        login_redes?.setOnClickListener { login() }
        txt_login?.setOnClickListener { login() }
            }
    private fun registrar() {
        val intent = Intent(this, Registro_correo::class.java)
        startActivity(intent)
    }
    private fun login() {
        val intent = Intent(this, Login_correo::class.java)
        startActivity(intent)
    }
}
