package ru.skillbox.feature_film_page.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.skillbox.core.domain.entities.MovieDetails
import ru.skillbox.core.domain.entities.StaffItem
import ru.skillbox.core.ui.fragments.BindFragment
import ru.skillbox.core.utils.States
import ru.skillbox.feature_film_page.R
import ru.skillbox.feature_film_page.databinding.FilmPageFragmentBinding
import ru.skillbox.feature_film_page.di.FilmPageComponentProvider
import ru.skillbox.feature_film_page.models.TranslatableSeason
import ru.skillbox.feature_film_page.models.TranslatableStaffItem
import ru.skillbox.feature_film_page.ui.adapters.ActorsAdapter
import ru.skillbox.feature_film_page.ui.adapters.SeasonAdapter
import ru.skillbox.feature_film_page.ui.adapters.StaffAdapter
import ru.skillbox.feature_film_page.ui.viewmodels.FilmPageViewModel
import ru.skillbox.feature_film_page.utils.LayoutTransitionProcessor
import ru.skillbox.feature_film_page.utils.MovieDetailsProcessor

class FilmPageFragment : BindFragment<FilmPageFragmentBinding>(FilmPageFragmentBinding::inflate) {

    private val viewModel by activityViewModels<FilmPageViewModel> {
        (requireContext().applicationContext as FilmPageComponentProvider)
            .filmPageComponent()
            .filmPageViewModelFactory()
    }
    private lateinit var layoutTransitionProcessor: LayoutTransitionProcessor
    private lateinit var seasonAdapter: SeasonAdapter
    private lateinit var staffAdapter: StaffAdapter
    private lateinit var actorsAdapter: ActorsAdapter
    private var isShortDescriptionCollapsed = true
    private var isFullDescriptionCollapsed = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
//        initAdapters()
        initObservers()
        initLayoutTransitionProcessor()
        initMovieDetails()
    }

    private fun initToolbar() {
        with(binding.filmPageToolbar) {
            setupWithNavController(findNavController())
            title = null
            setNavigationIcon(R.drawable.film_page_toolbar_back_button_icon)
        }
    }

    private fun initObservers() {
        viewModel.stateFlow.onEach { handleState(it) }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.movieDetailsFlow.onEach { filmDetails ->
            initListeners(filmDetails)
            bindMovieDetails(filmDetails)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

//        viewModel.seasonsFlow.onEach { seasons ->
//            handleSeasons(seasons)
//        }.launchIn(viewLifecycleOwner.lifecycleScope)
//
//        viewModel.staffFlow.onEach { staffItems ->
//            handleStaffCollection(staffItems, false)
//        }.launchIn(viewLifecycleOwner.lifecycleScope)
//
//        viewModel.actorsFlow.onEach { actors ->
//            handleStaffCollection(actors, true)
//        }.launchIn(viewLifecycleOwner.lifecycleScope)

        combine(
            viewModel.seasonsFlow,
            viewModel.staffFlow,
            viewModel.actorsFlow
        ) { seasons, staff, actors ->
            initAdapters(seasons.isNotEmpty())
            handleSeasons(seasons)
            handleStaffCollection(actors, true)
            handleStaffCollection(staff, false)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initAdapters(isSerial: Boolean) {
        initStaffAdapter()
        initActorsAdapter()
        val adapter =
            if (isSerial) {
                initSeasonAdapter()
                ConcatAdapter(seasonAdapter, actorsAdapter, staffAdapter)
            } else {
                ConcatAdapter(actorsAdapter, staffAdapter)
            }
        binding.filmPageCollections.adapter = adapter
    }

    private fun initLayoutTransitionProcessor() {
        this.layoutTransitionProcessor = LayoutTransitionProcessor(
            binding.textParentLayout,
            binding.descriptionShortContentTextView
        )
    }

    private fun initMovieDetails() {
        val key = resources.getString(ru.skillbox.core.R.string.film_id_key)
        val id = requireArguments().getLong(key)
        viewModel.getMovieDetails(id)
    }

    private fun initSeasonAdapter() {
        val title = resources.getString(R.string.film_page_season_collection_title_text)
        val allButtonText =
            resources.getString(R.string.film_page_season_collection_all_button_text)
        seasonAdapter =
            SeasonAdapter(
                title,
                allButtonText,
                { onClickAllSeasons(it) },
                { onSeasonItemClick(it) })
    }

    private fun initStaffAdapter() {
        val title = resources.getString(R.string.film_page_staff_collection_title_text)
        staffAdapter = StaffAdapter(title, { onClickAllStaff(it) }, { onStaffItemClick(it) })
    }

    private fun initActorsAdapter() {
        val title = resources.getString(R.string.film_page_actors_collection_title_text)
        actorsAdapter = ActorsAdapter(title, { onClickAllStaff(it) }, { onStaffItemClick(it) })
    }

    private fun initListeners(movieDetails: MovieDetails) {
        with(binding) {
            descriptionShortContentTextView.setOnClickListener { shortDescriptionTextView ->
                val shortText = movieDetails.shortDescription ?: ""
                onDescriptionClick(shortDescriptionTextView, isShortDescriptionCollapsed, shortText)
                isShortDescriptionCollapsed = !isShortDescriptionCollapsed
            }
            descriptionContentTextView.setOnClickListener { fullDescriptionTextView ->
                val longText = movieDetails.description ?: ""
                onDescriptionClick(fullDescriptionTextView, isFullDescriptionCollapsed, longText)
                isFullDescriptionCollapsed = !isFullDescriptionCollapsed
            }
        }
    }

    private fun bindMovieDetails(movieDetails: MovieDetails) {
        MovieDetailsProcessor.setRatingNameValue(movieDetails, binding.filmRatingName)
        MovieDetailsProcessor.setYearGenresValue(movieDetails, binding.filmYearGenres)
        setCountriesLengthAgeLimit(movieDetails)

        val shortText = movieDetails.shortDescription
        if (shortText == null || shortText.isEmpty()) {
            binding.descriptionShortContentTextView.visibility = View.GONE
        } else {
            binding.descriptionShortContentTextView.text =
                layoutTransitionProcessor.cutText(shortText)
        }

        val longText = movieDetails.description
        if (longText == null || longText.isEmpty()) {
            binding.descriptionContentTextView.visibility = View.GONE
        } else {
            binding.descriptionContentTextView.text = layoutTransitionProcessor.cutText(longText)
        }

        if (movieDetails.logoUrl != null) {
            setPoster(movieDetails.coverUrl, movieDetails.logoUrl)
        } else {
            setPoster(movieDetails.posterUrl, null)
        }
    }

    private fun onStaffItemClick(staff: StaffItem) {
        TODO("Not yet implemented")
    }

    private fun onSeasonItemClick(season: TranslatableSeason) {
        TODO("Not yet implemented")
    }

    private fun onClickAllStaff(all: List<StaffItem>) {
        TODO("Not yet implemented")
    }

    private fun onClickAllSeasons(all: List<TranslatableSeason>) {
        TODO("Not yet implemented")
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleSeasons(seasons: List<TranslatableSeason>) {
        if (seasons.isNotEmpty()) {
            seasonAdapter.setSeasons(seasons)
            seasonAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleStaffCollection(
        staffCollection: List<TranslatableStaffItem>,
        areActors: Boolean
    ) {
        if (areActors) {
            actorsAdapter.setItems(staffCollection)
            actorsAdapter.notifyDataSetChanged()
        } else {
            staffAdapter.setItems(staffCollection)
            staffAdapter.notifyDataSetChanged()
        }
    }

    private fun handleState(state: States) {
        when (state) {
            States.LOADING -> binding.progressBarFilmPage.visibility = View.VISIBLE
            States.COMPLETE -> binding.progressBarFilmPage.visibility = View.GONE
        }
    }

    private fun setCountriesLengthAgeLimit(movieDetails: MovieDetails) {
        MovieDetailsProcessor.setCountriesLengthAgeLimit(
            movieDetails,
            binding.filmCountriesLengthAgeLimit,
            resources.getString(R.string.film_countries_length_age_limit_text)
        )
    }

    private fun setPoster(posterUrl: String?, logoUrl: String?) {
        with(binding) {
            Glide
                .with(requireContext())
                .load(posterUrl)
                .placeholder(R.drawable.film_page_poster_place_holder)
                .into(filmPageExpandedImage)

            Glide
                .with(requireContext())
                .load(logoUrl)
                .into(logoImage)
        }
    }

    private fun onDescriptionClick(view: View, isCollapsed: Boolean, description: String) {
        layoutTransitionProcessor.onDescriptionClick(view, isCollapsed, description)
    }
}