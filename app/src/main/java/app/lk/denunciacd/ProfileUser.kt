package app.lk.denunciacd

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ProfileUser : AppCompatActivity() {
    private var nombretxt: TextView? = null
    private var correotxt: TextView? = null
    private var telefonotxt: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)
        init()
        cargar_info()
    }
    private fun init() {
        nombretxt = findViewById(R.id.textView) as TextView
        correotxt = findViewById(R.id.textView6) as TextView
        telefonotxt = findViewById(R.id.textView7) as TextView



    }
    private fun cargar_info() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // Name, email address, and profile photo Url
            val name = user.displayName
            nombretxt?.text=name
            val email = user.email
            correotxt?.text=email
            val telefono = user.phoneNumber
            telefonotxt?.text=telefono
            Toast.makeText(applicationContext,"$name $email", Toast.LENGTH_LONG).show()
        }
    }
}
