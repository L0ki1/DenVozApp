package app.lk.denunciacd

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registro_correo.*


class Registro_correo : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var login: Button? = null
    private var txt_registrar: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_correo)
        init()

    }
    private fun init() {
        mAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.registro_btn) as Button
        txt_registrar = findViewById(R.id.iniciatxt) as LinearLayout

        login?.setOnClickListener{
            if(registro_correo.text.isEmpty()||registro_contrasena.text.isEmpty()){
                Toast.makeText(applicationContext,"Informacion incorrecta",Toast.LENGTH_LONG).show()
            }else{
                registrar()}
        }
        txt_registrar?.setOnClickListener { logeate() }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.getCurrentUser()
        if (currentUser!=null){
            entrar_app()
            Toast.makeText(applicationContext,"${currentUser!!.email}", Toast.LENGTH_LONG).show()

        }else{
            Toast.makeText(applicationContext,"null", Toast.LENGTH_LONG).show()
        }
    }
    fun registrar(){
        mAuth!!.createUserWithEmailAndPassword(registro_correo.text.toString(), registro_contrasena.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = mAuth!!.getCurrentUser()
                        Toast.makeText(applicationContext, "${user!!.email}",
                                Toast.LENGTH_SHORT).show()
                        espera_login(registro_correo.text.toString(), registro_contrasena.text.toString())
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(applicationContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }

                    // ...
                }
    }

    fun espera_login(correo:String,contrasena:String){
        object : CountDownTimer(3000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                login(correo, contrasena)
            }
        }.start()
    }

    fun login (correo:String,contrasena:String){
        mAuth!!.signInWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = mAuth!!.getCurrentUser()
                        Toast.makeText(applicationContext, "${user!!.email}",
                                Toast.LENGTH_SHORT).show()
                        entrar_app()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(applicationContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }


                }
    }
    private fun entrar_app() {
        val intent = Intent(this, Splash::class.java)
        startActivity(intent)
        finish()
    }
    private fun logeate() {
        val intent = Intent(this, Login_correo::class.java)
        startActivity(intent)
        finish()
    }
}
