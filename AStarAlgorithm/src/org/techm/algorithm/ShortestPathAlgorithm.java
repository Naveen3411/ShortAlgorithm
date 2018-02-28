package org.techm.algorithm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class ShortestPathAlgorithm {
	
    static int startPosI = 0, startPosJ = 0;
	static int endPosI = 0, endPosJ = 0;
	static char[][] charArr;
	Position[] shortestPath;
	Stack<Position> path;
	
	public ShortestPathAlgorithm(char[][] array) {
		this.charArr = array;
	}
	
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(new InputStreamReader(System.in));
		System.out.println("Please enter file location:");
		String fileName = sc.next();
		
		File file = new File(fileName);
		Scanner input = new Scanner(file);
		int rows = 0;
		int columns = 0;
		List<String> list = new ArrayList<String>();
		String line = "";
		while (input.hasNextLine()) {
			line = input.nextLine();
			if(line.contains(".")) {
			    list.add(line.trim());
			    rows++;
			}
		}
		
		columns = list.get(0).split("\\s").length;
		char[][] fileText = new char[rows][columns];
		int i=0, j=0;
		
		for(int k=0; k<list.size();k++) {
			j=0;
			for(int m=0; m<list.get(k).toCharArray().length; m++) {
				if(list.get(k).toCharArray()[m] != ' ') {
					fileText[i][j] = list.get(k).toCharArray()[m];
					if(fileText[i][j] == 'S') {
						startPosI = i;
						startPosJ = j;
					} else if(fileText[i][j] == 'E') {
						endPosI = i;
						endPosJ = j;
					}
					j++;
				}
			}
			i++;
		}
		
		ShortestPathAlgorithm shortPath = new ShortestPathAlgorithm(fileText);
		
        String s = "";
		for (char[] row : fileText) {
        	s += (Arrays.toString(row)).replace("[", "").replace("]", "").replace(",", "") + "\n";
        }
		
		s += "\n\n";
		
		Position[] path = shortPath.findShortPath();
        
		Position p1 = path[path.length-1];
		if(p1.x == endPosI && p1.y == endPosJ) { 
			for(Position p : path) {
				if(charArr[p.x][p.y] != 'S' && charArr[p.x][p.y] != 'E')
					charArr[p.x][p.y] = '*';
			}
		}
		
		for (char[] row : charArr) {
        	s += (Arrays.toString(row)).replace("[", "").replace("]", "").replace(",", "") + "\n";
        }
				
        Scanner sc1 = new Scanner(new InputStreamReader(System.in));
		System.out.println("Please enter output file path:");
		String oFileName = sc1.next();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(oFileName));
        writer.write(s);
        writer.close();
		System.out.println("File write done.");

	}

	public Position[] findShortPath() {
		Position startPos = new Position(startPosI, startPosJ);
		path = new Stack<Position>();
		shortestPath = null;

		LinkedList<Position> prevPosList = new LinkedList<Position>();
		Queue<Position> queue = new LinkedList<Position>();
		queue.offer(startPos);
		if (charArr[startPos.x][startPos.y] == '.') {
			charArr[startPos.x][startPos.y] = '"';
		}

		if (startPos == null) {
			return null;
		}

		while (!queue.isEmpty()) {
			Position position = queue.poll();
			prevPosList.add(position);

			if (charArr[position.x][position.y] != 'E') {
				Position downPos = new Position(position.x + 1, position.y, position);
				if (isDot(downPos)) {
					queue.offer(downPos);
					currVisit(downPos);
				}

				Position rightPos = new Position(position.x, position.y + 1, position);
				if (isDot(rightPos)) {
					queue.offer(rightPos);
					currVisit(rightPos);
				}

				Position upPos = new Position(position.x - 1, position.y, position);
				if (isDot(upPos)) {
					queue.offer(upPos);
					currVisit(upPos);
				}

				Position leftPos = new Position(position.x, position.y - 1, position);
				if (isDot(leftPos)) {
					queue.offer(leftPos);
					currVisit(leftPos);
				}
			} else {
				break;
			}
		}

		Position currPos = prevPosList.getLast();

		if (currPos != null) {
			do {
				path.push(currPos);
				currPos = currPos.prevPos;
			} while (currPos != null);

			shortestPath = new Position[path.size()];
			int i = 0;
			while (!path.isEmpty()) {
				shortestPath[i++] = path.pop();
			}
		}

		return shortestPath;
	}
	
	private boolean isDot(Position position) {
		boolean isCharDot = false;
		if(position.x >= 0 && position.y >= 0 && position.x < charArr.length && position.y < charArr[position.x].length
				&& ((charArr[position.x][position.y] == '.') || (charArr[position.x][position.y] == 'E'))) {
			isCharDot = true;
		}
		return isCharDot;
	}

	private void currVisit(Position position) {
		if (charArr[position.x][position.y] == '.') {
			charArr[position.x][position.y] = '"';
		}
	}
	
}



