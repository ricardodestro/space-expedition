package org.destro.space.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * VO responsável pelos dados do Robo
 * 
 * @author destro
 */
public class RobotVO {

	private String expeditionName;

	private String name;

	private int landX;

	private int landY;

	private String direction;

	private List<CommandVO> commandList = new ArrayList<CommandVO>();

	public RobotVO(String expeditionName, String name, int landX, int landY,
			String direction) {
		super();
		this.expeditionName = expeditionName;
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

	public String getExpeditionName() {
		return expeditionName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((expeditionName == null) ? 0 : expeditionName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RobotVO other = (RobotVO) obj;
		if (expeditionName == null) {
			if (other.expeditionName != null)
				return false;
		} else if (!expeditionName.equals(other.expeditionName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
