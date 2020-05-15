package edu.br.fja.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class GameBird extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;
	private Texture canoDoTopo;
	private Texture canoDeBaixo;
	private Texture gameOver;
	private float variacao=0;
    private float velocidadeQueda=0;
    private float posicaoInicialVertical=0;
    private float posicaoDoMovimentoDoCanoHorizontal;
    private float alturaDispositivo;
    private float larguraDispositivo;
    private int estadoDoJogo = 0; // 0->Jogo não iniciado | 1->Jogo iniciado | 2->Game Over
    private float espacoEntreOsCanos;
    private float alturaEntreOsCanos;
    private float detlaTime;
    private Random numeroRandomico;
    private int pontuacao=0;
    private BitmapFont fonte;
    private BitmapFont mensagem;
    private boolean marcouPonto=false;

    private final float TELA_WIDTH = 1960;
    private final float TELA_HEIGHT = 2050;

    //Provisório
	private ShapeRenderer shape;
	private Circle passaroCirculo;
	private Rectangle retanguloCanoTopo;
	private Rectangle retanguloCanoBaixo;
	//Provisório

    @Override
	public void create () {
		batch = new SpriteBatch();
		passaros = new Texture[3];
		numeroRandomico = new Random();
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");
		fundo = new Texture("fundo.png");
		canoDeBaixo = new Texture("cano_baixo.png");
		canoDoTopo = new Texture("cano_topo.png");
		gameOver = new Texture("game_over.png");

		fonte = new BitmapFont();
		fonte.setColor(Color.WHITE);
		fonte.getData().setScale(6);

		mensagem = new BitmapFont();
		mensagem.setColor(Color.WHITE);
		mensagem.getData().setScale(3);

		alturaDispositivo = TELA_HEIGHT;
		larguraDispositivo = TELA_WIDTH;
		posicaoInicialVertical = alturaDispositivo/2;
		posicaoDoMovimentoDoCanoHorizontal = larguraDispositivo /2;
		espacoEntreOsCanos = 300;

		shape = new ShapeRenderer();
		retanguloCanoBaixo = new Rectangle();
		retanguloCanoTopo = new Rectangle();
		passaroCirculo = new Circle();
	}

	@Override
	public void render () {
		//Acontece em LOOP eterno até que um código do programador o finalize ou o APP seja encerrado

		//Gdx.app.log("Variação:", "Varia em " + Gdx.graphics.getDeltaTime());
		//Gdx.app.log("Altura:", "Altura " + alturaDispositivo);

		detlaTime = Gdx.graphics.getDeltaTime();
		variacao += detlaTime * 5;
        if(variacao>2) variacao = 0;

		if (estadoDoJogo == 0 ){ // Jogo não iniciado
            if(Gdx.input.justTouched()){
                estadoDoJogo = 1;
            }
        }else{
			velocidadeQueda++;

            if (Gdx.input.justTouched())
                velocidadeQueda = -20;

            if(posicaoInicialVertical>0 || velocidadeQueda < 0)
                posicaoInicialVertical = posicaoInicialVertical - velocidadeQueda;

			posicaoDoMovimentoDoCanoHorizontal -= detlaTime * 200;

			if(posicaoDoMovimentoDoCanoHorizontal < -canoDoTopo.getWidth()){
				posicaoDoMovimentoDoCanoHorizontal = larguraDispositivo /2;
				alturaEntreOsCanos = numeroRandomico.nextInt(400) - 200;
				marcouPonto=false;
			}

			if(posicaoDoMovimentoDoCanoHorizontal < 150){
				if(!marcouPonto){
					pontuacao++;
					marcouPonto=true;
				}

			}

        }



		batch.begin();

		batch.draw(fundo,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(canoDoTopo, posicaoDoMovimentoDoCanoHorizontal, (alturaDispositivo/2) + (espacoEntreOsCanos/2) + alturaEntreOsCanos);
		batch.draw(canoDeBaixo,posicaoDoMovimentoDoCanoHorizontal,(alturaDispositivo/2) - canoDeBaixo.getHeight() - (espacoEntreOsCanos/2) + alturaEntreOsCanos);
		batch.draw(passaros[(int)variacao], 150, posicaoInicialVertical);
		fonte.draw(batch,String.valueOf(pontuacao), (larguraDispositivo/2)/2, (alturaDispositivo/2)+855);

		if(estadoDoJogo == 2){
			batch.draw(gameOver,(larguraDispositivo/2)/2 - gameOver.getWidth()/2,150);
			mensagem.draw(batch,"Toque na tela para reiniciar",(larguraDispositivo/2)/2 - (gameOver.getWidth()/2) -200, 150);
			estadoDoJogo = 1;
		}

		batch.end();


		passaroCirculo.set(150 + (passaros[0].getWidth() / 2), posicaoInicialVertical + (passaros[0].getHeight()/2), (passaros[0].getWidth() / 2) );
        retanguloCanoBaixo = new Rectangle(
        		posicaoDoMovimentoDoCanoHorizontal,
				(alturaDispositivo/2) - canoDeBaixo.getHeight() - (espacoEntreOsCanos/2) + alturaEntreOsCanos,
				canoDeBaixo.getWidth(),
				canoDeBaixo.getHeight()
		);
        retanguloCanoTopo = new Rectangle(
				posicaoDoMovimentoDoCanoHorizontal,
				(alturaDispositivo/2) + (espacoEntreOsCanos/2) + alturaEntreOsCanos,
				canoDoTopo.getWidth(),
				canoDoTopo.getHeight()
		);

/*
		shape.begin(ShapeRenderer.ShapeType.Filled);

		shape.circle(passaroCirculo.x, passaroCirculo.y, passaroCirculo.radius);
		shape.rect(retanguloCanoBaixo.x,retanguloCanoBaixo.y,retanguloCanoBaixo.width, retanguloCanoBaixo.height);
		shape.rect(retanguloCanoTopo.x, retanguloCanoTopo.y, retanguloCanoTopo.width, retanguloCanoTopo.height);

		shape.end();
*/
		if(Intersector.overlaps(passaroCirculo, retanguloCanoBaixo)
				|| Intersector.overlaps(passaroCirculo, retanguloCanoTopo)
		        || posicaoInicialVertical <=0 || posicaoInicialVertical>=alturaDispositivo){
			estadoDoJogo = 2;
			pontuacao=0;
		}
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
