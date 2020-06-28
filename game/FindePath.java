package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 클래스 명 : FindPath class
 * 설명 : 길을 찾는 에이스타 알고리즘, 길찾기 스레드에 사용한다.
 *        캐릭터의 위치와 목표 지점의 위치로 빠른 길을 찾는다.
 * 
 * 
 * @author sim-younghyun
 *
 */
public class FindePath {
    private PriorityQueue<Node> openList;
    private ArrayList<Node> closedList;
    private HashMap<Node, Integer> gMaps;
    private HashMap<Node, Integer> fMaps;
    private Node[] node;
    private int distanceBetweenNodes = 1;
    private int[][] miniMap;
    
    public FindePath() {
    	this.openList = new PriorityQueue<Node>(100, new fCompare());
    	this.closedList = new ArrayList<Node>();
    	this.gMaps = new HashMap<Node, Integer>();
    	this.fMaps = new HashMap<Node, Integer>();
    }
    
    public void init(int[][] miniMap) {
    	this.miniMap = miniMap;
    	this.node = new Node[miniMap[0].length * miniMap.length];
    	int [] directionX= {-1,0,1,0};
    	int [] directionY= {0,-1,0,1};
    	
    	for(int i=0; i<miniMap.length; i++) {
    		for(int j=0; j<miniMap[0].length; j++) {
    			int index = miniMap[0].length * i + j;
    			node[index] = new Node();
    			node[index].setData(index+1);
    			node[index].setXY(j, i);
    		}
    	}
    	
    	for (int i = 0; i <miniMap.length; i++) {
        	for(int j=0; j<miniMap[0].length; j++) {
        		for(int z=0;z<4;z++) {
        			int tempX = j+directionX[z];
        			int tempY = i+directionY[z];
        			
					if(tempX<0 ||tempX>=miniMap[0].length || tempY<0 || tempY>=miniMap.length){
						continue;
					}
					
					if(miniMap[tempY][tempX] == 1) {
						continue;
					}
					
        			node[8*i+j].addNeighbors(node[8*tempY+tempX]);
        		}
        	}
        }
    }
    
//    public static void main(String[] args) {
//    	int[][] forest1MiniMap = {
//				{5,2,0,0,0,0,0,0}
//				,{0,2,0,4,0,1,1,1}
//				,{0,0,0,1,0,0,0,0}
//				,{0,0,1,1,0,0,0,0}
//				,{0,0,0,0,0,7,0,0}
//				,{0,0,0,0,0,0,0,0}
//				,{0,0,1,0,0,0,0,0}
//				,{0,0,1,0,0,0,0,6}
//				};
//    	
//    	FindePath findePath = new FindePath();
//    	findePath.init(forest1MiniMap);
//    	findePath.search(7, 7, 0, 0);
//	}

    public Node search(int i, int j,int row,int col) {
    	Node start = node[miniMap[0].length * i + j];
    	Node end = node[miniMap[0].length * row + col];
    	
    	openList.clear();
        closedList.clear();

        gMaps.put(start, 0);
        openList.add(start);
        
        while (!openList.isEmpty()) {
            Node current = openList.element();
            if (current.equals(end)) {
//                System.out.println("Goal Reached");
//                printPath(current);
                return current;
            }
            closedList.add(openList.poll());
            ArrayList<Node> neighbors = current.getNeighbors();

            for (Node neighbor : neighbors) {
                int gScore = gMaps.get(current) + distanceBetweenNodes;
                int fScore = gScore + h(neighbor, current);

                if (closedList.contains(neighbor)) {

                    if (gMaps.get(neighbor) == null) {
                        gMaps.put(neighbor, gScore);
                    }
                    if (fMaps.get(neighbor) == null) {
                        fMaps.put(neighbor, fScore);
                    }

                    if (fScore >= fMaps.get(neighbor)) {
                        continue;
                    }
                }
                if (!openList.contains(neighbor) || fScore < fMaps.get(neighbor)) {
                    neighbor.setParent(current);
                    gMaps.put(neighbor, gScore);
                    fMaps.put(neighbor, fScore);
                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);

                    }
                }
            }
        }
        
        return start;
     }
    
    private int h(Node node, Node goal) {
        int x = node.getX() - goal.getX();
        int y = node.getY() - goal.getY();
        return x * x + y * y;
    }
    
    private List<Node> getNodeList(Node node){
    	List<Node> list = new ArrayList<Node>();
    	list.add(node);
    	while (node.getParent() != null) {
            node = node.getParent();
            list.add(node);
        }
    	return list;
    }
    
    private void printPath(Node node) {
        System.out.println(node.getData());

        while (node.getParent() != null) {
            node = node.getParent();
            System.out.println(node.getData());
            System.out.println(node.getX()+","+node.getY());
        }
    }

    class fCompare implements Comparator<Node> {

        public int compare(Node o1, Node o2) {
            if (fMaps.get(o1) < fMaps.get(o2)) {
                return -1;
            } else if (fMaps.get(o1) > fMaps.get(o2)) {
                return 1;
            } else {
                return 1;
            }
        }
    }
}

class Node {
	
    private Node parent;
    private ArrayList<Node> neighbors;
    private int x;
    private int y;
    private int data;
    private int type;
    
    public Node() {
        neighbors = new ArrayList<Node>();
    }

    public Node(int x, int y, int type) {
        this();
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public Node(Node parent) {
        this();
        this.parent = parent;
    }

    public Node(Node parent, int x, int y) {
        this();
        this.parent = parent;
        this.x = x;
        this.y = y;
    }

    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<Node> neighbors) {
        this.neighbors = neighbors;
    }

    public void addNeighbor(Node node) {
        this.neighbors.add(node);
    }

    public void addNeighbors(Node... node) {
        this.neighbors.addAll(Arrays.asList(node));
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public boolean equals(Node n) {
        return this.x == n.x && this.y == n.y;
    }

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
