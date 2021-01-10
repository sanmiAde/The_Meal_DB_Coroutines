package com.sanmidev.themealdbcoroutines.features.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sanmidev.themealdbcoroutines.R
import com.sanmidev.themealdbcoroutines.databinding.MealsFragmentBinding
import com.sanmidev.themealdbcoroutines.utils.*
import dagger.hilt.android.AndroidEntryPoint
import io.cabriole.decorator.ColumnProvider
import io.cabriole.decorator.GridMarginDecoration
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@AndroidEntryPoint
class MealsFragment : Fragment() {

    @Inject
    lateinit var mealViewModelFactory: MealsViewModel.AssistedFactory

    private val args by navArgs<MealsFragmentArgs>()

    private val viewModel by viewModels<MealsViewModel> {
        MealsViewModel.provideFactory(mealViewModelFactory, args.mealName)
    }

    private var mealAdapter : MealsAdapter? = null

    private var _binding : MealsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  MealsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerview()

        lifecycleScope.launchWhenStarted {
            viewModel.mealNetworkState.collect { uiState ->
                when(uiState){
                    NetworkState.Initial -> {/** NO Op **/ }
                    NetworkState.Loading -> {
                        binding.pbMeals.show()
                    }
                    is NetworkState.Success -> {
                        if(uiState.data.isNotEmpty()){
                            mealAdapter?.submitList(uiState.data)
                        }else{
                            Snackbar.make(binding.root, "There are no meals for this category", Snackbar.LENGTH_SHORT).show()
                        }
                        binding.pbMeals.gone()

                    }
                    is NetworkState.Error ->{
                        Snackbar.make(binding.root, "Could not get Meal", Snackbar.LENGTH_SHORT).show()
                        binding.pbMeals.gone()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        mealAdapter = null
        super.onDestroyView()
    }

    private fun setupRecyclerview(){
        mealAdapter = MealsAdapter {

        }

        binding.rvMeal.apply {
            val spanCount = context.resources.getInteger(R.integer.recycler_view_span_count)
            val margin = context.resources.dpToPx(MARGIN_SIZE)

            layoutManager = GridLayoutManager(requireContext(), spanCount)
            this.addItemDecoration(GridMarginDecoration(margin, object : ColumnProvider {
                override fun getNumberOfColumns(): Int = spanCount
            }, orientation = RecyclerView.VERTICAL))

            setHasFixedSize(true)
            adapter = mealAdapter
        }
    }
}