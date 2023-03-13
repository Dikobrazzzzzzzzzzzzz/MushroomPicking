package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;


public class MyGdxGame extends ApplicationAdapter {
    public static final int X = 1720;
    public static final int Y = 700;
    SpriteBatch batch;
    Texture img;
    Vector3 touch;

    int countmashroom = 3;
    Texture[] imgMushroom = new Texture[countmashroom];
    float[][] Mushroom = new float[countmashroom][2]; // расположение грибов
    Mushroom[] mush = new Mushroom[10];
    Texture imgBasket;
    int pick = 0;
    long timeStart, timeCurrent;
    private OrthographicCamera camera;
    boolean game = true;
    BitmapFont text;
    float xm;
    float ym;
    long timeMushroom;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, X, Y);
        text = new BitmapFont();
        text.setColor(Color.CORAL);
        touch = new Vector3();
        img = new Texture("mushroompolanka.jpg");
        imgBasket = new Texture("basket.png");
        for (int i = 0; i < imgMushroom.length; i++) {
            imgMushroom[i] = new Texture("mushroom" + i + ".png");
            Mushroom[i][0] = MathUtils.random(100f, 640f);
            Mushroom[i][1] = MathUtils.random(280f, 640f);
            timeStart = TimeUtils.millis();
        }
    }

    public static int getX() {
        return X; //экран обращение
    }

    public static int getY() {
        return Y;
    }

    @Override
    public void render() {
        if (game) {
            for (int i = 0; i < imgMushroom.length; i++) {
                imgMushroom[i] = new Texture("mushroom" + i + ".png");
                Mushroom[i][0] = MathUtils.random(500f, 900f); // игровое поле
                Mushroom[i][1] = MathUtils.random(200f, 440f);
            }
            timeStart = TimeUtils.millis();
            timeMushroom = TimeUtils.millis();
            game = false;
        }

        if ((TimeUtils.millis()- timeMushroom )/ 1000 > 5 ) {
            for (int i = 0; i < imgMushroom.length; i++) {
                Mushroom[i][0] = MathUtils.random(500f, 900f);
                Mushroom[i][1] = MathUtils.random(200f, 440f);
            }
            timeMushroom = TimeUtils.millis();
        }

        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            float xt = touch.x;
            float yt = touch.y;
            for (int i = 0; i < imgMushroom.length; i++) {
                xm = Mushroom[i][0];
                ym = Mushroom[i][1];
                if (xm < xt && xt < xm + 50 && ym < yt && yt < ym + 50) {
                    pick++;
                    Mushroom[i][0] = MathUtils.random(500f, 900f);
                    Mushroom[i][1] = MathUtils.random(200f, 440f);
                    break;
                }
            }
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(img, 0, 0, X, Y);
        batch.draw(imgBasket, 0, 0, 300f, 300f);
        for (int i = 0; i < imgMushroom.length; i++) {
            batch.draw(imgMushroom[i], Mushroom[i][0], Mushroom[i][1], 50f, 50f);// размер
        }
        timeCurrent = TimeUtils.millis() - timeStart;
        text.draw(batch,time_string(timeCurrent), 1200f, 700f);// время
        text.draw(batch,""+pick, 900f, 90f);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }

    String time_string(long time) {
        String minutes = "" + time / 1000 / 60 / 10 + time / 1000 / 60 % 10;
        String sec = "" + time / 1000 % 60 / 10 + time / 1000 % 60 % 10;
        return minutes+":"+sec;
    }
}