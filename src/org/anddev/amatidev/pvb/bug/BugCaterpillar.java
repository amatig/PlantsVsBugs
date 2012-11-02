package org.anddev.amatidev.pvb.bug;

import org.anddev.amatidev.pvb.singleton.GameData;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.MoveYModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.sprite.Sprite;

public class BugCaterpillar extends Bug {
	
	public BugCaterpillar(final float y) {
		super(y, GameData.getInstance().mBugCaterpillar);
		//getFirstChild().getFirstChild().attachChild(new Sprite(-10, 0, GameData.getInstance().mBugCaterpillar2));
		//getFirstChild().getFirstChild().attachChild(new Sprite(-20, 0, GameData.getInstance().mBugCaterpillar2));
		//getFirstChild().getFirstChild().attachChild(new Sprite(0, 0, GameData.getInstance().mBugCaterpillar));
		
		//move(-3, 2, getFirstChild().getFirstChild().getChild(0));
		//move(2, -3, getFirstChild().getFirstChild().getChild(1));
		
		this.mLife = 16;
		this.mSpeed = 13f;
		this.mPoint = 8;
		this.mAttack = 2.5f;
	}
	
	private void move(final int pStartY, final int pEndY, final IEntity pElem) {
		pElem.registerEntityModifier(
				new LoopEntityModifier(
						null, 
						-1, 
						null,
						new SequenceEntityModifier(
								new MoveYModifier(2f, pStartY, pEndY),
								new MoveYModifier(2f, pEndY, pStartY)
						)
				));
	}
	
}
