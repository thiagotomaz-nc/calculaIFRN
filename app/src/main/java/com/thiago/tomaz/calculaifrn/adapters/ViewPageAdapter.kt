package com.thiago.tomaz.calculaifrn.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.thiago.tomaz.calculaifrn.CalculadoraBimestral
import com.thiago.tomaz.calculaifrn.CalculadoraSemestral

class ViewPageAdapter(fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
       when(position){
           0 -> return CalculadoraBimestral();
           1 -> return CalculadoraSemestral();
           else -> return CalculadoraBimestral() ;

       }
    }

    override fun getItemCount(): Int {
        return 2;
    }


}