package com.gibberfish.tile.client;

import java.util.ArrayList;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Tilegame implements EntryPoint {

	Canvas canvas, canvas_menue;
	Context2d ctx, ctx_m;

	static final int canvasHeight = 480;
	static final int canvasWidth = 640;

	static final int fieldRows = 12;
	static final int fieldColums = 16;

	static final float tileWidth = canvasWidth / fieldColums;
	static final float tileHeight = canvasHeight / fieldRows;

	TileField tField;

	TileTank[] tanks;

	Timer loopTimer, moveTimer;

	ArrayList<TileDrawable> backgroundDrawStack = new ArrayList<TileDrawable>();
	ArrayList<TileDrawable> foregroundDrawStack = new ArrayList<TileDrawable>();

	TileTank movingTank;

	boolean bLoop;

	long StartTime, TimeNow;
	int fps;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {	

		canvas = Canvas.createIfSupported();

		if (canvas == null) {
			RootPanel
					.get()
					.add(new Label(
							"Sorry, your browser doesn't support the HTML5 Canvas element"));
			return;
		}
		canvas.setStyleName("mainCanvas");
		canvas.setWidth(canvasWidth + "px");
		canvas.setCoordinateSpaceWidth(canvasWidth);

		canvas.setHeight(canvasHeight + "px");
		canvas.setCoordinateSpaceHeight(canvasHeight);

		RootPanel.get().add(canvas);
		ctx = canvas.getContext2d();

		canvas.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				canvasClicked(event);
			}
		});

		canvas_menue = Canvas.createIfSupported();

		canvas_menue.setStyleName("menueCanvas");
		canvas_menue.setWidth(100 + "px");
		canvas_menue.setCoordinateSpaceWidth(100);

		canvas_menue.setHeight(canvasHeight + "px");
		canvas_menue.setCoordinateSpaceHeight(canvasHeight);

		RootPanel.get().add(canvas_menue);
		ctx_m = canvas_menue.getContext2d();

		initialize();
		StartTime = System.currentTimeMillis();

		loopTimer = new Timer() {
			@Override
			public void run() {
				loop();
			}
		};
		bLoop = false;
		loopTimer.scheduleRepeating(10);
	}

	
	public void initialize() {
		fps = 0;
		tField = new TileField(fieldRows, fieldColums, tileWidth, tileHeight, backgroundDrawStack);

		tanks = new TileTank[1];
		tanks[0] = new TileTank();
		tanks[0].setPosition(tField.getTileFieldObject(6, 6));
		foregroundDrawStack.add(tanks[0]);
	}

	public void draw() {
		for (TileDrawable obj : backgroundDrawStack) {
			if (obj.bDraw){
				obj.draw(ctx);
			}
		}
		for (TileDrawable obj : foregroundDrawStack) {
			if (obj.bDraw){
				obj.draw(ctx);
			}
		}

	}

	public void canvasClicked(ClickEvent event) {
		TileFieldObject tfoClicked = tField.findTileFieldObject(event.getX(),
				event.getY());
		tanks[0].moveTo(tfoClicked);
		movingTank = tanks[0];
	}

	public void loop() {
		if (bLoop)
			return;
		bLoop = true;
		// check if a tank is moving
		if (movingTank != null) {
			movingTank.move();

			movingTank.posTileField.bDraw = true;
			movingTank.bDraw = true;
			if (movingTank.lastTouchedTileField != null) {
				movingTank.lastTouchedTileField.bDraw = true;
			}
			if (movingTank.moveInProgress) {
				movingTank.nextTargetTileField.bDraw=true;
			} else {
				movingTank = null;
			}
		}
		// draw everything we have to draw :)
		draw();

		TimeNow = System.currentTimeMillis();
		if (TimeNow <= StartTime + 1000) {
			fps++;
		} else {
			ctx_m.setFillStyle("rgb(0,0,0)");
			ctx_m.fillRect(0, 0, 80, 40);
			ctx_m.setFillStyle("rgb(255,255,255)");
			ctx_m.setFont("12pt Arial");
			ctx_m.fillText("FPS: " + String.valueOf(fps), 1, 20);

			fps = 0;
			StartTime = System.currentTimeMillis();
		}

		bLoop = false;

	}
}
