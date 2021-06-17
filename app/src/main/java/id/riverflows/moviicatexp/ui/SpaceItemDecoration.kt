package id.riverflows.moviicatexp.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(private val space: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if(parent.getChildLayoutPosition(view) % 2 != 0){
            outRect.left = space
            outRect.right = 0
            outRect.bottom = space
        }else{
            outRect.left = 0
            outRect.right = space
            outRect.bottom = space
        }
    }
}