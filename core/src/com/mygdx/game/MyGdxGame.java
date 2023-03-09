package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
//import com.badlogic.gdx.graphics.g2d.freetype;


public class MyGdxGame extends ApplicationAdapter {
    public static final int X = 1270;
    public static final int Y = 720;
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
    private Camera camera;
    boolean game = true;
    BitmapFont text;



    @Override
    public void create() {
        batch = new SpriteBatch();
        text = new BitmapFont();
        text.setColor(Color.CORAL);
        touch = new Vector3();
        img = new Texture("mushroompolanka.jpg");
        imgBasket = new Texture("basket.png");
        for (int i = 0; i < imgMushroom.length; i++) {
            imgMushroom[i] = new Texture("mushroom" + i + ".png");
            Mushroom[i][0] = MathUtils.random(480f, 640f);
            Mushroom[i][1] = MathUtils.random(480f, 640f);
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
                Mushroom[i][0] = MathUtils.random(480f, 640f); // игровое поле
                Mushroom[i][1] = MathUtils.random(480f, 640f);
            }
            timeStart = TimeUtils.millis();
            game = false;
        }

        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
        }
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        batch.draw(img, 0, 0, X, Y);
        batch.draw(imgBasket, 0, 0, 300f, 300f);
        for (int i = 0; i < imgMushroom.length; i++) {
            batch.draw(imgMushroom[i], Mushroom[i][0], Mushroom[i][1], 50f, 50f);// размер
        }
        // if(Gdx.input.justTouched()) {

        // }
        timeCurrent = TimeUtils.millis() - timeStart;
        text.draw(batch,time_string(timeCurrent), 1200f, 700f); // время
        batch.end();
        if (Gdx.input.justTouched()) Gdx.app.exit();
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

//    // void createFont(){
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("mr_countryhouse.ttf"));
//        //FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("comic.ttf"));
//        FreeTypeTGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameter.characters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
//        parameter.size = 50;
//        parameter.color = Color.CHARTREUSE;
//        parameter.borderWidth = 2;
//        parameter.borderColor = Color.BLACK;
//        text = generator.generateFont(parameter);
//        parameter.size = 70;
//        generator.dispose();
//    }
}