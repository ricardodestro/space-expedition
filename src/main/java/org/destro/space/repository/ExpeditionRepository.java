package org.destro.space.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.destro.space.ValidationException;
import org.destro.space.vo.ExpeditionVO;
import org.destro.space.vo.RobotVO;
import org.springframework.stereotype.Repository;

/**
 * @author destro
 */
@Repository
public class ExpeditionRepository {

	private Map<String, ExpeditionVO> map = new ConcurrentHashMap<String, ExpeditionVO>();

	/**
	 * Cria expedição e armazena em memória
	 * 
	 * @param name
	 * @param landX
	 * @param landY
	 * 
	 * @return
	 */
	public ExpeditionVO create(String name, int borderX, int borderY) {

		if (map.containsKey(name)) {
			throw new ValidationException("Expedition name already exists, name: " + name);
		} else if (borderX <= 0 || borderY <= 0) {
			throw new ValidationException(
					"Expedition border X,Y must be > 0, borderX: " + borderX + ", borderY: " + borderY);
		}

		ExpeditionVO vo = new ExpeditionVO(name, borderX, borderY);

		map.put(name, vo);

		return vo;
	}

	/**
	 * Efetua deploy do robo na expedição
	 * 
	 * @param expeditionName
	 * @param name
	 * @param landX
	 * @param landY
	 * @param direction
	 * @return
	 * @throws RepositoryException
	 */
	public RobotVO deployRobot(String expeditionName, String name, int landX, int landY, String direction) {

		ExpeditionVO expeditionVO = map.get(expeditionName);

		if (expeditionVO == null) {
			throw new ValidationException("Expedition name not exist, expeditionName: " + expeditionName);
		} else if (expeditionVO.getRobotList().stream().anyMatch(p -> p.getName().equalsIgnoreCase(name))) {
			throw new ValidationException("Robot name already exists, name: " + name);
		} else if (expeditionVO.getRobotList().stream()
				.anyMatch(p -> (p.getLandX() == landX && p.getLandY() == landY))) {
			throw new ValidationException("There is a Robot landed, landX: " + landX + ", landY: " + landY);
		} else if (landX > expeditionVO.getBorderX() || landY > expeditionVO.getBorderY()) {
			throw new ValidationException(
					"Robot landing out of expedition border limit, borderX: " + expeditionVO.getBorderX() + ", borderY:"
							+ expeditionVO.getBorderY() + ", landX: " + landX + ", landY: " + landY);
		} else if (!direction.toUpperCase().matches("(N|S|E|W)")) {
			throw new ValidationException("Wrong direction for robot (N|S|E|W) direction: " + direction);
		} else {
			RobotVO vo = new RobotVO(name, landX, landY, direction.toUpperCase());

			// Adiciona robo na lista
			expeditionVO.getRobotList().add(vo);

			return vo;
		}
	}

	/**
	 * Retorna expedição
	 * 
	 * @param expeditionName
	 * @param robotName
	 * @return
	 */
	public ExpeditionVO getExpedition(String expeditionName) {
		return map.get(expeditionName);
	}

	/**
	 * Retorna todas expedições
	 * 
	 * @param expeditionName
	 * @param robotName
	 * @return
	 */
	public Map<String, ExpeditionVO> getAllExpedition() {
		return map;
	}
}
