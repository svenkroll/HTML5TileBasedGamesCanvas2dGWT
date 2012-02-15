package com.gibberfish.tile.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

public class TileFieldObject extends TileDrawable {
	
	private int iRow;
	private int iColmn;
	
	private TileField tField;
	
	private float fWidth;
	private float fHeight;

	private Image img;

	public TileFieldObject(float fieldWidth, float fieldHeight, int row, int colmn, Image Img, TileField tf) {
		iRow = row;
		iColmn = colmn;
		fWidth = fieldWidth;
		fHeight = fieldHeight;
		fPosX = fWidth * iColmn;
		fPosY = fHeight * iRow;
		img = Img;
		tField = tf;
	}
	
	@Override
	public void draw(Context2d ctx) {
	    //Draw grid lines
		ctx.setFillStyle("rgb(150,29,28)");
	    ctx.fillRect (fPosX, fPosY, fWidth, fHeight);
	    //Draw background image
	    ctx.drawImage(ImageElement.as(img.getElement()), fPosX + 1, fPosY + 1, fWidth - 1, fHeight - 1);
	    bDraw=false;
    }

	public int getiRow() {
		return iRow;
	}
	
	public int getiColmn() {
		return iColmn;
	}

	public TileFieldObject getTileFieldBottom() {
		return tField.getTileFieldObject(iRow + 1, iColmn);
	}
	public TileFieldObject getTileFieldTop() {
		return tField.getTileFieldObject(iRow - 1, iColmn);
	}
	public TileFieldObject getTileFieldLeft() {
		return tField.getTileFieldObject(iRow, iColmn - 1);
	}
	public TileFieldObject getTileFieldRight() {
		return tField.getTileFieldObject(iRow, iColmn + 1);
	}

	public boolean isAt(int x, int y) {
		if (x >= this.fPosX && x <= (this.fPosX + fWidth) && y >= this.fPosY && y <= (this.fPosY + fHeight)){
			return true;
		}
		return false;
	}
	
	
}
