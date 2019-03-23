package com.sdl.primer.motion

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.motion.MotionLayout
import android.widget.CompoundButton
import com.sdl.primer.R
import kotlinx.android.synthetic.main.activity_motion.*

class MotionActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {


    private var doShowPaths = false

    private val viewAdapter by lazy {
        DemosAdapter(dataset)
    }

    private val dataset: Array<DemosAdapter.Demo> = arrayOf(
            DemosAdapter.Demo("Basic Example (1/2)", "Basic motion example using referenced ConstraintLayout files", R.layout.motion_01_basic),
            DemosAdapter.Demo("Basic Example (2/2)", "Basic motion example using ConstraintSets defined in the MotionScene file", R.layout.motion_02_basic),
            DemosAdapter.Demo("Custom Attribute", "Show color interpolation (custom attribute)", R.layout.motion_03_custom_attribute),
            DemosAdapter.Demo("ImageFilterView (1/2)", "Show image cross-fade (using ML's ImageFilterView + custom attribute)", R.layout.motion_04_imagefilter),
            DemosAdapter.Demo("ImageFilterView (2/2)", "Show image saturation transition (using ML's ImageFilterView + custom attribute)", R.layout.motion_05_imagefilter),
            DemosAdapter.Demo("Keyframe Position (1/3)", "Use a simple keyframe to change the interpolated motion", R.layout.motion_06_keyframe),
            DemosAdapter.Demo("Keyframe Interpolation (2/3)", "More complex keyframe, adding rotation interpolation", R.layout.motion_07_keyframe),
            DemosAdapter.Demo("Keyframe Cycle (3/3)", "Basic example of using a keyframe cycle ", R.layout.motion_08_cycle),
            DemosAdapter.Demo("CoordinatorLayout Example (1/3)", "Basic example of using MotionLayout instead of AppBarLayout", R.layout.motion_09_coordinatorlayout),
            DemosAdapter.Demo("CoordinatorLayout Example (2/3)", "Slightly more complex example of MotionLayout replacing AppBarLayout, with multiple elements and parallax background", R.layout.motion_10_coordinatorlayout),
            DemosAdapter.Demo("CoordinatorLayout Example (3/3)", "Another AppBarLayout replacement example", R.layout.motion_11_coordinatorlayout)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion)

        recyclerview.apply {
            setHasFixedSize(true)
            adapter = viewAdapter
        }

        showPaths.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        doShowPaths = isChecked
    }

    fun start(activity: Class<*>, layoutFileId: Int) {
        val intent = Intent(this, activity).apply {
            putExtra("layout_file_id", layoutFileId)
            putExtra("showPaths", doShowPaths)
        }
        startActivity(intent)
    }
}
