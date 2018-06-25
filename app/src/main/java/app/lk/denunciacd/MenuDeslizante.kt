package app.lk.denunciacd

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_menu_deslizante.*
import kotlinx.android.synthetic.main.app_bar_menu_deslizante.*
import kotlinx.android.synthetic.main.content_menu_deslizante.*


class MenuDeslizante : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        Fragment_reporte.OnFragmentInteractionListener,Fragment_misreportes.OnFragmentInteractionListener,Fragment_home.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_deslizante)
        cargar_info()
        cambiar_fragment(Fragment_home())
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        nav_view.setNavigationItemSelectedListener(this)
    }



    private fun cargar_info() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            Toast.makeText(applicationContext,"$name $email",Toast.LENGTH_LONG).show()
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nv_home -> {
                cambiar_fragment(Fragment_home())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nv_reportar -> {

            cambiar_fragment(Fragment_reporte())

                return@OnNavigationItemSelectedListener true
            }
            R.id.nv_misreportes -> {
                cambiar_fragment(Fragment_misreportes())

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
fun ClikPerfil(v: View?){
    usuario()
}
fun cambiar_fragment(f:Fragment){
    supportFragmentManager.beginTransaction()
            .replace(R.id.content_menudeslizante, f).commit()
}
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_deslizante, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun Cerrar_sesion() {
        FirebaseAuth.getInstance().signOut();
        val intent = Intent(this, Login_correo::class.java)
        startActivity(intent)
        finish()
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
cambiar_fragment(Fragment_home())
            }
            R.id.nav_aviso_privacidad -> {

            }
            R.id.nav_cerrar_sesion -> {
                Cerrar_sesion()
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
     fun usuario(){
         val intent = Intent(this, ProfileUser::class.java)
         startActivity(intent)
    }
}
