package it.polimi.ingsw.pc22.gamebox;

import java.util.List;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.player.Player;

public abstract class DevelopmentCard 
{
	private String name;
	private CardTypeEnum type;
	private int roundNumber;
	private List<Effect> immediateEffect;
	private List<Effect> permanentEffect;
	
	public abstract void useImmediateEffect(Player player);
	public abstract void usePermanentEffect(Player player);
}
