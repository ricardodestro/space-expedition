package org.destro.space.vo;

/**
 * Comandos do robo
 * 
 * @author destro
 */
public class CommandVO {

	String command;

	int steps;

	public CommandVO(String command, int steps) {
		this.command = command;
		this.steps = steps;
	}

	public String getCommand() {
		return command;
	}

	public int getSteps() {
		return steps;
	}

}
