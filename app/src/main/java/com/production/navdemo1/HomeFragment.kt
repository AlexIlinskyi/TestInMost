package com.production.navdemo1

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.production.navdemo1.adapters.RecyclerViewAdapter
import com.production.navdemo1.databinding.FragmentHomeBinding
import com.production.navdemo1.defaults.Constants
import com.production.navdemo1.model.Book
import com.zaemloans.bistronakartu.network.InfoService
import com.zaemloans.bistronakartu.network.ServiceGenerator
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    //private lateinit var viewModel: MainActivityViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        /*binding.button.setOnClickListener {
            val bundle = bundleOf("user_input" to binding.editTextTextPersonName.text.toString())
            it.findNavController().navigate(R.id.action_homeFragment_to_secondFragment, bundle)
        }*/
        //viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.myViewModel = (activity as MainActivity).viewModel
        if (binding.myViewModel!!.booksList.isNotEmpty()){
            binding.emptyLL.visibility = GONE
            binding.recyclerView.visibility = VISIBLE
        }else{
            binding.emptyLL.visibility = VISIBLE
            binding.recyclerView.visibility = GONE
        }
        binding.emptyTv.typeface = Typeface.createFromAsset(activity?.assets, "fonts/UniNeueBold.ttf")
        binding.searchView.isIconified = false
        binding.settingsIv.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_secondFragment)
        }
        binding.recyclerView.apply{
            layoutManager = GridLayoutManager(activity, 1)
            adapter = RecyclerViewAdapter(context, (binding.myViewModel as MainActivityViewModel).booksList)
        }
        binding.searchView.setOnQueryTextListener(object :android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val infoService: InfoService = ServiceGenerator.createService(InfoService::class.java, null)
                //val call: Call<ResponseBody> = infoService.getBy("https://www.googleapis.com/books/v1/volumes/?q=" + query!!+"&key="+Constants.api_key)
                var myQuery: String? = null
                when((binding.myViewModel as MainActivityViewModel).searchParameter){
                    0->myQuery = "https://www.googleapis.com/books/v1/volumes/?q=" + newText!!
                    1->myQuery = "https://www.googleapis.com/books/v1/volumes/?q=+inauthor:" + newText!!
                    2->myQuery = "https://www.googleapis.com/books/v1/volumes/?q=+intitle:" + newText!!
                    3->myQuery = "https://www.googleapis.com/books/v1/volumes/?q=+subject:" + newText!!
                    4->myQuery = "https://www.googleapis.com/books/v1/volumes/?q=+inpublisher:" + newText!!
                }
                val call: Call<ResponseBody> = infoService.getBy(myQuery!!)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        //Toast.makeText(activity,""+response.body()!!.toString(),Toast.LENGTH_LONG).show();

                        (binding.myViewModel as MainActivityViewModel).booksList = arrayListOf()

                        /*
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setTitle("Success").setMessage(""+response.body()!!.string()).create()
                        val alertDialog = builder.create()
                        alertDialog.show()*/


                        try{
                            val jo = JSONObject(response.body()!!.string())
                            for (i in 0 until jo.getJSONArray("items").length()){
                                var item = jo.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo")
                                (binding.myViewModel as MainActivityViewModel).booksList.add(Book(if (item.has("imageLinks"))
                                    item.getJSONObject("imageLinks").getString("smallThumbnail") else null,
                                    if (item.has("authors"))item.getJSONArray("authors").toString() else null ,
                                    if (item.has("title"))item.getString("title") else null)
                                )
                            }
                        }catch (e:Exception){

                        }
                        if((binding.myViewModel as MainActivityViewModel).booksList.isNotEmpty()){
                            binding.emptyLL.visibility = GONE
                            binding.recyclerView.visibility  = VISIBLE
                        }else{
                            binding.emptyLL.visibility = VISIBLE
                            binding.recyclerView.visibility  = GONE
                        }


                        binding.recyclerView.apply{
                            layoutManager = GridLayoutManager(activity, 1)
                            adapter = RecyclerViewAdapter(context, (binding.myViewModel as MainActivityViewModel).booksList)
                        }

                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        //Toast.makeText(applicationContext, "NO", Toast.LENGTH_LONG).show()

                        val builder = AlertDialog.Builder(requireContext())
                        builder.setTitle("Произошла ошибка").setMessage("Нет подключения к сети.").create()
                        val alertDialog = builder.create()
                        alertDialog.show()
                    }
                })
                return true
            }

        })
        return binding.root
    }
}