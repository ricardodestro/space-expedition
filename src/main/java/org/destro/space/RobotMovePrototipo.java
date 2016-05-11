package org.destro.space;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Prototipo de movimento de um robo
 * 
 * @author destro
 */
public class RobotMovePrototipo {

	private static Scanner scan;

	public static void main(String[] args) throws Exception {

		// Entrada no console para comando
		scan = new Scanner(System.in);

		System.out.println("First command ?: ");

		// Leitura da primeira entrada de dados
		String firstCommand = scan.nextLine();

		// Separa os comandos
		String[] c1 = firstCommand.split("");

		int x = 0;
		int y = 0;
		String pos = "N";

		System.out.println("Position ?: ");

		// Posição da sonda
		String secondCommand = scan.nextLine();

		// Separa os comandos da posição da sonda
		String[] c2 = secondCommand.split("");

		System.out.println("Move ?: ");

		// Movimento da sonda
		String thirdCommand = scan.nextLine();

		// Verifica se comandos de movimento existem
		if (c1.length > 0) {
			x = Integer.valueOf(c1[0]);
			y = Integer.valueOf(c1[1]);

			x = Integer.valueOf(c2[0]);
			y = Integer.valueOf(c2[1]);
			pos = c2[2];

			// Lista de comandos
			List<Command> commandList = createCommands(pos,
					thirdCommand.toCharArray());

			for (Command cmd : commandList) {

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
					throw new Exception("Invalid command");
				}

				pos = cmd.getCommand();
			}

		} else {
			// Comando inválido
			x = 999;
			y = 999;
		}

		// Resultado
		System.out.println("(" + x + "," + y + "," + pos + ")");
	}

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
	 * Cria comandos
	 * 
	 * @param moveCommand
	 * 
	 * @return
	 */
	private static List<Command> createCommands(String pos, char[] moveCommand)
			throws Exception {

		List<Command> commandList = new ArrayList<Command>();

		String lastPosition = pos;

		for (char m : moveCommand) {

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
					lastPosition = pos;
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
					lastPosition = pos;
					break;
				}
			} else if (m == Commands.MOVE) {
				steps++;
			} else {
				throw new Exception("Invalid command, pos: " + pos
						+ ", moveCommand: " + moveCommand.toString());
			}

			System.out.println("[ADD] command: " + m + ", pos: " + lastPosition
					+ ", steps: " + steps);

			// Adiciona na lista de comandos
			commandList.add(new Command(lastPosition, steps));
		}

		return commandList;
	}

	/**
	 * Comando
	 */
	private static class Command {
		String command;

		int steps;

		public Command(String command, int steps) {
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
}
