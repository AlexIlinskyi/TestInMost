package com.production.navdemo1

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.production.navdemo1.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewList: ArrayList<View>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false)
        //viewModel = ViewModelProvider(this).get(SecondFragmentViewModel::class.java)
        viewModel = (activity as MainActivity).viewModel
        binding.toolbarTitle.typeface = Typeface.createFromAsset(activity?.assets, "fonts/UniNeueBold.ttf")
        viewList = ArrayList()
        binding.backIv.setOnClickListener {
            activity?.onBackPressed()
        }
        var tempView = inflater.inflate(R.layout.parameter_item, null, false)
        tempView.findViewById<TextView>(R.id.textView).text = "Поиск по всему"
        tempView.findViewById<ImageView>(R.id.checkedIv).visibility = INVISIBLE
        tempView.setOnClickListener {
            for (cur in viewList){
                cur.findViewById<ImageView>(R.id.checkedIv).visibility = INVISIBLE
            }
            viewList[0].findViewById<ImageView>(R.id.checkedIv).visibility = VISIBLE
            viewModel.searchParameter = 0
        }
        viewList.add(tempView)
        binding.optionsLL.addView(tempView)
        tempView = inflater.inflate(R.layout.parameter_item, null, false)
        tempView.findViewById<TextView>(R.id.textView).text = "По автору"
        tempView.findViewById<ImageView>(R.id.checkedIv).visibility = INVISIBLE
        tempView.setOnClickListener {
            for (cur in viewList){
                cur.findViewById<ImageView>(R.id.checkedIv).visibility = INVISIBLE
            }
            viewList[1].findViewById<ImageView>(R.id.checkedIv).visibility = VISIBLE
            viewModel.searchParameter = 1
        }
        viewList.add(tempView)
        binding.optionsLL.addView(tempView)
        tempView = inflater.inflate(R.layout.parameter_item, null, false)
        tempView.findViewById<TextView>(R.id.textView).text = "По названию"
        tempView.findViewById<ImageView>(R.id.checkedIv).visibility = INVISIBLE
        tempView.setOnClickListener {
            for (cur in viewList){
                cur.findViewById<ImageView>(R.id.checkedIv).visibility = INVISIBLE
            }
            viewList[2].findViewById<ImageView>(R.id.checkedIv).visibility = VISIBLE
            viewModel.searchParameter = 2
        }
        viewList.add(tempView)
        binding.optionsLL.addView(tempView)
        tempView = inflater.inflate(R.layout.parameter_item, null, false)
        tempView.findViewById<TextView>(R.id.textView).text = "По жанру"
        tempView.findViewById<ImageView>(R.id.checkedIv).visibility = INVISIBLE
        tempView.setOnClickListener {
            for (cur in viewList){
                cur.findViewById<ImageView>(R.id.checkedIv).visibility = INVISIBLE
            }
            viewList[3].findViewById<ImageView>(R.id.checkedIv).visibility = VISIBLE
            viewModel.searchParameter = 3
        }
        viewList.add(tempView)
        binding.optionsLL.addView(tempView)
        tempView = inflater.inflate(R.layout.parameter_item, null, false)
        tempView.findViewById<TextView>(R.id.textView).text = "По издателю"
        tempView.findViewById<ImageView>(R.id.checkedIv).visibility = INVISIBLE
        tempView.setOnClickListener {
            for (cur in viewList){
                cur.findViewById<ImageView>(R.id.checkedIv).visibility = INVISIBLE
            }
            viewList[4].findViewById<ImageView>(R.id.checkedIv).visibility = VISIBLE
            viewModel.searchParameter = 4
        }
        viewList.add(tempView)
        binding.optionsLL.addView(tempView)
        viewList[viewModel.searchParameter].findViewById<ImageView>(R.id.checkedIv).visibility = VISIBLE
        return binding.root
    }
}