package com.example.testprojeckt

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import com.example.testprojeckt.databinding.FragmentFullInfoUsersBinding
import com.squareup.picasso.Picasso
import java.lang.System.load


class FullInfoUsersFragment : Fragment() {

    val args: FullInfoUsersFragmentArgs by navArgs()
    lateinit var binding: FragmentFullInfoUsersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFullInfoUsersBinding.inflate(inflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvLogin.text = args.username
        binding.tvId.text = args.userid
        if (args.useravatar == "empty") {
            binding.imageView2.setImageResource(R.drawable.cee2686dad9a4221694469b5d7f7f8ed)
        } else {
            Picasso.with(context)
                .load(args.useravatar)
                .into(binding.imageView2)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FullInfoUsersFragment()
    }
}