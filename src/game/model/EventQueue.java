package game.model;

import java.util.concurrent.ArrayBlockingQueue;

public class EventQueue extends ArrayBlockingQueue<Command> {

	private static final long serialVersionUID = 1L;
	
	public EventQueue()
	{
		super(5);
	}
}
