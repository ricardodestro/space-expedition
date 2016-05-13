package org.destro.space.service;

import java.util.ArrayList;
import java.util.List;

import org.destro.space.ValidationException;
import org.destro.space.ValidationException.ValidationErrorCode;
import org.destro.space.repository.ExpeditionRepository;
import org.destro.space.utils.StringUtils;
import org.destro.space.vo.CommandVO;
import org.destro.space.vo.ExpeditionVO;
import org.destro.space.vo.RobotVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável no setup e movimento do robo
 * 
 * @author destro
 */
@Service
public class RobotMoveService {

	@Autowired
	private ExpeditionRepository expeditionRepository;

	interface Commands {
		String NORTH = "N";
		String SOUTH = "S";
		String EAST = "E";
		String WEST = "W";

		char LEFT = 'L';
		char RIGHT = 'R';
		char MOVE = 'M';
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
						() -> new ValidationException(
								ValidationErrorCode.NAME_NOT_EXISTS,
								"Robot not found, name: " + robotName));

		this.setup(expeditionVO, robotVO, commands);

		this.move(expeditionVO, robotVO);

		return robotVO;
	}

	/**
	 * Setup para os movimentos do robo
	 * 
	 * @param expeditionVO
	 * @param robotVO
	 * @param commands
	 */
	private void setup(ExpeditionVO expeditionVO, RobotVO robotVO,
			String commands) {

		List<CommandVO> testCommand = new ArrayList<CommandVO>();

		String lastPosition = robotVO.getDirection();

		for (char m : commands.toCharArray()) {

			int steps = 0;

			if (m == Commands.LEFT) {
				switch (lastPosition) {
				case Commands.NORTH:
					lastPosition = Commands.WEST;
					break;
				case Commands.SOUTH:
					lastPosition = Commands.EAST;
					break;
				case Commands.EAST:
					lastPosition = Commands.NORTH;
					break;
				case Commands.WEST:
					lastPosition = Commands.SOUTH;
					break;
				default:
					lastPosition = robotVO.getDirection();
					break;
				}
			} else if (m == Commands.RIGHT) {
				switch (lastPosition) {
				case Commands.NORTH:
					lastPosition = Commands.EAST;
					break;
				case Commands.SOUTH:
					lastPosition = Commands.WEST;
					break;
				case Commands.EAST:
					lastPosition = Commands.SOUTH;
					break;
				case Commands.WEST:
					lastPosition = Commands.NORTH;
					break;
				default:
					lastPosition = robotVO.getDirection();
					break;
				}
			} else if (m == Commands.MOVE) {
				steps++;
			} else {
				throw new ValidationException(
						ValidationErrorCode.INVALID_COMMAND,
						"Invalid command, pos: " + robotVO.getDirection()
								+ ", commands: " + commands.toString());
			}

			// Adiciona na lista de comandos
			testCommand.add(new CommandVO(lastPosition, steps));

		}

		// Simula movimento para validar colisões
		// TODO Implementar validação de colisão com outros robos
		this.move(expeditionVO, robotVO, testCommand);

		// Limpa ultimos movimento
		robotVO.getCommandList().clear();

		// Adicionar comandos de movimento testados
		robotVO.getCommandList().addAll(testCommand);
	}

	/**
	 * Movo ou simula movimento do robo
	 * 
	 * @param expeditionVO
	 * @param robotVO
	 * @param testMove
	 */
	private void move(ExpeditionVO expeditionVO, RobotVO robotVO,
			List<CommandVO> testMove) {

		int x = robotVO.getLandX();
		int y = robotVO.getLandY();
		String pos = robotVO.getDirection();

		List<CommandVO> list = testMove != null ? testMove : robotVO
				.getCommandList();

		for (CommandVO cmd : list) {

			switch (cmd.getCommand()) {
			case Commands.NORTH:
				y += cmd.getSteps();
				break;
			case Commands.SOUTH:
				y -= cmd.getSteps();
				break;
			case Commands.EAST:
				x += cmd.getSteps();
				break;
			case Commands.WEST:
				x -= cmd.getSteps();
				break;
			default:
				throw new ValidationException(
						ValidationErrorCode.INVALID_COMMAND, "Invalid command");
			}
			pos = cmd.getCommand();

			if (x < 0 || x > expeditionVO.getBorderX() || y < 0
					|| y > expeditionVO.getBorderY()) {
				throw new ValidationException(
						ValidationErrorCode.OUT_OF_BORDER,
						"Invalid moviment, out limit of expedition borderX: "
								+ expeditionVO.getBorderX() + ", borderY: "
								+ expeditionVO.getBorderY() + ", x: " + x
								+ ", y:" + y);
			}

			// Verifica possível colisão
			checkCollision(robotVO, x, y);

		}

		// Atualiza dados de posição do robo se não for teste
		if (testMove == null) {
			robotVO.setDirection(pos);
			robotVO.setLandX(x);
			robotVO.setLandY(y);
		}
	}

	/**
	 * Verificar se robo colidirá com outro robo
	 * 
	 * @param x
	 * @param y
	 */
	private void checkCollision(RobotVO robotVO, int x, int y) {
		for (ExpeditionVO e : expeditionRepository.getAllExpedition().values()) {
			e.getRobotList().forEach(
					p -> {
						if (!p.equals(robotVO) && p.getLandX() == x
								&& p.getLandY() == y) {
							throw new ValidationException(
									ValidationErrorCode.IMMINENT_COLLISION,
									"Robot name: " + p.getName()
											+ ", expeditionName: "
											+ e.getName());
						}
					});
		}
	}

	/**
	 * Move o robo definitivamente
	 * 
	 * @param robotVO
	 */
	private void move(ExpeditionVO expeditionVO, RobotVO robotVO) {

		this.move(expeditionVO, robotVO, null);
	}

}
