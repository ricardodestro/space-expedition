package org.destro.space.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * VO responsável pelos dados do Robo
 * 
 * @author destro
 */
public class RobotVO {

	private String name;

	private int landX;

	private int landY;

	private String direction;

	private List<CommandVO> commandList = new ArrayList<CommandVO>();

	public RobotVO(String name, int landX, int landY, String direction) {
		super();
		this.name = name;
		this.landX = landX;
		this.landY = landY;
		this.direction = direction;
	}

	public void addCommand(CommandVO commandVO) {
		commandList.add(commandVO);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLandX() {
		return landX;
	}

	public void setLandX(int landX) {
		this.landX = landX;
	}

	public int getLandY() {
		return landY;
	}

	public void setLandY(int landY) {
		this.landY = landY;
	}

	public List<CommandVO> getCommandList() {
		return commandList;
	}

	public void setCommandList(List<CommandVO> commandList) {
		this.commandList = commandList;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
