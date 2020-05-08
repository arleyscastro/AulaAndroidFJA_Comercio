package edu.br.fja.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameBird extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;
	float variacao=0;
	float velocidadeQueda=0;
	float posicaoInicialVertical=0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		passaros = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");
		fundo = new Texture("fundo.png");
		posicaoInicialVertical = Gdx.graphics.getHeight()/2;
	}

	@Override
	public void render () {
		//Acontece em LOOP eterno até que um código do programador o finalize ou o APP seja encerrado

		//Gdx.app.log("Variação:", "Varia em " + Gdx.graphics.getDeltaTime());

		variacao += Gdx.graphics.getDeltaTime() * 5;
		velocidadeQueda++;

		if (Gdx.input.justTouched())
			velocidadeQueda = -20;

		if(variacao>2) variacao = 0;

		if(posicaoInicialVertical>0 || velocidadeQueda < 0)
			posicaoInicialVertical = posicaoInicialVertical - velocidadeQueda;

		batch.begin();

		batch.draw(fundo,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(passaros[(int)variacao], 15, posicaoInicialVertical);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		passaros[0].dispose();
		passaros[1].dispose();
		passaros[2].dispose();
		fundo.dispose();
	}
}
