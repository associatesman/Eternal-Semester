package com.veyrongaming.eternalsemester.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.characters.Player;

public class TamedBeast extends Enemy {
    public static final int WIDTH = 76;
    public static final int HEIGHT = 67;

    public static String name = "TamedBeast"; 
    public static int maxHealth = 900; 
    public static int speed = 40;
    public static int damage = 120; 
    public static float attackCooldown = 0.8f;
    public static float deathDuration = 1.7f;

    public Texture death = new Texture("Enemies/Tribe Tamed Beast/Tribe Tamed Beast-death.png");
    public Texture hit = new Texture("Enemies/Tribe Tamed Beast/Tribe Tamed Beast-hit.png");
    public Texture run = new Texture("Enemies/Tribe Tamed Beast/Tribe Tamed Beast-move lr.png");

    public TamedBeast(EternalSemester game, World world, Player player) {
        super(game, world, player, name, maxHealth, speed, damage, attackCooldown, deathDuration);

        createAnimation();
    }

    private void createAnimation() {
        animations = new Animation[3];
        TextureRegion deathSheet[][] = TextureRegion.split(death, WIDTH, HEIGHT);
        TextureRegion hitSheet[][] = TextureRegion.split(hit, WIDTH, HEIGHT);
        TextureRegion runSheet[][] = TextureRegion.split(run, WIDTH, HEIGHT);
        TextureRegion deathAnimation[] = new TextureRegion[17];
        TextureRegion hitAnimation[] = new TextureRegion[2];
        TextureRegion runAnimation[] = new TextureRegion[6];

        for (int i = 0; i < 17; i++) {
            deathAnimation[i] = deathSheet[0][i];
        }
        
        for (int i = 0; i < 2; i++) {
            hitAnimation[i] = hitSheet[0][i];
        }

        for (int i = 0; i < 6; i++) {
            runAnimation[i] = runSheet[0][i];
        }

        animations[0] = new Animation<>(0.1f, deathAnimation);
        animations[1] = new Animation<>(0.1f, hitAnimation);
        animations[2] = new Animation<>(0.1f, runAnimation);
    }


    @Override
    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion tr;

        switch (currentState) {
            case DEATH:
                tr = animations[0].getKeyFrame(stateTimer, false);
                break;
            case HIT:
                tr = animations[1].getKeyFrame(stateTimer, true);
                break;
            default:
                tr = animations[2].getKeyFrame(stateTimer, true);
                break;
        }

        if (!isFacingRight && !tr.isFlipX()) 
            tr.flip(true, false);
        else if (isFacingRight && tr.isFlipX())
            tr.flip(true, false);
        
        stateTimer = (currentState == previousState ? stateTimer + delta : 0);
        previousState = currentState;
        return tr;
    }

    @Override
    public void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(getPosition().x, getPosition().y);

        Body body = world.createBody(bodyDef);
		body.setFixedRotation(true);

        CircleShape shape = new CircleShape();
        shape.setRadius(3 * 30/2);

        FixtureDef fixture = new FixtureDef();
        fixture.density = 10f;
        fixture.shape = shape;
        body.createFixture(fixture).setUserData(this);

        /* PolygonShape shape = new PolygonShape();
        shape.setAsBox(3 * 18 / 2, 3 * 18 / 2); */
        shape.dispose();

        this.body = body;
    }

    @Override
    public void draw(float delta) {
        game.batch.draw(getFrame(delta), getPosition().x - 3*WIDTH/2, getPosition().y - 3*HEIGHT/2 + 9, 3*WIDTH, 3*HEIGHT);
        game.font.draw(game.batch, health + "", position.x, position.y);
    } 

}
