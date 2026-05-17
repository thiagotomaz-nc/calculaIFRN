package com.thiago.tomaz.calculaifrn

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.thiago.tomaz.calculaifrn.adapters.ViewPageAdapter
import com.thiago.tomaz.calculaifrn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var biddingMainActivity: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biddingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(biddingMainActivity.root)

        setSupportActionBar(biddingMainActivity.materialToolbar)

        val tabLayout = biddingMainActivity.tabsLayoutSemestralBimestral
        val viewPager2 = biddingMainActivity.viewPagerConteiner
        val viewPagerAdapter = ViewPageAdapter(this)

        viewPager2.adapter = viewPagerAdapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(p0: TabLayout.Tab?) {
                // aba selecionada
                viewPager2.setCurrentItem(p0?.position!!)
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                // aba desmarcada
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
                // aba clicada novamente
            }

        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.select()
                 }


        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.menuItemAvaliar -> {
                abrirPlayStore()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun abrirPlayStore() {
        val appPackageName = packageName

        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName&showAllReviews=true")
                )
            )
        } catch (e: ActivityNotFoundException) {

            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }


}