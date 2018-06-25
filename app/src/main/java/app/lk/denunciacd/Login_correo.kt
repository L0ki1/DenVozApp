package app.lk.denunciacd

import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login_correo.*
import java.util.*


class Login_correo : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var login: Button? = null
    private var login_google: ImageButton? = null
    private var login_anon: ImageButton? = null
    private var login_facebook: ImageButton? = null
    private var txt_registrar: LinearLayout? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 9001
    private var mCallbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login_correo)

          init()
    }

    private fun init() {


        login_anon = findViewById(R.id.ib_anon) as ImageButton
        login_anon?.setOnClickListener {
        login_anonimo()
        }

        FacebookSdk.sdkInitialize(getApplicationContext())

        mCallbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(mCallbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    override fun onCancel() {
                        Toast.makeText(applicationContext, "Login Cancel", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(exception: FacebookException) {
                        Toast.makeText(applicationContext, exception.message, Toast.LENGTH_LONG).show()
                    }
                })


        login_facebook = findViewById(R.id.ib_facebook) as ImageButton
        login_facebook?.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"))
        }

        mAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        login_google = findViewById(R.id.ib_google) as ImageButton
        login = findViewById(R.id.login) as Button
        txt_registrar = findViewById(R.id.registratetxt) as LinearLayout

        login_google?.setOnClickListener { login_google() }
        login?.setOnClickListener {
            if (correo.text.isEmpty() || contrasena.text.isEmpty()) {
                Toast.makeText(applicationContext, "Informacion incorrecta", Toast.LENGTH_LONG).show()
            } else {
                login()
            }
        }
        txt_registrar?.setOnClickListener { registrar() }

    }


    private fun login_anonimo() {
        mAuth!!.signInAnonymously()
                .addOnCompleteListener(this, object: OnCompleteListener<AuthResult>{
                   override fun onComplete(@NonNull task: Task<AuthResult>) {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            val user = mAuth!!.getCurrentUser()
                            Toast.makeText(applicationContext, ""+user,Toast.LENGTH_LONG).show()
                            entrar_app()
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(applicationContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }
                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                })
    }


    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        if (currentUser != null) {
            entrar_app()
            Toast.makeText(applicationContext, "${currentUser!!.email}", Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(applicationContext, "null", Toast.LENGTH_LONG).show()
        }
    }


    fun registrar(v: View) {

        mAuth!!.createUserWithEmailAndPassword(correo.text.toString(), contrasena.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = mAuth!!.getCurrentUser()
                        Toast.makeText(applicationContext, "${user!!.email}",
                                Toast.LENGTH_SHORT).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(applicationContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }

                    // ...
                }
    }

    fun login() {
        mAuth!!.signInWithEmailAndPassword(correo.text.toString(), contrasena.text.toString())
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

    private fun registrar() {
        val intent = Intent(this, Registro_correo::class.java)
        startActivity(intent)
        finish()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }

        }
        mCallbackManager!!.onActivityResult(requestCode, resultCode, data)

    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)!!.addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                val user = mAuth?.getCurrentUser()
                Toast.makeText(applicationContext, "${user!!.email}",
                        Toast.LENGTH_SHORT).show()
                entrar_app()
            } else {
                // If sign in fails, display a message to the user.
                Snackbar.make(findViewById(android.R.id.content), "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
            }

            // [START_EXCLUDE]
            // [END_EXCLUDE]
        }
    }

    private fun login_google() {
        val signInIntent = mGoogleSignInClient!!.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    private fun handleFacebookAccessToken(tokenDeAcceso: AccessToken) {
        val credencial = FacebookAuthProvider.getCredential(tokenDeAcceso.token)
        mAuth!!.signInWithCredential(credencial)
                .addOnCompleteListener(this) { task ->
                    if (!task.isSuccessful) {
                        val user = mAuth!!.getCurrentUser();
                        Toast.makeText(applicationContext, "$user$ " + task.exception!!.toString(), Toast.LENGTH_SHORT).show()
                        entrar_app()
                    }
                }
    }



}