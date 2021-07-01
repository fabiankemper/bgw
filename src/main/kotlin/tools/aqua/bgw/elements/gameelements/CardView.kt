@file:Suppress("unused")

package tools.aqua.bgw.elements.gameelements

import tools.aqua.bgw.elements.gameelements.CardView.Companion.DEFAULT_CARD_HEIGHT
import tools.aqua.bgw.elements.gameelements.CardView.Companion.DEFAULT_CARD_WIDTH
import tools.aqua.bgw.visual.Visual

/**
 * A [CardView] may be used to visualize a card.
 * You can inherit from this class if you want to add additional functionality or fields.
 * Inheriting does NOT change how a cardView is visualized by the BGW framework.
 *
 * Visualization:
 * The [Visual] at the [currentSide] value is used to visualize the card. By default the back side is shown.
 *
 * @param height height for this [CardView]. Default: [DEFAULT_CARD_HEIGHT].
 * @param width width for this [CardView]. Default: [DEFAULT_CARD_WIDTH].
 * @param posX horizontal coordinate for this [CardView]. Default: 0.
 * @param posY vertical coordinate for this [CardView]. Default: 0.
 * @param front visual to represent the front side of the card.
 * @param back visual to represent the back side of the card. Default: same [Visual] as front.
 */
open class CardView(
	height: Number = DEFAULT_CARD_HEIGHT,
	width: Number = DEFAULT_CARD_WIDTH,
	posX: Number = 0,
	posY: Number = 0,
	front: Visual,
	back: Visual = front
) : GameElementView(
	height = height,
	width = width,
	posX = posX,
	posY = posY,
	visual = back
) {
	/**
	 * The current [CardSide] that is displayed.
	 *
	 * @see showFront
	 * @see showBack
	 */
	var currentSide: CardSide = CardSide.BACK
		set(value) {
			if (field != value) {
				field = value
				
				visual = if (value == CardSide.FRONT)
					frontVisual
				else
					backVisual
			}
		}
	
	/**
	 * Front [Visual] for this [CardView].
	 */
	var frontVisual: Visual = front
		set(value) {
			field = value
			
			if (currentSide == CardSide.FRONT)
				visual = value
		}
	
	/**
	 * Back [Visual] for this [CardView].
	 */
	var backVisual: Visual = back
		set(value) {
			field = value
			
			if (currentSide == CardSide.BACK)
				visual = value
		}
	
	/**
	 * Sets the currentSide to be displayed to front.
	 */
	fun showFront() {
		currentSide = CardSide.FRONT
	}
	
	/**
	 * Sets the currentSide to be displayed to back.
	 */
	fun showBack() {
		currentSide = CardSide.BACK
	}
	
	/**
	 * Sets the currentSide to the parameter value.
	 */
	fun showCardSide(side: CardSide) {
		currentSide = side
	}
	
	/**
	 * Defines some static constants that can be used as suggested properties of a card.
	 */
	companion object {
		/**
		 * Suggested card height.
		 */
		const val DEFAULT_CARD_HEIGHT: Int = 200
		
		/**
		 * Suggested card width.
		 */
		const val DEFAULT_CARD_WIDTH: Int = 130
	}
	
	/**
	 * Enum for the card sides FRONT and BACK with their visual indices.
	 */
	enum class CardSide {
		/**
		 * The FRONT side.
		 */
		FRONT,
		
		/**
		 * The BACK side.
		 */
		BACK
	}
}