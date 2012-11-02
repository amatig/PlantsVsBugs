package org.anddev.amatidev.pvb.bug;

import org.anddev.amatidev.pvb.singleton.GameData;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

public class BugBeetle extends Bug {
	
	public BugBeetle(final float y) {
		super(y, GameData.getInstance().mBugBeetle);
		
		//AnimatedSprite leg = new AnimatedSprite(23, 57, GameData.getInstance().mBugLeg);
		//getFirstChild().getFirstChild().attachChild(leg);
		//leg.animate(400);
		
		this.mLife = 9;
		this.mSpeed = 12f;
		this.mPoint = 2;
		this.mAttack = 1.5f;
	}
	
}
