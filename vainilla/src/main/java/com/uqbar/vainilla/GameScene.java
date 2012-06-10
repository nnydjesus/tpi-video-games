package com.uqbar.vainilla;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.uqbar.vainilla.events.EventQueue;
import com.uqbar.vainilla.events.GameEvent;

public class GameScene {
	
	private Game game;
	private Map<Integer, List<GameComponent<?, ?>>> components;
	private EventQueue eventQueue;
	private double lastUpdateTime;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public GameScene() {
		this.setComponents(new HashMap<Integer, List<GameComponent<?, ?>>>());
		this.setEventQueue(new EventQueue());
	}

	public GameScene(GameComponent<? extends GameScene, ?>... components) {
		this(Arrays.asList(components));
	}

	public GameScene(Collection<? extends GameComponent<? extends GameScene, ?>> components) {
		this();

		for (GameComponent<? extends GameScene, ?> component : components) {
			this.addComponent(component);
		}
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public int getComponentCount(int layer) {
		return this.getComponents().get(layer).size();
	}
	
	public int getComponentCount() {
		int total = 0;
		
		for (Integer layer : this.components.keySet()) {
			total += this.getComponentCount(layer);
		}
		
		return total;
		
	}
	
	protected List<GameComponent<?, ?>> getComponentInLayer(int layer) {
		if (!this.components.containsKey(layer)) {
			this.components.put(layer, new ArrayList<GameComponent<?, ?>>());
		}
		return this.components.get(layer);
	}

	protected int getZFromComponentAt(List<GameComponent<?, ?>> componentsInLayer, int index) {
		return componentsInLayer.get(index).getZ();
	}

	protected int indexToInsert(GameComponent<?, ?> component) {
		int lowerIndex = 0;
		int layer = component.getLayer();
		List<GameComponent<?, ?>> componentInLayer = getComponentInLayer(layer);
		int higherIndex = componentInLayer.size() - 1;
		int searchedZ = component.getZ();

		if (this.getComponents().isEmpty() || componentInLayer.isEmpty()|| searchedZ < this.getZFromComponentAt(componentInLayer, lowerIndex)) {
			return 0;
		}

		if (searchedZ >= this.getZFromComponentAt(componentInLayer, higherIndex)) {
			return componentInLayer.size();
		}

		while (lowerIndex <= higherIndex) {
			int middleIndex = lowerIndex + higherIndex >>> 1;
			int middleZ = this.getZFromComponentAt(componentInLayer, middleIndex);

			if (middleZ <= searchedZ) {
				lowerIndex = middleIndex + 1;
			} else if (middleZ > searchedZ) {
				higherIndex = middleIndex - 1;
			}
		}

		return lowerIndex;
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	public void onSetAsCurrent() {
		for (List<GameComponent<?, ?>> components : this.components.values()) {
			for (GameComponent<?, ?> component : components) {
				component.onSceneActivated();
			}
		}
	}

	public void pushEvent(GameEvent event) {
		this.getEventQueue().pushEvent(event);
	}

	public void takeStep(Graphics2D graphics) {
		long now = System.nanoTime();
		double delta = this.getLastUpdateTime() > 0 ? (now - this.getLastUpdateTime()) / 1000000000L : 0;
		this.setLastUpdateTime(now);

		DeltaState state = this.getEventQueue().takeState(delta);

		this.takeStep(graphics, state);
	}

	protected void takeStep(Graphics2D graphics, DeltaState state) {
		List<GameComponent<?, ?>> sortedComponent = null;
		for (List<GameComponent<?, ?>> components : this.components.values()) {
			sortedComponent = new ArrayList<GameComponent<?,?>>(components);
			Collections.sort(sortedComponent);
			for (GameComponent<?, ?> component : sortedComponent) {
				if (component.isDestroyPending()) {
					this.removeComponent(component);
				} else {
					this.updateComponent(component, state);
					this.renderComponent(component, graphics);
					this.renderDebug(component, graphics);
				}
			}
		}
	}

	protected void renderDebug(GameComponent<?, ?> component, Graphics2D graphics) {
		if(this.getGame().isDebuggin()){
			graphics.setColor(Color.BLUE);
			graphics.drawRect((int)component.getX(), (int)component.getY(), (int)component.getAppearance().getWidth(), (int)component.getAppearance().getHeight());
		}
	}

	protected void updateComponent(GameComponent<?, ?> component,	DeltaState state) {
		component.update(state);
	}

	protected void renderComponent(GameComponent<?, ?> component, Graphics2D graphics) {
		component.render(graphics);
	}

	// ****************************************************************
	// ** ACCESS OPERATIONS
	// ****************************************************************

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addComponent(GameComponent component) {
		component.setScene(this);
		int layer = component.getLayer();
		List<GameComponent<?, ?>> componentInLayer = this.getComponentInLayer(layer);
		if(!componentInLayer.contains(component)){
			componentInLayer.add(this.indexToInsert(component), component);
		}
	}

	public void addComponents(GameComponent<?, ?>... components) {
		for (GameComponent<?, ?> component : components) {
			this.addComponent(component);
		}
	}

	public void addComponents(Collection<? extends GameComponent<?, ?>> components) {
		for (GameComponent<?, ?> component : components) {
			this.addComponent(component);
		}
	}

	public void removeComponent(GameComponent<?, ?> component) {
		this.getComponentInLayer(component.getLayer()).remove(component);
//		component.setScene(null);
	}

	public void removeComponents(GameComponent<?, ?>... components) {
		for (GameComponent<?, ?> component : components) {
			this.removeComponent(component);
		}
	}

	public void removeComponents(Collection<? extends GameComponent<?, ?>> components) {
		for (GameComponent<?, ?> component : components) {
			this.removeComponent(component);
		}
	}
	

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	protected Map<Integer, List<GameComponent<?, ?>>> getComponents() {
		return this.components;
	}

	protected void setComponents(
			Map<Integer, List<GameComponent<?, ?>>> components) {
		this.components = components;
	}

	protected EventQueue getEventQueue() {
		return this.eventQueue;
	}

	protected void setEventQueue(EventQueue eventQueue) {
		this.eventQueue = eventQueue;
	}

	protected double getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(double lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}