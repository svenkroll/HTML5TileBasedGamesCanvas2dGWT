package com.gibberfish.tile.client;


import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Image;

public class TileTank extends TileDrawable{
	
	Image img;
	boolean moveInProgress;
	TileFieldObject posTileField;
	TileFieldObject targetTileField;
	TileFieldObject nextTargetTileField;
	TileFieldObject lastTouchedTileField;

	public TileTank(){
		bDraw=true;
		Image.prefetch("./images/tank.png");
		img = new Image("./images/tank.png");
		moveInProgress = false;
	}
	
	public void setPosition(TileFieldObject tfo){
		posTileField = tfo;
		fPosX = posTileField.fPosX;
		fPosY = posTileField.fPosY;
	}
	
	@Override
	public void draw(Context2d ctx) {
	    //Draw tank image
	    ctx.drawImage(ImageElement.as(img.getElement()), fPosX, fPosY,40,40);
	    bDraw = false;
    }

	public void moveTo(TileFieldObject tileFieldObject) {
		if (moveInProgress){
			lastTouchedTileField = nextTargetTileField;
		}
		targetTileField = tileFieldObject;
		calculateNextTargetTileFieldObject();
		moveInProgress = true;
	}
	
	private void calculateNextTargetTileFieldObject() {
		//More random path finding :)
		boolean bMoveX = false;
		boolean bMoveY = false;
		
		if (posTileField.getiRow() != targetTileField.getiRow()){
			bMoveX = true;
		}
		if (posTileField.getiColmn() != targetTileField.getiColmn()){
			bMoveY = true;
		}
		//if wee need to move in both axis make the next direction random
		if (bMoveX && bMoveY){
			if (Random.nextBoolean()){
				bMoveY = false;
			}else{
				bMoveX = false;
			}
		}
		
		// if we have to move horizontal
		if (bMoveX){
			if (posTileField.getiRow() > targetTileField.getiRow()){
				nextTargetTileField = posTileField.getTileFieldTop();
			}
			else{
				nextTargetTileField = posTileField.getTileFieldBottom();
			}
		// if we have to move vertical
		}else if (bMoveY){
			if (posTileField.getiColmn() < targetTileField.getiColmn()){
				nextTargetTileField = posTileField.getTileFieldRight();
			}
			else{
				nextTargetTileField = posTileField.getTileFieldLeft();
			}
		//Not Vertical and not Horizontal = target arrived!
		}else{
			moveInProgress = false;
			nextTargetTileField = null;
			lastTouchedTileField = null;
			targetTileField = null;
		}
		
	}

	public void move(){
		if (this.fPosX < nextTargetTileField.fPosX){
			this.fPosX++;
		}else if (this.fPosX > nextTargetTileField.fPosX){
			this.fPosX--;
		}else if (this.fPosY > nextTargetTileField.fPosY){
			this.fPosY--;
		}else if (this.fPosY < nextTargetTileField.fPosY){
			this.fPosY++;
		}else //nextTarget arrived, set position and calculate next target
		{
			posTileField = nextTargetTileField;
			nextTargetTileField = null;
			lastTouchedTileField = null;
			calculateNextTargetTileFieldObject();
		}
		
	}
}
