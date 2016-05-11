package org.destro.space.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * VO responsável pelos dados da expedição e robos associados
 * 
 * @author destro
 */
public class ExpeditionVO {

	private String name;

	private int borderX;

	private int borderY;

	private List<RobotVO> robotList = new ArrayList<RobotVO>();

	public ExpeditionVO(String name, int borderX, int borderY) {
		super();
		this.name = name;
		this.borderX = borderX;
		this.borderY = borderY;
	}

	public String getName() {
		return name;
	}

	public int getBorderX() {
		return borderX;
	}

	public int getBorderY() {
		return borderY;
	}

	public List<RobotVO> getRobotList() {
		return robotList;
	}
}
