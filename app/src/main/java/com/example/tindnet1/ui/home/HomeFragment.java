package com.example.tindnet1.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tindnet1.Company;
import com.example.tindnet1.CompanyAdapter;
import com.example.tindnet1.R;
import com.example.tindnet1.databinding.FragmentHomeBinding;
// Clases importadas para lo de las tarjetas
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ViewPager2 viewPager;
    private CompanyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewPager = root.findViewById(R.id.viewPager);

        List<Company> companies = new ArrayList<>();
        companies.add(new Company(R.drawable.foto_empresa_random, "Description for Company 1"));
        companies.add(new Company(R.drawable.random_foto_2, "Description for Company 2"));
        // Añade más empresas según sea necesario

        adapter = new CompanyAdapter(companies);
        viewPager.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
