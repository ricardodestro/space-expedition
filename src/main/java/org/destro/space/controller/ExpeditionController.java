package org.destro.space.controller;

import org.destro.space.service.ExpeditionService;
import org.destro.space.service.RobotMoveService;
import org.destro.space.vo.ExpeditionVO;
import org.destro.space.vo.ResponseVO;
import org.destro.space.vo.RobotVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controle responsável na gerencia da expedição
 * 
 * @author destro
 */
@RestController
public class ExpeditionController {

	@Autowired
	private ExpeditionService expeditionService;

	@Autowired
	private RobotMoveService robotMoveService;

	/**
	 * Cria expedição
	 * 
	 * @param name
	 * @param borderX
	 * @param borderY
	 * @return
	 */
	@RequestMapping(value = "/createExpedition", method = RequestMethod.POST)
	public ResponseVO createExpedition(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "borderX", required = true) int borderX,
			@RequestParam(value = "borderY", required = true) int borderY) {

		ExpeditionVO expeditionVO = expeditionService.createExpedition(name,
				borderX, borderY);

		return new ResponseVO(HttpStatus.OK.value(), "OK", expeditionVO);
	}

	/**
	 * Efetua deploy de um novo robo para expedição
	 * 
	 * @param expeditionName
	 * @param name
	 * @param landX
	 * @param landY
	 * @param direction
	 * @return
	 */
	@RequestMapping(value = "/deployRobot/{expeditionName}", method = RequestMethod.POST)
	public ResponseVO deployRobot(
			@PathVariable(value = "expeditionName") String expeditionName,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "landX", required = true) int landX,
			@RequestParam(value = "landY", required = true) int landY,
			@RequestParam(value = "direction", required = true) String direction) {

		RobotVO robotVO = expeditionService.deployRobot(expeditionName, name,
				landX, landY, direction);

		return new ResponseVO(HttpStatus.OK.value(), "OK", robotVO);
	}

	/**
	 * Movimenta robo
	 * 
	 * @param expeditionName
	 * @param robotName
	 * @param commands
	 * @return
	 */
	@RequestMapping(value = "/moveRobot/{expeditionName}/{robotName}", method = RequestMethod.POST)
	public ResponseVO moveRobot(
			@PathVariable(value = "expeditionName") String expeditionName,
			@PathVariable(value = "robotName") String robotName,
			@RequestParam(value = "commands", required = true) String commands) {

		RobotVO robotVO = robotMoveService.moveRobot(expeditionName, robotName,
				commands);

		return new ResponseVO(HttpStatus.OK.value(), "OK", robotVO);
	}

	/**
	 * Retorna todas as expedições
	 * 
	 * @param expeditionName
	 * @param robotName
	 * @param commands
	 * @return
	 */
	@RequestMapping(value = "/allExpeditions", method = RequestMethod.GET)
	public ResponseVO allExpeditions() {

		return new ResponseVO(HttpStatus.OK.value(), "OK",
				expeditionService.getAllExpeditions());
	}
}
