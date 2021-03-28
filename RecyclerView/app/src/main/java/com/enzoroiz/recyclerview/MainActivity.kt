package com.enzoroiz.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val teamsList = listOf(
        Team("Manchester City", "EN"),
        Team("Bayern", "DE"),
        Team("PSG", "FR"),
        Team("Real Madrid", "ES"),
        Team("Juventus", "IT")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lstData.layoutManager = LinearLayoutManager(this)
        lstData.adapter = RecyclerViewAdapter(teamsList) {
            onSelectTeam(it)
        }
    }

    private fun onSelectTeam(team: Team) {
        Toast.makeText(this, "You selected ${team.name} - ${team.countryCode}", Toast.LENGTH_LONG).show()
    }
}