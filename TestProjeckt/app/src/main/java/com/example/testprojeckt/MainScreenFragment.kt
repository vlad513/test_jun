package com.example.testprojeckt

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.testprojeckt.Adapter.AdapterRecicler
import com.example.testprojeckt.Server.DataUser
import com.example.testprojeckt.ViewModels.ViewModelMainScreen
import com.example.testprojeckt.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment() {
    private lateinit var vm: ViewModelMainScreen
    lateinit var repoResponses: List<DataUser>
    private val adapter = AdapterRecicler { callback1(it) }
    lateinit var browserIntent: Intent
    lateinit var binding: FragmentMainScreenBinding
    private val clientId: String = "8df790ce4c06cf6c04ca"
    private val clientSecret: String = "1d0bbb5ef5c58c8ad4eaf96cb5fb7b482e75c003"
    private val redirectUri: String = "test://callback"

    fun callback1(repoResponse: DataUser) {
        if(repoResponse.avatar==null){ findNavController().navigate(
            MainScreenFragmentDirections.actionMainScreenToFullInfoUsersFragment(
                repoResponse.login,
                repoResponse.id,
                "empty"/*,repoResponse.comany,repoResponse.location,repoResponse.email,repoResponse.created_at*/
            )
        )}
       else{ findNavController().navigate(
            MainScreenFragmentDirections.actionMainScreenToFullInfoUsersFragment(
                repoResponse.login,
                repoResponse.id,
                repoResponse.avatar
                /*,repoResponse.comany,repoResponse.location,repoResponse.email,repoResponse.created_at*/
            )
        )}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainScreenBinding.inflate(inflater)
        vm = ViewModelProvider(this).get(ViewModelMainScreen::class.java)
        browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://github.com/login/oauth/authorize" + "?client_id=" + clientId + "&scope=repo&redirect_uri=" + redirectUri)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val swipe: SwipeRefreshLayout? = binding.swipeRefresh
        swipe?.setOnRefreshListener {
            vm.configureRetrofitq()
            if (swipe.isRefreshing) {
                swipe.isRefreshing = false
            }
        }
        init()
        binding.buttonStart?.setOnClickListener {
            startActivity(browserIntent)
        }

        lifecycleScope.launchWhenStarted {
            vm.userInfo.collect {
                repoResponses = it
                adapter.addRepoz(repoResponses)
            }
        }
    }

    private fun init() {
        with(binding) {
            rcView?.layoutManager = LinearLayoutManager(requireContext())
            rcView?.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        val data = requireActivity().intent.data
        if (data != null) {
            val code = data.getQueryParameter("code")
            vm.GetGitTocken(code!!, clientId, clientSecret)
            binding.buttonStart!!.visibility = View.GONE
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainScreenFragment()
    }
}

