package org.destro.space.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.destro.space.SpaceApplication;
import org.destro.space.ValidationException.ValidationErrorCode;
import org.destro.space.repository.ExpeditionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpaceApplication.class)
@WebAppConfiguration
public class ExpeditionControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ExpeditionRepository expeditionRepository;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext).build();

		expeditionRepository.cleanAllExpedition();
	}

	@Test
	public void createExpedition() throws Exception {

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.post("/createExpedition")
						.param("name", "exp0").param("borderX", "9")
						.param("borderY", "9")).andExpect(status().isOk()).andDo(print());

		result.andExpect(jsonPath("code").value(200));
		result.andExpect(jsonPath("entity").exists());
	}

	@Test
	public void createExpeditionWithSameName() throws Exception {

		mockMvc.perform(
				MockMvcRequestBuilders.post("/createExpedition")
						.param("name", "exp0").param("borderX", "9")
						.param("borderY", "9")).andExpect(status().isOk());

		mockMvc.perform(
				MockMvcRequestBuilders.post("/createExpedition")
						.param("name", "exp0").param("borderX", "9")
						.param("borderY", "9"))
				.andExpect(status().is4xxClientError())
				.andExpect(
						jsonPath("code").value(
								ValidationErrorCode.NAME_ALREADY_EXISTS
										.getCode()));
	}

	@Test
	public void createExpeditionWithInvalidParameter() throws Exception {

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.post("/createExpedition")
						.param("name", "exp0").param("borderX", "-1")
						.param("borderY", "5")).andExpect(
				status().is4xxClientError());

		result.andExpect(jsonPath("code").value(
				ValidationErrorCode.OUT_OF_BORDER.getCode()));
		result.andExpect(jsonPath("entity").isEmpty());
	}

	@Test
	public void deployRobot() throws Exception {

		// Cria expedição
		mockMvc.perform(
				MockMvcRequestBuilders.post("/createExpedition")
						.param("name", "exp1").param("borderX", "7")
						.param("borderY", "7")).andExpect(status().isOk());

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.post("/deployRobot/exp1")
						.param("name", "robot1").param("landX", "3")
						.param("landY", "3").param("direction", "N"))
				.andExpect(status().isOk()).andDo(print());

		result.andExpect(jsonPath("code").value(200));
		result.andExpect(jsonPath("entity").exists());
	}

	@Test
	public void deployRobotWithSameName() throws Exception {

		// Cria expedição
		mockMvc.perform(
				MockMvcRequestBuilders.post("/createExpedition")
						.param("name", "exp100").param("borderX", "8")
						.param("borderY", "8")).andExpect(status().isOk());

		mockMvc.perform(
				MockMvcRequestBuilders.post("/deployRobot/exp100")
						.param("name", "robot1").param("landX", "2")
						.param("landY", "2").param("direction", "N"))
				.andExpect(status().isOk());

		mockMvc.perform(
				MockMvcRequestBuilders.post("/deployRobot/exp100")
						.param("name", "robot1").param("landX", "2")
						.param("landY", "2").param("direction", "N"))
				.andExpect(status().is4xxClientError())
				.andExpect(
						jsonPath("code").value(
								ValidationErrorCode.NAME_ALREADY_EXISTS
										.getCode()));
		;
	}

	@Test
	public void deployRobotWithSameLanding() throws Exception {

		// Cria expedição
		mockMvc.perform(
				MockMvcRequestBuilders.post("/createExpedition")
						.param("name", "exp101").param("borderX", "8")
						.param("borderY", "8")).andExpect(status().isOk());

		mockMvc.perform(
				MockMvcRequestBuilders.post("/deployRobot/exp101")
						.param("name", "robot1").param("landX", "2")
						.param("landY", "2").param("direction", "N"))
				.andExpect(status().isOk());

		mockMvc.perform(
				MockMvcRequestBuilders.post("/deployRobot/exp101")
						.param("name", "robot2").param("landX", "2")
						.param("landY", "2").param("direction", "N"))
				.andExpect(status().is4xxClientError())
				.andExpect(
						jsonPath("code").value(
								ValidationErrorCode.NOT_ALLOWED_TO_LAND
										.getCode()));
	}

	@Test
	public void deployRobotWithInvalidExpedition() throws Exception {

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.post("/deployRobot/expInvalid")
						.param("name", "robot100").param("landX", "1")
						.param("landY", "1").param("direction", "N"))
				.andExpect(status().is4xxClientError());

		result.andExpect(jsonPath("code").value(
				ValidationErrorCode.NAME_NOT_EXISTS.getCode()));
		result.andExpect(jsonPath("entity").isEmpty());
	}

	@Test
	public void deployRobotWithInvalidLand() throws Exception {

		// Cria expedição
		mockMvc.perform(
				MockMvcRequestBuilders.post("/createExpedition")
						.param("name", "exp201").param("borderX", "20")
						.param("borderY", "20")).andExpect(status().isOk());

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.post("/deployRobot/exp201")
						.param("name", "robot201").param("landX", "21")
						.param("landY", "21").param("direction", "N"))
				.andExpect(status().is4xxClientError());

		result.andExpect(jsonPath("code").value(
				ValidationErrorCode.OUT_OF_BORDER.getCode()));
		result.andExpect(jsonPath("entity").isEmpty());
	}

	@Test
	public void deployRobotWithInvalidDirection() throws Exception {

		// Cria expedição
		mockMvc.perform(
				MockMvcRequestBuilders.post("/createExpedition")
						.param("name", "exp202").param("borderX", "20")
						.param("borderY", "20")).andExpect(status().isOk());

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.post("/deployRobot/exp202")
						.param("name", "robot202").param("landX", "13")
						.param("landY", "14").param("direction", "X"))
				.andExpect(status().is4xxClientError());

		result.andExpect(jsonPath("code").value(
				ValidationErrorCode.INVALID_DIRECTION.getCode()));
		result.andExpect(jsonPath("entity").isEmpty());
	}

	@Test
	public void moveRobot() throws Exception {

		// Cria expedição
		mockMvc.perform(
				MockMvcRequestBuilders.post("/createExpedition")
						.param("name", "exp20").param("borderX", "5")
						.param("borderY", "5")).andExpect(status().isOk());

		// Cria robo
		mockMvc.perform(
				MockMvcRequestBuilders.post("/deployRobot/exp20")
						.param("name", "robot20").param("landX", "3")
						.param("landY", "3").param("direction", "E"))
				.andExpect(status().isOk());

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.post("/moveRobot/exp20/robot20").param(
						"commands", "MMRMMRMRRM")).andExpect(status().isOk()).andDo(print());

		result.andExpect(jsonPath("code").value(200)).andDo(print());
		result.andExpect(jsonPath("entity").exists());
		result.andExpect(jsonPath("entity.landX").value(5));
		result.andExpect(jsonPath("entity.landY").value(1));
		result.andExpect(jsonPath("entity.direction").value("E"));

	}

	@Test
	public void moveRobotInvalidMove() throws Exception {

		// Cria expedição
		mockMvc.perform(
				MockMvcRequestBuilders.post("/createExpedition")
						.param("name", "exp11").param("borderX", "11")
						.param("borderY", "11")).andExpect(status().isOk());

		// Cria robo
		mockMvc.perform(
				MockMvcRequestBuilders.post("/deployRobot/exp11")
						.param("name", "robot11").param("landX", "3")
						.param("landY", "3").param("direction", "E"))
				.andExpect(status().isOk());

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.post("/moveRobot/exp11/robot11").param(
						"commands", "MMMMMMMMMMMMMMMMMM")).andExpect(
				status().is4xxClientError());

		result.andExpect(jsonPath("code").value(
				ValidationErrorCode.OUT_OF_BORDER.getCode()));
		result.andExpect(jsonPath("entity").isEmpty());
	}

	@Test
	public void moveRobotInvalidWithCollisionMove() throws Exception {

		// Cria expedição
		mockMvc.perform(
				MockMvcRequestBuilders.post("/createExpedition")
						.param("name", "exp11").param("borderX", "11")
						.param("borderY", "11")).andExpect(status().isOk());

		// Cria robo 1
		mockMvc.perform(
				MockMvcRequestBuilders.post("/deployRobot/exp11")
						.param("name", "robot11").param("landX", "3")
						.param("landY", "3").param("direction", "E"))
				.andExpect(status().isOk());

		// Cria robo 2
		mockMvc.perform(
				MockMvcRequestBuilders.post("/deployRobot/exp11")
						.param("name", "robot12").param("landX", "3")
						.param("landY", "2").param("direction", "N"))
				.andExpect(status().isOk());

		// Move robo 2 para mesmo posição do robo 1
		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.post("/moveRobot/exp11/robot12").param(
						"commands", "M"))
				.andExpect(status().is4xxClientError());

		result.andExpect(jsonPath("code").value(
				ValidationErrorCode.IMMINENT_COLLISION.getCode()));
		result.andExpect(jsonPath("entity").isEmpty());
	}

	@Test
	public void allExpeditions() throws Exception {

		// Cria expedição
		mockMvc.perform(
				MockMvcRequestBuilders.post("/createExpedition")
						.param("name", "exp1000").param("borderX", "5")
						.param("borderY", "5")).andExpect(status().isOk());

		mockMvc.perform(
				MockMvcRequestBuilders.post("/createExpedition")
						.param("name", "exp2000").param("borderX", "7")
						.param("borderY", "7")).andExpect(status().isOk());

		mockMvc.perform(
				MockMvcRequestBuilders.post("/createExpedition")
						.param("name", "exp3000").param("borderX", "9")
						.param("borderY", "9")).andExpect(status().isOk());

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.get("/allExpeditions")).andExpect(
				status().isOk()).andDo(print());

		result.andExpect(jsonPath("code").value(200));
		result.andExpect(jsonPath("entity").isMap());

	}

	@Test
	public void notFound() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/notFound")).andExpect(
				status().isNotFound());
	}
}
