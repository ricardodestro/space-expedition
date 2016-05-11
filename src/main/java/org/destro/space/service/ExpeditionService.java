package org.destro.space.service;

import java.util.Map;

import org.destro.space.ValidationException;
import org.destro.space.repository.ExpeditionRepository;
import org.destro.space.utils.StringUtils;
import org.destro.space.vo.ExpeditionVO;
import org.destro.space.vo.RobotVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviços da expedição
 * 
 * @author destro
 */
@Service
public class ExpeditionService {

	@Autowired
	private ExpeditionRepository expeditionRepository;

	@Autowired
	private RobotMoveService robotMoveService;

	/**
	 * Cria expedição
	 * 
	 * @param name
	 * @param landX
	 * @param landY
	 * 
	 * @return
	 */
	public ExpeditionVO createExpedition(String name, int borderX, int borderY) {

		return expeditionRepository.create(StringUtils.normalize(name),
				borderX, borderY);
	}

	/**
	 * Deploy do robo na expedição
	 * 
	 * @param expeditionName
	 * @param name
	 * @param landX
	 * @param landY
	 * @param direction
	 * @return
	 */
	public RobotVO deployRobot(String expeditionName, String name, int landX,
			int landY, String direction) {

		return expeditionRepository.deployRobot(
				StringUtils.normalize(expeditionName),
				StringUtils.normalize(name), landX, landY, direction);
	}

	/**
	 * Movimenta robo
	 * 
	 * @param expeditionName
	 * @param robotName
	 * @param commands
	 * @return
	 */
	public RobotVO moveRobot(String expeditionName, String robotName,
			String commands) {

		ExpeditionVO expeditionVO = expeditionRepository
				.getExpedition(StringUtils.normalize(expeditionName));

		RobotVO robotVO = expeditionVO
				.getRobotList()
				.stream()
				.filter(p -> p.getName().equals(
						StringUtils.normalize(robotName)))
				.findFirst()
				.orElseThrow(
						() -> new ValidationException("Robot not found, name: "
								+ robotName));

		robotMoveService.setup(expeditionVO, robotVO, commands);

		robotMoveService.move(expeditionVO, robotVO);

		return robotVO;
	}

	/**
	 * Retorna todas expedições
	 * 
	 * @return
	 */
	public Map<String, ExpeditionVO> getAllExpeditions() {

		return expeditionRepository.getAllExpedition();
	}
}
