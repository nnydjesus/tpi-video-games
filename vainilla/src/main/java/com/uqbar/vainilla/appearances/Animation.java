package com.uqbar.vainilla.appearances;


import java.awt.Graphics2D;
import com.uqbar.vainilla.GameComponent;

public class Animation implements Appearance {
	private double meantime;
	private Sprite[] sprites;
	private int currentIndex;
	private double remainingTime;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************
	
	public Animation(double meantime, int nada, Sprite... sprites) {
		this(meantime, sprites);
	}

	public Animation(double meantime, Sprite[] sprites) {
		this.setMeantime(meantime);
		this.setSprites(sprites);
		this.setCurrentIndex(0);
		this.setRemainingTime(meantime);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public double getWidth() {
		return this.getCurrentSprite().getWidth();
	}

	@Override
	public double getHeight() {
		return this.getCurrentSprite().getHeight();
	}

	public double getDuration() {
		return this.getMeantime() * this.getSprites().length;
	}

	public Sprite getCurrentSprite() {
		return this.getSprites()[this.getCurrentIndex()];
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void update(double delta) {
		this.setRemainingTime(this.getRemainingTime() - delta);

		if(this.getRemainingTime() <= 0) {
			this.advance();
		}
	}

	@Override
	public void render(GameComponent<?, ?> component, Graphics2D graphics) {
		this.getCurrentSprite().render(component, graphics);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Animation copy() {
		return new Animation(this.getMeantime(), this.getSprites());
	}

	public void advance() {

		if(this.finish()) {
			this.setCurrentIndex(0);
		}else{
			this.setCurrentIndex(this.getCurrentIndex() + 1);
		}

		this.setRemainingTime(this.getMeantime() - this.getRemainingTime());
	}
	
	public void back() {
		this.setCurrentIndex(this.getCurrentIndex() - 1);

		if(this.getCurrentIndex() < 0) {
			this.setCurrentIndex(this.getSprites().length -1);
		}

		this.setRemainingTime(this.getMeantime() - this.getRemainingTime());
	}
	
	public void reset(){
		this.currentIndex = 0;
	}


	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected double getMeantime() {
		return this.meantime;
	}

	protected void setMeantime(double meantime) {
		this.meantime = meantime;
	}

	protected Sprite[] getSprites() {
		return this.sprites;
	}

	protected void setSprites(Sprite... sprites) {
		this.sprites = sprites;
	}

	protected int getCurrentIndex() {
		return this.currentIndex;
	}

	public void setCurrentIndex(int index) {
		this.currentIndex = index;
	}

	protected double getRemainingTime() {
		return this.remainingTime;
	}

	protected void setRemainingTime(double remainingTime) {
		this.remainingTime = remainingTime;
	}
	
	public boolean finish(){
		return this.getCurrentIndex()+1 >= this.getSprites().length ;
	}
}
