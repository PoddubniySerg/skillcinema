package ru.skillbox.feature_film_page.utils

import android.animation.LayoutTransition
import android.text.InputFilter
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout

class LayoutTransitionProcessor(
    private val textParentLayout: ConstraintLayout,
    private val descriptionShortContentTextView: AppCompatTextView
) {

    companion object {
        private const val MAX_LENGTH_DESCRIPTION_COLLAPSED = 250
        private const val TRANSITION_TEXT_DURATION = 500L
        private const val IDLE_ANIMATION_STATE = 1
        private const val EXPANDING_ANIMATION_STATE = 2
        private const val COLLAPSING_ANIMATION_STATE = 3
    }

    private var shortDescriptionAnimationState = IDLE_ANIMATION_STATE
    private var longDescriptionAnimationState = IDLE_ANIMATION_STATE

    init {
        val transition = LayoutTransition()
        transition.setDuration(TRANSITION_TEXT_DURATION)
        transition.enableTransitionType(LayoutTransition.CHANGING)
        textParentLayout.layoutTransition = transition
        transition.addTransitionListener(
            object : LayoutTransition.TransitionListener {
                override fun startTransition(
                    transition: LayoutTransition?,
                    container: ViewGroup?,
                    view: View?,
                    transitionType: Int
                ) {
//                    TODO nothing
                }

                override fun endTransition(
                    transition: LayoutTransition?,
                    container: ViewGroup?,
                    view: View?,
                    transitionType: Int
                ) {
                    view?.let {
                        val currentAnimationState: Int
                        if (view == descriptionShortContentTextView) {
                            currentAnimationState = shortDescriptionAnimationState
                            shortDescriptionAnimationState = IDLE_ANIMATION_STATE
                        } else {
                            currentAnimationState = longDescriptionAnimationState
                            longDescriptionAnimationState = IDLE_ANIMATION_STATE
                        }
                        if (currentAnimationState == COLLAPSING_ANIMATION_STATE) {
                            setLengthFilter(view as AppCompatTextView, view.text.toString())
                        }
                    }
                }
            }
        )
    }

    fun onDescriptionClick(view: View, isCollapsed: Boolean, description: String) {
        var currentAnimationState =
            if (view == descriptionShortContentTextView) {
                shortDescriptionAnimationState
            } else {
                longDescriptionAnimationState
            }
        if (currentAnimationState != IDLE_ANIMATION_STATE) {
            textParentLayout.layoutTransition =
                textParentLayout.layoutTransition
        }
        if (view is AppCompatTextView) {
            if (isCollapsed) {
                currentAnimationState = EXPANDING_ANIMATION_STATE
                removeLengthFilter(view, description)
            } else {
                currentAnimationState = COLLAPSING_ANIMATION_STATE
                setLengthFilter(view, description)
                view.post { removeLengthFilter(view, description) }
            }
            if (view == descriptionShortContentTextView) {
                shortDescriptionAnimationState = currentAnimationState
            } else {
                longDescriptionAnimationState = currentAnimationState
            }
        }
    }

    fun cutText(text: String): String {
        return if (text.length > MAX_LENGTH_DESCRIPTION_COLLAPSED) {
            "${text.substring(0, MAX_LENGTH_DESCRIPTION_COLLAPSED - 1)}..."
        } else {
            text
        }
    }

    private fun setLengthFilter(view: AppCompatTextView, text: String) {
        val filter = InputFilter.LengthFilter(MAX_LENGTH_DESCRIPTION_COLLAPSED + 3)
        view.filters = arrayOf(filter)
        view.text = cutText(text)
    }

    private fun removeLengthFilter(view: AppCompatTextView, text: String) {
        view.filters = emptyArray()
        view.text = text
    }
}