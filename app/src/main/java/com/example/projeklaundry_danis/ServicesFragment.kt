package com.example.projeklaundry_danis

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projeklaundry_danis.databinding.FragmentServicesBinding

class ServicesFragment : Fragment() {
    private var _binding: FragmentServicesBinding? = null
    private val binding get() = _binding!!
    private lateinit var servicesAdapter: ServicesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        servicesAdapter = ServicesAdapter(
            listOf(
                LaundryService(
                    "Cuci + Setrika Kiloan",
                    "Estimasi 2 Hari • Bersih & Wangi",
                    "Rp 6.500 / kg"
                ),
                LaundryService(
                    "Laundry Satuan (Jaket/Selimut)",
                    "Estimasi 3 Hari • Perawatan Khusus",
                    "Mulai Rp 15.000 / pcs"
                ),
                LaundryService(
                    "Setrika Saja",
                    "Estimasi 1 Hari • Rapi & Licin",
                    "Rp 5.000 / kg"
                ),
                LaundryService(
                    "Express Kilat (6 Jam)",
                    "Selesai dalam 6 jam • Prioritas",
                    "Rp 12.000 / kg"
                )
            )
        )
        binding.servicesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.servicesRecyclerView.adapter = servicesAdapter
        updateEmptyState()

        binding.searchServicesInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                servicesAdapter.filterServices(s?.toString().orEmpty())
                updateEmptyState()
            }

            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun updateEmptyState() {
        val isEmpty = servicesAdapter.itemCount == 0
        binding.emptyServicesMessage.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.servicesRecyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
