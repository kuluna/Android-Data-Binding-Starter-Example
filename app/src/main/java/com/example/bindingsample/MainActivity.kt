package com.example.bindingsample

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bindingsample.databinding.ActivityMainBinding
import com.example.bindingsample.databinding.ListRepositoryBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel

        val adapter = RepositoryAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.repositories.observe(this, Observer { response ->
            response?.let { adapter.items = it }
        })
    }

    class RepositoryAdapter(context: Context) : RecyclerView.Adapter<Holder>() {
        private val inflater = LayoutInflater.from(context)
        var items: List<RepositoryItem> = emptyList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun getItemCount(): Int = items.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val binding: ListRepositoryBinding = DataBindingUtil.inflate(inflater, R.layout.list_repository, parent, false)
            return Holder(binding)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.binding.item = items[position]
            holder.binding.executePendingBindings()
        }
    }

    class Holder(val binding: ListRepositoryBinding) : RecyclerView.ViewHolder(binding.root)
}
