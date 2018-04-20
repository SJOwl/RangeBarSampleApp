# Material range bar
<img src="https://github.com/SJOwl/RangeBarSampleApp/blob/master/screen.png?raw=true" alt="screen.png" width="40%">

# Features
Material thumbs with shades.

Fully controlled colors, shades, dimensions

# Usage
    1. download lib module

    2.xml
    <au.sj.owl.rangebarsampleapp.materialrangebar.MaterialRangeBar
            android:id="@+id/bar1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="20dp"
            app:sjmrb_animationDuarionMS="250"
            app:sjmrb_rangeInnerColor="#ff5500"
            app:sjmrb_rangeInnerThickness="4dp"
            app:sjmrb_rangeOuterColor="#dedede"
            app:sjmrb_rangeOuterThickness="4dp"
            app:sjmrb_thumbColor="#e4e4e4"
            app:sjmrb_thumbPosLeft="10"
            app:sjmrb_thumbPosRight="60"
            app:sjmrb_thumbRadiusActive="16dp"
            app:sjmrb_thumbRadiusFree="16dp"
            app:sjmrb_thumbShadowColor="#7f000000"
            app:sjmrb_thumbShadowDX="2dp"
            app:sjmrb_thumbShadowDY="2dp"
            app:sjmrb_thumbShadowRadius="5dp"
            app:sjmrb_valueMax="100"
            app:sjmrb_valueMin="0" />
            
    3.
    Code:
    bar1.setRangeChangeListener { left, right ->
        Log.d("tag", "left: $left, right: $right")
    }
