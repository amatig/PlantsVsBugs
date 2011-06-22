package org.anddev.andengine.pvb.singleton;

import java.util.LinkedList;

import org.anddev.andengine.extra.ExtraScoring;
import org.anddev.andengine.extra.Resource;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.pvb.card.Card;

import android.graphics.Color;

public class GameData {
	
    private static GameData mInstance = null;
    
    // generici
    public LinkedList<Card> mCards;
    public ExtraScoring mScoring;
    
    public TextureRegion mSplash;
    
    public TextureRegion mBackground;
    public TextureRegion mTable;
    public TextureRegion mSeed;
    public TextureRegion mShot;
    public TextureRegion mShotShadow;
    public TextureRegion mPlantShadow;
    public TextureRegion mCardSelected;
    
    // cards
    public TextureRegion mCard;
    public TextureRegion mCardTomato;
	public TextureRegion mCardFlower2;
	
	// plants
	public TextureRegion mPlantTomato;
	public TextureRegion mPlantFlower2;
	
	// bugs
	public TextureRegion mBugBeetle;
	public TextureRegion mBugLadybug;

	// fonts
	public Font mFont1;
	public Font mFont2;
	public Font mFont3;
	public Font mFontMainMenu;
	public Font mFontGameMenu;
	
	private GameData() {
		
	}
	
	public static synchronized GameData getInstance() {
		if (mInstance == null) 
			mInstance = new GameData();
		return mInstance;
	}
	
	public void initData() {
		this.mFont1 = Resource.getFont("akaDylan Plain", 21, 2, Color.WHITE, Color.BLACK);
		this.mFont2 = Resource.getFont("akaDylan Plain", 14, 1, Color.WHITE, Color.BLACK);
		this.mFont3 = Resource.getFont("akaDylan Plain", 30, 2, Color.WHITE, Color.BLACK);
		this.mFontMainMenu = Resource.getFont(512, 512, "akaDylan Plain", 43, 3, Color.WHITE, Color.BLACK);
		this.mFontGameMenu = Resource.getFont(512, 512, "akaDylan Plain", 48, 3, Color.WHITE, Color.BLACK);
		this.mSplash = Resource.getTexture(1024, 512, "splash");
		
		this.mBackground = Resource.getTexture(1024, 512, "back");
		this.mTable  = Resource.getTexture(1024, 128, "table");
		this.mSeed = Resource.getTexture(64, 64, "seed");
		this.mShot = Resource.getTexture(64, 64, "shot");
		this.mShotShadow = Resource.getTexture(64, 64, "shadow2");
		
		this.mCardSelected = Resource.getTexture(64, 128, "select");
		
		this.mCard = Resource.getTexture(64, 128, "card");
		
		this.mCardTomato = Resource.getTexture(64, 128, "card_tomato");
		this.mCardFlower2 = Resource.getTexture(64, 128, "card_flower2");
		
		this.mPlantShadow = Resource.getTexture(64, 16, "shadow");
		
		this.mPlantTomato = Resource.getTexture(64, 128, "tomato");
		this.mPlantFlower2 = Resource.getTexture(64, 128, "default2");
		
		this.mBugBeetle = Resource.getTexture(64, 128, "beetle");
		this.mBugLadybug = Resource.getTexture(64, 128, "ladybug");
		
		this.mScoring = new ExtraScoring(625, 47, GameData.getInstance().mFont3);
		
		this.mCards = new LinkedList<Card>();
	}
	
}
