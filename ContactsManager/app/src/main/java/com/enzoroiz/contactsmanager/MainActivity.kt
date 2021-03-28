package com.enzoroiz.contactsmanager

import android.Manifest.permission.READ_CONTACTS
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.Contacts.CONTENT_URI
import android.provider.ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

private const val REQUEST_CODE_READ_CONTACTS = 100

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val hasReadContactsPermission = ContextCompat.checkSelfPermission(this, READ_CONTACTS)
        if (hasReadContactsPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(READ_CONTACTS), REQUEST_CODE_READ_CONTACTS)
        }

        fab.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {
                Snackbar.make(
                    it, "Please grant access to your Contacts",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("GRANT ACCESS") {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, READ_CONTACTS)) {
                        ActivityCompat.requestPermissions(this, arrayOf(READ_CONTACTS), REQUEST_CODE_READ_CONTACTS)
                    } else {
                        val intent = Intent()
                        intent.action = ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package", this.packageName, null)

                        intent.data = uri
                        this.startActivity(intent)
                    }
                }.show()
                return@setOnClickListener
            }

            val projection = arrayOf(DISPLAY_NAME_PRIMARY)
            val cursor = contentResolver.query(
                    CONTENT_URI,
                    projection,
                    null,
                    null,
                    DISPLAY_NAME_PRIMARY
            )

            val contacts = arrayListOf<String>()
            cursor?.use { c ->
                while (c.moveToNext()) {
                    contacts.add(
                        c.getString(
                            c.getColumnIndex(DISPLAY_NAME_PRIMARY)
                        )
                    )
                }
            }

            val adapter = ArrayAdapter<String>(this, R.layout.contact_detail, R.id.name, contacts)
            list_contacts.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}