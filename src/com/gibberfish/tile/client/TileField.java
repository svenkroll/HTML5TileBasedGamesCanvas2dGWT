package com.gibberfish.tile.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;


public class TileField {
	
	private int rows;
	private int colums;
	final Image img;
	
	private TileFieldObject[][] field;
	
	public TileField(int fieldrows, int fieldcolums,float fieldWidth ,float fieldHeight, ArrayList<TileDrawable> DrawStack) {
		rows = fieldrows;
		colums = fieldcolums;
		
		field = new TileFieldObject[rows][colums];
		
		Image.prefetch("./images/map/grassland.png");
		img = new Image("./images/map/grassland.png");
		
		initializeField(fieldWidth, fieldHeight, DrawStack);
	}
	
	public TileFieldObject getTileFieldObject(int iRow, int jColumn){
		return field[iRow][jColumn];
	}
	
	public TileFieldObject findTileFieldObject(int x, int y){
		for( int i = 0; i < field.length; i++){
			for (int j = 0; j < field[i].length; j++){
				if (field[i][j].isAt(x,y)){
					return field[i][j];
				}
			}
		}
		return null;
	}
	
	private void initializeField(float fieldWidth ,float fieldHeight, ArrayList<TileDrawable> DrawStack){
		for( int i = 0; i < field.length; i++){
			for (int j = 0; j < field[i].length; j++){
				field[i][j] = new TileFieldObject(fieldWidth, fieldHeight, i, j, img, this);
				DrawStack.add(field[i][j]);
				field[i][j].bDraw = true;
			}
		}
	}

}
