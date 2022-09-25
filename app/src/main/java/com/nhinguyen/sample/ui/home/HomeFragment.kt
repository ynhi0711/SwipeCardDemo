package com.nhinguyen.sample.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import android.view.animation.PathInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nhinguyen.sample.R
import com.nhinguyen.sample.databinding.CardViewProfileBinding
import com.nhinguyen.sample.databinding.FragmentHomeBinding
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.Duration
import com.yuyakaido.android.cardstackview.RewindAnimationSetting
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import com.yuyakaido.android.cardstackview.SwipeableMethod
import java.util.ArrayList

class HomeFragment : Fragment(), CardStackListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private val manager by lazy { CardStackLayoutManager(context, this) }
    private val adapter by lazy {
        CardStackAdapter(createSpots(),
            onLike = {
                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .setDuration(Duration.Slow.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
                manager.setSwipeAnimationSetting(setting)
                _binding?.cardStackView?.swipe()
            },
            onNope = {
                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .setDuration(Duration.Slow.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
                manager.setSwipeAnimationSetting(setting)
                _binding?.cardStackView?.swipe()

            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCardStackView()
        setupButton()
    }

    private fun setupCardStackView() {
        initialize()
    }

    private fun setupButton() {
//        _binding?.skipButton?.setOnClickListener {
//            val setting = SwipeAnimationSetting.Builder()
//                .setDirection(Direction.Left)
//                .setDuration(Duration.Normal.duration)
//                .setInterpolator(AccelerateInterpolator())
//                .build()
//            manager.setSwipeAnimationSetting(setting)
//            _binding?.cardStackView?.swipe()
//        }
//
//        _binding?.rewindButton?.setOnClickListener {
//            val setting = RewindAnimationSetting.Builder()
//                .setDirection(Direction.Bottom)
//                .setDuration(Duration.Normal.duration)
//                .setInterpolator(DecelerateInterpolator())
//                .build()
//            manager.setRewindAnimationSetting(setting)
//            _binding?.cardStackView?.rewind()
//        }
//
//        _binding?.likeButton?.setOnClickListener {
//            val setting = SwipeAnimationSetting.Builder()
//                .setDirection(Direction.Right)
//                .setDuration(Duration.Normal.duration)
//                .setInterpolator(AccelerateInterpolator())
//                .build()
//            manager.setSwipeAnimationSetting(setting)
//            _binding?.cardStackView?.swipe()
//        }
    }

    private fun initialize() {
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.9f)
        manager.setSwipeThreshold(0.5f)
        manager.setMaxDegree(30.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(false)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(AccelerateInterpolator())
        _binding?.cardStackView?.layoutManager = manager
        _binding?.cardStackView?.adapter = adapter
        _binding?.cardStackView?.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction) {
        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
        Toast.makeText(context, "Card Swiped to $direction ", Toast.LENGTH_SHORT).show()
        if (manager.topPosition == adapter.itemCount - 5) {
//            paginate()
        }
    }

    override fun onCardRewound() {
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
    }

    override fun onCardDisappeared(view: View?, position: Int) {
    }

    private fun createSpot(): Spot {
        return Spot(
            name = "Yasaka Shrine",
            city = "Kyoto",
            url = "https://source.unsplash.com/Xq1ntWruZQI/600x800"
        )
    }

    private fun createSpots(): List<Spot> {
        val spots = ArrayList<Spot>()
        spots.add(
            Spot(
                name = "Yasaka Shrine",
                city = "Kyoto",
                url = "https://source.unsplash.com/Xq1ntWruZQI/600x800"
            )
        )
        spots.add(
            Spot(
                name = "Fushimi Inari Shrine",
                city = "Kyoto",
                url = "https://source.unsplash.com/NYyCqdBOKwc/600x800"
            )
        )
        spots.add(
            Spot(
                name = "Bamboo Forest",
                city = "Kyoto",
                url = "https://source.unsplash.com/buF62ewDLcQ/600x800"
            )
        )
        spots.add(
            Spot(
                name = "Brooklyn Bridge",
                city = "New York",
                url = "https://source.unsplash.com/THozNzxEP3g/600x800"
            )
        )
        spots.add(
            Spot(
                name = "Empire State Building",
                city = "New York",
                url = "https://source.unsplash.com/USrZRcRS2Lw/600x800"
            )
        )
        spots.add(
            Spot(
                name = "The statue of Liberty",
                city = "New York",
                url = "https://source.unsplash.com/PeFk7fzxTdk/600x800"
            )
        )
        spots.add(
            Spot(
                name = "Louvre Museum",
                city = "Paris",
                url = "https://source.unsplash.com/LrMWHKqilUw/600x800"
            )
        )
        spots.add(
            Spot(
                name = "Eiffel Tower",
                city = "Paris",
                url = "https://source.unsplash.com/HN-5Z6AmxrM/600x800"
            )
        )
        spots.add(
            Spot(
                name = "Big Ben",
                city = "London",
                url = "https://source.unsplash.com/CdVAUADdqEc/600x800"
            )
        )
        spots.add(
            Spot(
                name = "Great Wall of China",
                city = "China",
                url = "https://source.unsplash.com/AWh9C-QjhE4/600x800"
            )
        )
        return spots
    }
}
