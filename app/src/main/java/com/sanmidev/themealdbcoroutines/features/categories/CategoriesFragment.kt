package com.sanmidev.themealdbcoroutines.features.categories

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sanmidev.themealdbcoroutines.R
import com.sanmidev.themealdbcoroutines.data.model.category.CategoryModel
import com.sanmidev.themealdbcoroutines.databinding.FragmentCategoriesBinding
import com.sanmidev.themealdbcoroutines.features.meal.MealsFragmentArgs
import com.sanmidev.themealdbcoroutines.utils.*
import dagger.hilt.android.AndroidEntryPoint
import io.cabriole.decorator.ColumnProvider
import io.cabriole.decorator.GridMarginDecoration
import kotlinx.coroutines.flow.collect



@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private val viewModel by viewModels<CategoriesViewModel>()

    private var _binding :  FragmentCategoriesBinding? = null
    private val binding : FragmentCategoriesBinding get() = _binding!!

    private var categoryAdapter: CategoryAdapter? = null

    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        setHasOptionsMenu(true)

        lifecycleScope.launchWhenResumed {
            viewModel.getCategoriesNetworkState.collect { uiState ->
                when(uiState){
                    is NetworkState.Loading -> {
                        binding.progressBar.show()
                    }
                    is NetworkState.Success -> {
                        binding.progressBar.gone()
                        categoryAdapter!!.submitList(uiState.data)
                    }
                    is NetworkState.Error -> {
                        binding.progressBar.gone()
                    }
                    NetworkState.NotFired -> { }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_category, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when(item.itemId){
           R.id.item_settings -> {
               val directions = CategoriesFragmentDirections.actionCategoriesFragmentToSettingsFragment()
               navController.navigate(directions)
               true
           }
           else -> {
               true
           }
       }
    }

    override fun onDestroyView() {
        categoryAdapter = null
        _binding = null
        super.onDestroyView()
    }


    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter { categoryModel: CategoryModel ->
            val directions = CategoriesFragmentDirections.actionCategoriesFragmentToMealsFragment(categoryModel.category)

            navController.navigate(directions)
        }

        binding.rvCategory.apply {
            val spanCount = context.resources.getInteger(R.integer.recycler_view_span_count)
            val margin = context.resources.dpToPx(MARGIN_SIZE)

            layoutManager = GridLayoutManager(requireContext(), spanCount)
            this.addItemDecoration(GridMarginDecoration(margin, object : ColumnProvider {
                override fun getNumberOfColumns(): Int = spanCount
            }, orientation = RecyclerView.VERTICAL))

            setHasFixedSize(true)
            adapter = categoryAdapter
        }
    }
}
