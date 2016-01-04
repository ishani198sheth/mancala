import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class Mancala {
    static int PLAYER = 1;
    static int OPP = 0;
    static int pits;
    static int player;
    static int MANCALA;
    static int CUTOFF;
    static int SECONDTURNS = 2;
    static String PRINT="ON";
    static int evaluation;
//    static int CALL=0;
    

    public static void main(String[] args) throws IOException,NullPointerException,ArrayIndexOutOfBoundsException{
    	try{
    	PrintWriter nextstate = new PrintWriter("next_state.txt", "UTF-8");
        Scanner sc = null;
        sc = new Scanner(new File("input_2.txt"));
        sc.useDelimiter("\n");

        ArrayList<String> information = new ArrayList<String>();

        while (sc.hasNext()) {
            String s = sc.next();
            information.add(s);
            if (s.trim().isEmpty()) {
                continue;
            }
        }
        sc.close();

        int i=0;

        int task = Integer.valueOf(information.get(i).trim());
        i++;

        //player assignment
        player = Integer.valueOf(information.get(i).trim());
        i++;

        //depth assignment
        CUTOFF = Integer.valueOf(information.get(i).trim());
        i++;

        ArrayList<ArrayList<Integer>> boards = new ArrayList<ArrayList<Integer>>();

        //board state for player 2
        String board2info = information.get(i);
        String board2list[] = board2info.split(" ");
        ArrayList<Integer> board2 = new ArrayList<Integer>();
        for (int b = 0; b < board2list.length; b++) {
            board2.add(Integer.valueOf(board2list[b].trim()));
        }
        Collections.reverse(board2);
        i++;

        //board state for player 1
        String board1info = information.get(i);
        String board1list[] = board1info.split(" ");
        ArrayList<Integer> board1 = new ArrayList<Integer>();
        for (int b = 0; b < board1list.length; b++) {
            board1.add(Integer.valueOf(board1list[b].trim()));
        }
        i++;

        pits = board1.size();
        MANCALA = pits;

        //player2 mancala value
        board2.add(Integer.valueOf(information.get(i).trim()));
        i++;

        //player1 mancala value
        board1.add(Integer.valueOf(information.get(i).trim()));
        i++;

        if(player == 1){
            boards.add(board2);
            boards.add(board1);
        } else {
            boards.add(board1);
            boards.add(board2);
        }

        ArrayList<Integer> secondTurn = new ArrayList<Integer>();
        secondTurn.add(0);
        secondTurn.add(0);

        boards.add(secondTurn);

        /*ArrayList<ArrayList<Integer>> next = new ArrayList<ArrayList<Integer>>();
        next = getNextState(boards, PLAYER, pits-1);
        printBoards(next);
        next = getNextState(next, OPP, 0);
        printBoards(next);
        next = getNextState(next, PLAYER, pits-2);
        printBoards(next);*/
        //printBoards(getNextState(boards, OPP, 0));
        //printBoards(getNextState(boards, OPP, 1));
        //printBoards(getNextState(boards, OPP, 2));

        
//        printBoards(nextState);
        
        //Greedy Call
        if(task==1)
        {
        	CUTOFF=1;
        	PrintWriter traverselog = new PrintWriter("traverse_log.txt", "UTF-8"); 
//        	PRINT = "OFF";
        	int nextAction = getMinimaxDecision(boards,task,traverselog);
        	ArrayList<ArrayList<Integer>> nextState = getNextState(boards, PLAYER, nextAction);
        	while(nextState.get(SECONDTURNS).get(PLAYER)==1)
            {
        		PRINT="OFF";
        		CUTOFF=1;
            	nextAction= getMinimaxDecision(nextState,task,traverselog);
            	if(nextAction>=0)
            	{
            	nextState=getNextState(nextState, PLAYER, nextAction);
            	}
            	else{
            		break;
            	}
            }
            //greedy mancala pit selection after first move
//        	System.out.println(nextState.get(PLAYER));
//          System.out.println(nextState.get(OPP));
        	if(player==2)
            {
            Collections.reverse(nextState.get(PLAYER));
            for(int z=1;z<nextState.get(PLAYER).size();z++)
            {
            	
            	nextstate.print((nextState.get(PLAYER).get(z)+" "));
            }
            nextstate.println();
            for(int z=0;z<nextState.get(OPP).size()-1;z++)
            {
            	nextstate.print(nextState.get(OPP).get(z)+" ");
            }
            nextstate.println();
            nextstate.println(nextState.get(PLAYER).get(0));
            nextstate.println(nextState.get(OPP).get(nextState.get(OPP).size()-1)); 
//            System.out.println(nextState.get(2));
//            System.out.println(nextState.get(OPP));
            }
            else
            {
            	Collections.reverse(nextState.get(OPP));
//            	System.out.println(nextState.get(OPP));
//                System.out.println(nextState.get(PLAYER));
                for(int z=1;z<nextState.get(OPP).size();z++)
                {
                	nextstate.print(nextState.get(OPP).get(z)+" ");
                }
                nextstate.println();
                for(int z=0;z<nextState.get(PLAYER).size()-1;z++)
                {
                	nextstate.print(nextState.get(PLAYER).get(z)+" ");
                }
                nextstate.println();
                nextstate.println(nextState.get(OPP).get(0));
                nextstate.println(nextState.get(PLAYER).get(nextState.get(PLAYER).size()-1)); 
                
            }
            
        }
        
        //Minimax Call
        if(task==2)
        {

          PrintWriter traverselog = new PrintWriter("traverse_log.txt", "UTF-8"); 

        	traverselog.println("Node,Depth,Value");
            int nextAction = getMinimaxDecision(boards,task,traverselog);
            traverselog.close();
            ArrayList<ArrayList<Integer>> nextState = getNextState(boards, PLAYER, nextAction);
            while(nextState.get(SECONDTURNS).get(PLAYER)==1)
            {
            	PRINT="OFF";
//            	CALL=CALL+1;
//            	System.out.println("Node,Depth,Value");
            	nextAction= getMinimaxDecision(nextState,task,traverselog);
            	if(nextAction>=0)
            	{
            	nextState=getNextState(nextState, PLAYER, nextAction);
            	}
            	else
            	{
            		break;
            	}
            }
//            System.out.println(nextState.get(PLAYER));
            if(player==2)
            {
            Collections.reverse(nextState.get(PLAYER));
            for(int z=1;z<nextState.get(PLAYER).size();z++)
            {
            	nextstate.print(nextState.get(PLAYER).get(z)+" ");
            }
            nextstate.println();
            for(int z=0;z<nextState.get(OPP).size()-1;z++)
            {
            	nextstate.print(nextState.get(OPP).get(z)+" ");
            }
            nextstate.println();
            nextstate.println(nextState.get(PLAYER).get(0));
            nextstate.println(nextState.get(OPP).get(nextState.get(OPP).size()-1)); 
            
            
            }
            else
            {
            	Collections.reverse(nextState.get(OPP));
//            	System.out.println(nextState.get(OPP));
//                System.out.println(nextState.get(PLAYER));
                for(int z=1;z<nextState.get(OPP).size();z++)
                {
                	nextstate.print(nextState.get(OPP).get(z)+" ");
                }
                nextstate.println();
                for(int z=0;z<nextState.get(PLAYER).size()-1;z++)
                {
                	nextstate.print(nextState.get(PLAYER).get(z)+" ");
                }
                nextstate.println();
                nextstate.println(nextState.get(OPP).get(0));
                nextstate.println(nextState.get(PLAYER).get(nextState.get(PLAYER).size()-1));
        }
        }
        
        //Alpha-Beta Call
        if(task==3)
        {
        	PrintWriter traverselog = new PrintWriter("traverse_log.txt", "UTF-8"); 
        	traverselog.println("Node,Depth,Value,Alpha,Beta");
            int nextAction = getAlphaBetaMinimaxDecision(boards,traverselog);
            traverselog.close();
            ArrayList<ArrayList<Integer>> nextState = getNextState(boards, PLAYER, nextAction);
            while(nextState.get(SECONDTURNS).get(PLAYER)==1)
            {
            	PRINT="OFF";
            	nextAction= getAlphaBetaMinimaxDecision(nextState,traverselog);
            	if(nextAction>=0)
            	{
            		nextState=getNextState(nextState, PLAYER, nextAction);
            	}
            	else{
            		break;
            	}
            }
//            System.out.println(nextState.get(PLAYER));
            if(player==2)
            {
            Collections.reverse(nextState.get(PLAYER));
            for(int z=1;z<nextState.get(PLAYER).size();z++)
            {
            	nextstate.print(nextState.get(PLAYER).get(z)+" ");
            }
            nextstate.println();
            for(int z=0;z<nextState.get(OPP).size()-1;z++)
            {
            	nextstate.print(nextState.get(OPP).get(z)+" ");
            }
            nextstate.println();
            nextstate.println(nextState.get(PLAYER).get(0));
            nextstate.println(nextState.get(OPP).get(nextState.get(OPP).size()-1)); 
            
            
            }
            else
            {
            	Collections.reverse(nextState.get(OPP));
//            	System.out.println(nextState.get(OPP));
//                System.out.println(nextState.get(PLAYER));
                for(int z=1;z<nextState.get(OPP).size();z++)
                {
                	nextstate.print(nextState.get(OPP).get(z)+" ");
                }
                nextstate.println();
                for(int z=0;z<nextState.get(PLAYER).size()-1;z++)
                {
                	nextstate.print(nextState.get(PLAYER).get(z)+" ");
                }
                nextstate.println();
                nextstate.println(nextState.get(OPP).get(0));
                nextstate.println(nextState.get(PLAYER).get(nextState.get(PLAYER).size()-1));
        }
        }
        nextstate.close();
    }
    	catch(ArrayIndexOutOfBoundsException e){
			 
			 System.out.println("Element not found in nodelist");
		 }
		catch(IOException e){
			 
			 System.out.println("Input Output exception");
		 }
		catch(NullPointerException e){
			 
			 System.out.println("Null Pointer exception");
		 }
    }
    static void printBoards(ArrayList<ArrayList<Integer>> boards){
        ArrayList<ArrayList<Integer>> state = getStateCopy(boards);
        Collections.reverse(state.get(PLAYER));
        System.out.println("player  : " + state.get(PLAYER));
        System.out.println("opponent:    " + state.get(OPP));
    }

    static ArrayList<ArrayList<Integer>> getStateCopy(ArrayList<ArrayList<Integer>> boards){
        ArrayList<ArrayList<Integer>> state = new ArrayList<ArrayList<Integer>>();
        for(ArrayList<Integer> board:boards){
            ArrayList<Integer> copy = new ArrayList<Integer>(board);
            state.add(copy);
        }
        return state;
    }
    
    static boolean gameOver(ArrayList<ArrayList<Integer>> board)
    {
    	
    	if(player==1)
    	{
    		int stones=0;
    		int opponentstones=0;
    		for(int players=1;players<=2;players++)
    		{
    			
    		if(players==1)
    		{
    		for(int pit=0; pit<pits; pit++)
    		{
    			stones+=board.get(PLAYER).get(pit);
    		}
    		if(stones==0)
    		{
    			for(int i=pits-1; i>=0; i--)
    			{
    				opponentstones+=board.get(OPP).get(i);
    				board.get(OPP).set(i, 0);
    			}
//    			Collections.reverse(board.get(OPP));
    			int opponentMancala=board.get(OPP).get(board.get(OPP).size()-1);
    			opponentMancala+=opponentstones;
    			int playerMancala=board.get(PLAYER).get(board.get(PLAYER).size()-1);
    			evaluation = playerMancala-opponentMancala;
    			return true;
    		}
    		}
    		
    		if(players==2)
    		{
    			for(int i=pits-1; i>=0; i--)
        		{
        			stones+=board.get(OPP).get(i);
        		}
        		if(stones==0)
        		{
        			for(int pit=0; pit<pits; pit++)
        			{
        				opponentstones+=board.get(PLAYER).get(pit);
        				board.get(PLAYER).set(pit, 0);
        			}
//        			Collections.reverse(board.get(OPP));
        			int opponentMancala=board.get(OPP).get(board.get(OPP).size()-1);
        			opponentMancala+=opponentstones;
        			int playerMancala = board.get(PLAYER).get(board.get(PLAYER).size()-1);
        			evaluation = playerMancala-opponentMancala;
        			return true;
        		}
    		}
    		}
    		}
    	
    	if(player==2)
    	{
    		int stones=0;
    		int opponentstones=0;
    		for(int players=1;players<=2;players++)
    		{
    			
    			if(players==1)
        		{
        		for(int pit=0; pit<pits; pit++)
        		{
        			stones+=board.get(OPP).get(pit);
        		}
        		if(stones==0)
        		{
        			for(int i=pits-1; i>=0; i--)
        			{
        				opponentstones+=board.get(PLAYER).get(i);
        				board.get(PLAYER).set(i, 0);
        			}
//        			Collections.reverse(board.get(PLAYER));
        			int opponentMancala=board.get(OPP).get(board.get(OPP).size()-1);
        			opponentMancala+=opponentstones;
        			int playerMancala=board.get(PLAYER).get(board.get(PLAYER).size()-1);
        			evaluation = playerMancala-opponentMancala;
        			return true;
        		}
        		}
        		
        		if(players==2)
        		{
        			for(int i=pits-1; i>=0; i--)
            		{
            			stones+=board.get(PLAYER).get(i);
            		}
            		if(stones==0)
            		{
            			for(int pit=0; pit<pits; pit++)
            			{
            				opponentstones+=board.get(OPP).get(pit);
            				board.get(OPP).set(pit, 0);
            			}
//            			Collections.reverse(board.get(PLAYER));
            			int opponentMancala=board.get(OPP).get(board.get(OPP).size()-1);
            			opponentMancala+=opponentstones;
            			int playerMancala = board.get(PLAYER).get(board.get(PLAYER).size()-1);
            			evaluation = playerMancala-opponentMancala;
            			return true;
            		}
        		}
    		}
    	}
    	
    	return false;
    }

    static boolean isMovePossible(ArrayList<Integer> board){
        boolean movePossible = false;
        for(int pit=0; pit<pits; pit++){
            if(board.get(pit)!=0){
                movePossible = true;
            }
        }
        return movePossible;
    }

    static void transferStonesToMancala(ArrayList<ArrayList<Integer>> state, int actor){
        int remainingStones = 0;
        for(int pit=0; pit<pits; pit++){
            remainingStones += state.get(actor).get(pit);
            state.get(actor).set(pit, 0);
        }
        int oldMancala = state.get(actor).get(MANCALA);
        state.get(actor).set(MANCALA, oldMancala + remainingStones);
    }

    static ArrayList<ArrayList<Integer>> getNextState(ArrayList<ArrayList<Integer>> boards, int actor, int action){
        ArrayList<ArrayList<Integer>> state = getStateCopy(boards);
        state.get(SECONDTURNS).set(PLAYER, 0);
        state.get(SECONDTURNS).set(OPP, 0);

        int other;
        if(actor==PLAYER){
            other=OPP;
        } else {
            other=PLAYER;
        }
        int stones = state.get(actor).get(action);
        state.get(actor).set(action, 0);
        int currentSide = actor;
        int currentPit = action + 1;
        while(stones>0){
            if(currentPit==MANCALA && currentSide==actor){
                int oldValue = state.get(currentSide).get(MANCALA);
                state.get(currentSide).set(MANCALA, oldValue + 1);
                currentSide = other;
                currentPit = 0;
                stones--;
                if(stones==0){
                    state.get(SECONDTURNS).set(actor, 1);
                }
            } else if (currentPit!=MANCALA && currentSide==actor){
                int oldValue = state.get(currentSide).get(currentPit);
                state.get(currentSide).set(currentPit, oldValue + 1);
                stones--;
                if(stones==0){
                    int stonesInPit = state.get(currentSide).get(currentPit);
                    if(stonesInPit==1) {
                        int stonesAcross = state.get(other).get(pits - currentPit - 1);
                        state.get(other).set(pits - currentPit - 1, 0);
                        int oldMancala = state.get(currentSide).get(MANCALA);
                        state.get(currentSide).set(MANCALA, oldMancala + stonesAcross + 1);
                        state.get(currentSide).set(currentPit, 0);
                    }
                }
                currentPit++;
            } else if (currentPit==MANCALA && currentSide==other){
                currentSide = actor;
                currentPit = 0;
            } else if(currentPit!=MANCALA && currentSide==other){
                int oldValue = state.get(currentSide).get(currentPit);
                state.get(currentSide).set(currentPit, oldValue + 1);
                currentPit++;
                stones--;
            }
        }
        if(!isMovePossible(state.get(OPP))){
            transferStonesToMancala(state, PLAYER);
            return state;
        }
        if(!isMovePossible(state.get(PLAYER))){
            transferStonesToMancala(state, OPP);
            return state;
        }
        return state;
    }

    
    // Minimax/Greedy Code Logic
    static int getMinimaxDecision(ArrayList<ArrayList<Integer>> boards, int task, PrintWriter writer) throws FileNotFoundException, UnsupportedEncodingException{
        ArrayList<ArrayList<Integer>> state = getStateCopy(boards);
        Integer maxVal = Integer.MIN_VALUE;
      
        if(PRINT.equals("ON")){
       
        writer.println("root,0,-Infinity");
        }
        int bestAction = -1;
        if(player==1){
            for(int i=0; i<pits; i++){
            	if(state.get(PLAYER).get(i)==0)
            	{
            		continue;
            	}
                ArrayList<ArrayList<Integer>> nextState = getNextState(state, PLAYER, i);
                int nextValue;
                if (nextState.get(SECONDTURNS).get(PLAYER) == 0) {
                	if(gameOver(nextState))
                	{
                		printForPlayer(i, 1, evaluation,writer);
                        nextValue = evaluation;
                	}
                	else
                	{
                		if(CUTOFF==1 && task==2)
                		{
                			int value = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
                			printForPlayer(i, 1, value,writer);
                			nextValue=value;
                		}
                		else{
                    printForPlayer(i, 1, Integer.MAX_VALUE,writer);
                    nextValue = getMinValue(nextState, 1, PLAYER, i, 1,writer);
                		}
                	}
                } else {
                	if(gameOver(nextState))
                	{
                		printForPlayer(i, 1, evaluation,writer);
                		nextState.get(SECONDTURNS).set(PLAYER, 0);
                        nextValue = evaluation;
                	}
                	
                	else
                	{
                		
                    printForPlayer(i, 1, Integer.MIN_VALUE,writer);
//                    nextState.get(SECONDTURNS).set(PLAYER, 0);
                    nextValue = getMaxValue(nextState, 1, PLAYER, i, 1,writer);
                	}
                	
                }
                if(nextValue > maxVal){
                    maxVal = nextValue;
                    bestAction = i;
                }
                if(PRINT.equals("ON"))
                {
                	writer.println("root,0," + maxVal);
                }
            }
        } else {
            for(int i=pits-1; i>=0; i--){
            	if(state.get(PLAYER).get(i)==0)
            	{
            		continue;
            	}
                ArrayList<ArrayList<Integer>> nextState = getNextState(state, PLAYER, i);
                int nextValue;
                if (nextState.get(SECONDTURNS).get(PLAYER) == 0) {
                	
                	if(gameOver(nextState))
                	{
                		 printForPlayer(i, 1, evaluation,writer);
                         nextValue = evaluation;
                	}
                	else{
                		if(CUTOFF==1 && task==2)
                		{
                			int value = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
                			printForPlayer(i, 1, value,writer);
                			nextValue=value;
                		}
                		else{
                    printForPlayer(i, 1, Integer.MAX_VALUE,writer);
                    nextValue = getMinValue(nextState, 1, PLAYER, i, 1,writer);
                		}
                	}
                } else {
                	if(gameOver(nextState))
                	{
                		 printForPlayer(i, 1, evaluation,writer);
                         nextState.get(SECONDTURNS).set(PLAYER, 0);
                         nextValue = evaluation;
                	}
                	else{
                		
                    printForPlayer(i, 1, Integer.MIN_VALUE,writer);
//                    nextState.get(SECONDTURNS).set(PLAYER, 0);
                    nextValue = getMaxValue(nextState, 1, PLAYER, i, 1,writer);
                		
                    }
                }
                if(nextValue > maxVal){
                    maxVal = nextValue;
                    bestAction = i;
                }
                writer.println("root,0," + maxVal);
            }
        }
        return bestAction;
        
    }

    static void printForOpponent(int activePit, int level, Integer value, PrintWriter writer){
        String val = value.toString();
        if(value==Integer.MAX_VALUE){
            val = "Infinity";
        }
        if(value==Integer.MIN_VALUE){
            val = "-Infinity";
        }
        if (player == 2) {
        	if(PRINT.equals("ON"))
        	{
        		writer.println("B" + (activePit + 2) + "," + level + "," + val);
        	}
        } else {
        	if(PRINT.equals("ON"))
        	{
        		writer.println("A" + (pits - activePit + 1) + "," + level + "," + val);
        	}
        }
    }

    static void printForPlayer(int activePit, int level, Integer value, PrintWriter writer){
        String val = value.toString();
        if(value==Integer.MAX_VALUE){
            val = "Infinity";
        }
        if(value==Integer.MIN_VALUE){
            val = "-Infinity";
        }
        if (player == 1) {
        	if(PRINT.equals("ON"))
        	{
        		writer.println("B" + (activePit + 2) + "," + level + "," + val);
        	}
        } else {
        	if(PRINT.equals("ON"))
        	{
        		writer.println("A" + (pits - activePit + 1) + "," + level + "," + val);
        	}
        }
    }

    static int getMinValue(ArrayList<ArrayList<Integer>> boards, int level, int parent, int parentPit, int parentLevel, PrintWriter writer){
        ArrayList<ArrayList<Integer>> state = getStateCopy(boards);

        if(level == CUTOFF){
            if(state.get(SECONDTURNS).get(OPP) == 0){
                int value = state.get(PLAYER).get(MANCALA) - state.get(OPP).get(MANCALA);
                return value;
            } 
            
            else {
                state.get(SECONDTURNS).set(OPP,0);
            }
        }

        if(gameOver(boards))
        {
        	
        	return evaluation;
        }

        int minValue = Integer.MAX_VALUE;
        int nextValue;
        int nextLevel;
        if(parent == PLAYER){
            nextLevel = level + 1;
        } else {
            nextLevel = level;
        }

        if(player == 1) {
            for (int i = pits - 1; i >= 0; i--) {
                int stonesInPit = state.get(OPP).get(i);
                if (stonesInPit > 0) {
                    ArrayList<ArrayList<Integer>> nextState = getNextState(state, OPP, i);
                    if (nextState.get(SECONDTURNS).get(OPP) != 0) {
//                        if (nextLevel != CUTOFF) {
                    	if(gameOver(nextState))
                    	{
                            printForOpponent(i, nextLevel, evaluation,writer);
                            nextValue = evaluation;
                    	}
                    	else{
                    		 printForOpponent(i, nextLevel, Integer.MAX_VALUE,writer);
                    		 nextValue = getMinValue(nextState, nextLevel, OPP, i, nextLevel,writer);
                    	}
//                        } else {
//                            int valueAtCutoff = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
//                            printForOpponent(i, nextLevel, valueAtCutoff);
//                        }
                        
                    } else {
                    	if(gameOver(nextState))
                    	{
                    		printForOpponent(i, nextLevel, evaluation,writer);
                    		nextValue = evaluation;
                    	}
                    	else
                    	{
                    		if (nextLevel != CUTOFF) {
                            printForOpponent(i, nextLevel, Integer.MIN_VALUE,writer);
                        } else {
                            int valueAtCutoff = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
                            printForOpponent(i, nextLevel, valueAtCutoff,writer);
                        }
                        nextValue = getMaxValue(nextState, nextLevel, OPP, i, nextLevel,writer);
                    	}
                    }
                    if (minValue > nextValue) {
                        minValue = nextValue;
                    }
                    if(gameOver(nextState))
                    {
                    	if (parent == PLAYER) {
                            printForPlayer(parentPit, parentLevel,evaluation,writer);
                        } else {
                            printForOpponent(parentPit, parentLevel, evaluation,writer);
                        }
                    }
                    else{
                    if (parent == PLAYER) {
                        printForPlayer(parentPit, parentLevel, minValue,writer);
                    } else {
                        printForOpponent(parentPit, parentLevel, minValue,writer);
                    }
                    }
                }
            }
        } else {
            for (int i = 0; i < pits; i++) {
                int stonesInPit = state.get(OPP).get(i);
                if (stonesInPit > 0) {
                    ArrayList<ArrayList<Integer>> nextState = getNextState(state, OPP, i);
                    if (nextState.get(SECONDTURNS).get(OPP) != 0) {
//                        if (nextLevel != CUTOFF) {
                    	if(gameOver(nextState)){
                    		printForOpponent(i, nextLevel, evaluation,writer);
                    		nextValue=evaluation;
                    		
                    	}else{
                            printForOpponent(i, nextLevel, Integer.MAX_VALUE,writer);
                            nextValue = getMinValue(nextState, nextLevel, OPP, i, nextLevel,writer); 
                    	}
//                        } else {
//                            int valueAtCutoff = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
//                            printForOpponent(i, nextLevel, valueAtCutoff);
//                        }
                       
                    } else {
                    	if(gameOver(nextState))
                    	{
                    		printForOpponent(i, nextLevel, evaluation,writer);
                    		nextValue=evaluation;
                    	}
                    	else {
                    		if (nextLevel != CUTOFF) {
                            printForOpponent(i, nextLevel, Integer.MIN_VALUE,writer);
                        } else {
                            int valueAtCutoff = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
                            printForOpponent(i, nextLevel, valueAtCutoff,writer);
                        }
                        nextValue = getMaxValue(nextState, nextLevel, OPP, i, nextLevel,writer);
                    }
                    }
                    if (minValue > nextValue) {
                        minValue = nextValue;
                    }
                    if(gameOver(nextState))
                    {
                    	if (parent == PLAYER) {
                            printForPlayer(parentPit, parentLevel,evaluation,writer);
                        } else {
                            printForOpponent(parentPit, parentLevel, evaluation,writer);
                        }
                    }
                    else{
                    if (parent == PLAYER) {
                        printForPlayer(parentPit, parentLevel, minValue,writer);
                    } else {
                        printForOpponent(parentPit, parentLevel, minValue,writer);
                    }
                }
                }
            }
        }
        return minValue;
    }

    static int getMaxValue(ArrayList<ArrayList<Integer>> boards, int level, int parent, int parentPit, int parentLevel, PrintWriter writer){
        ArrayList<ArrayList<Integer>> state = getStateCopy(boards);

        if(level == CUTOFF){
            if(state.get(SECONDTURNS).get(PLAYER) == 0){
                int value = state.get(PLAYER).get(MANCALA) - state.get(OPP).get(MANCALA);
                return value;
            } 
            
            else {
                state.get(SECONDTURNS).set(PLAYER,0);
            }
        }
        if(gameOver(boards))
        {
        	
        	return evaluation;
        }

        int maxValue = Integer.MIN_VALUE;
        int nextValue;
        int nextLevel;
        if(parent == OPP){
            nextLevel = level + 1;
        } else {
            nextLevel = level;
        }
        if(player==1) {
            for (int i = 0; i < pits; i++) {
                int stonesInPit = state.get(PLAYER).get(i);
                if (stonesInPit > 0) {
                    ArrayList<ArrayList<Integer>> nextState = getNextState(state, PLAYER, i);
                    if (nextState.get(SECONDTURNS).get(PLAYER) != 0) {
//                        if (nextLevel != CUTOFF) {
                    	if(gameOver(nextState))
                    	{
                    		printForPlayer(i, nextLevel, evaluation,writer);
                    		nextValue=evaluation;
                    	}
                    	else{
                            printForPlayer(i, nextLevel, Integer.MIN_VALUE,writer);
                            nextValue = getMaxValue(nextState, nextLevel, PLAYER, i, nextLevel,writer);
                    	}
//                        } else {
//                            int valueAtCutoff = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
//                            printForPlayer(i, nextLevel, valueAtCutoff);
//                        }
                        
                    } else {
                    	if(gameOver(nextState))
                    	{
                    		printForPlayer(i, nextLevel, evaluation,writer);
                    		nextValue=evaluation;
                    	}
                    	else
                    	{
                    	 if (nextLevel != CUTOFF) {
                            printForPlayer(i, nextLevel, Integer.MAX_VALUE,writer);
                        } else {
                            int valueAtCutoff = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
                            printForPlayer(i, nextLevel, valueAtCutoff,writer);
                        }
                        nextValue = getMinValue(nextState, nextLevel, PLAYER, i, nextLevel,writer);
                    	}
                    }
                    if (maxValue < nextValue) {
                        maxValue = nextValue;
                    }
                    if(gameOver(nextState))
                    {
                    	if (parent == PLAYER) {
                            printForPlayer(parentPit, parentLevel, maxValue,writer);
                        } else {
                            printForOpponent(parentPit, parentLevel, maxValue,writer);
                        }	
                    }
                    else{
                    if (parent == PLAYER) {
                        printForPlayer(parentPit, parentLevel, maxValue,writer);
                    } else {
                        printForOpponent(parentPit, parentLevel, maxValue,writer);
                    }
                    }
                }
            }
        } else {
            for (int i = pits - 1; i >= 0; i--) {
                int stonesInPit = state.get(PLAYER).get(i);
                if (stonesInPit > 0) {
                    ArrayList<ArrayList<Integer>> nextState = getNextState(state, PLAYER, i);
                    if (nextState.get(SECONDTURNS).get(PLAYER) != 0) {
//                        if (nextLevel != CUTOFF) {
                    	if(gameOver(nextState))
                    	{
                    		printForPlayer(i, nextLevel, evaluation,writer);
                    		nextValue=evaluation;
                    	}
                    	else{
                            printForPlayer(i, nextLevel, Integer.MIN_VALUE,writer);
                            nextValue = getMaxValue(nextState, nextLevel, PLAYER, i, nextLevel,writer);
                    	}
//                        } else {
//                            int valueAtCutoff = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
//                            printForPlayer(i, nextLevel, valueAtCutoff);
//                        }
                        
                    } else {
                    	if(gameOver(nextState))
                    	{
                    		printForPlayer(i, nextLevel, evaluation,writer);
                    		nextValue = evaluation;
                    	}
                    	else
                    	{
                    	if	(nextLevel != CUTOFF) {
                            printForPlayer(i, nextLevel, Integer.MAX_VALUE,writer);
                        } else {
                            int valueAtCutoff = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
                            printForPlayer(i, nextLevel, valueAtCutoff,writer);
                        }
                        nextValue = getMinValue(nextState, nextLevel, PLAYER, i, nextLevel,writer);
                    }
                    }
                    if (maxValue < nextValue) {
                        maxValue = nextValue;
                    }
                    if(gameOver(nextState))
                    {
                    	if (parent == PLAYER) {
                            printForPlayer(parentPit, parentLevel, maxValue,writer);
                        } else {
                            printForOpponent(parentPit, parentLevel, maxValue,writer);
                        }
                    }
                    else{
                    if (parent == PLAYER) {
                        printForPlayer(parentPit, parentLevel, maxValue,writer);
                    } else {
                        printForOpponent(parentPit, parentLevel, maxValue,writer);
                    }
                }
                }
            }
        }
        return maxValue;
    }
    
    
    
    
    //Alpha-Beta Code Logic
    
    static int getAlphaBetaMinimaxDecision(ArrayList<ArrayList<Integer>> boards,PrintWriter writer){
        ArrayList<ArrayList<Integer>> state = getStateCopy(boards);
        Integer maxVal = Integer.MIN_VALUE;
        Integer alpha= Integer.MIN_VALUE;
        Integer beta = Integer.MAX_VALUE;
        if(PRINT.equals("ON"))
        {
        writer.println("root,0,-Infinity,-Infinity,Infinity");
        }
        int bestAction = -1;
        if(player==1){
            for(int i=0; i<pits; i++){
            	if(state.get(PLAYER).get(i)==0)
            	{
            		continue;
            	}
                ArrayList<ArrayList<Integer>> nextState = getNextState(state, PLAYER, i);
                int nextValue;
                if (nextState.get(SECONDTURNS).get(PLAYER) == 0) {
                	if(gameOver(nextState))
                	{
                		 printForPlayerAlphaBeta(i, 1, evaluation,alpha,beta,writer);
                         nextValue = evaluation;
                	}
                	else
                	{
                		if(CUTOFF==1)
                		{
                			int value = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
                			printForPlayerAlphaBeta(i, 1, value,alpha,beta,writer);
                            nextValue=value;
                		}
                		else{
                    printForPlayerAlphaBeta(i, 1, Integer.MAX_VALUE,alpha,beta,writer);
                    nextValue = getAlphaBetaMinValue(nextState, 1, PLAYER, i, 1, alpha, beta,writer);
                	}
                	}
                } else {
                	if(gameOver(nextState)){
                		 printForPlayerAlphaBeta(i, 1, evaluation,alpha,beta,writer);
                         nextState.get(SECONDTURNS).set(PLAYER, 0);
                         nextValue = evaluation;
                	}
                	else{
                    printForPlayerAlphaBeta(i, 1, Integer.MIN_VALUE,alpha,beta,writer);
//                    nextState.get(SECONDTURNS).set(PLAYER, 0);
                    nextValue = getAlphaBetaMaxValue(nextState, 1, PLAYER, i, 1, alpha, beta,writer);
                	}
                }
                if(nextValue > maxVal){
                    maxVal = nextValue;
                    bestAction = i;
                }
                if(maxVal>=beta)
                {
                	//print statement
                	if(PRINT.equals("ON"))
                	{
                		String a= alpha.toString();
                    	String b= beta.toString();
                    	if(alpha==Integer.MIN_VALUE)
                    	{
                    		a="-Infinity";
                    	}
                    	if(beta==Integer.MAX_VALUE)
                    	{
                    		b="Infinity";
                    	}
                	System.out.println("root,0," + maxVal+","+alpha+","+beta);     	
                	}
                	return bestAction;
                }
                else
                {
                	if(maxVal>alpha)
                	{
                		alpha=maxVal;
                	}
                }
                if(PRINT.equals("ON"))
                	
                {
                	String a= alpha.toString();
                	String b= beta.toString();
                	if(alpha==Integer.MIN_VALUE)
                	{
                		a="-Infinity";
                	}
                	if(beta==Integer.MAX_VALUE)
                	{
                		b="Infinity";
                	}
                writer.println("root,0," + maxVal+","+a+","+b);
                }
            }
        } else {
            for(int i=pits-1; i>=0; i--){
            	if(state.get(PLAYER).get(i)==0)
            	{
            		continue;
            	}
                ArrayList<ArrayList<Integer>> nextState = getNextState(state, PLAYER, i);
                int nextValue;
                if (nextState.get(SECONDTURNS).get(PLAYER) == 0) {
                	if(gameOver(nextState))
                	{
                		printForPlayerAlphaBeta(i, 1, evaluation,alpha,beta,writer);
                        nextValue = evaluation;
                	}
                	else{
                		if(CUTOFF==1)
                		{
                			int value = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
                			printForPlayerAlphaBeta(i, 1, value,alpha,beta,writer);
                            nextValue=value;
                		}
                		else{
                    printForPlayerAlphaBeta(i, 1, Integer.MAX_VALUE,alpha,beta,writer);
                    nextValue = getAlphaBetaMinValue(nextState, 1, PLAYER, i, 1, alpha, beta,writer);
                	}
                	}
                } else {
                	if(gameOver(nextState))
                	{
                		 printForPlayerAlphaBeta(i, 1, evaluation,alpha,beta,writer);
                         nextState.get(SECONDTURNS).set(PLAYER, 0);
                         nextValue = evaluation;
                	}
                	else
                	{
                    printForPlayerAlphaBeta(i, 1, Integer.MIN_VALUE,alpha,beta,writer);
//                    nextState.get(SECONDTURNS).set(PLAYER, 0);
                    nextValue = getAlphaBetaMaxValue(nextState, 1, PLAYER, i, 1,alpha, beta,writer);
                	}
                }
                if(nextValue > maxVal){
                    maxVal = nextValue;
                    bestAction = i;
                }
                if(maxVal>=beta)
                {
                	//print statement
                	if(PRINT.equals("ON"))
                	{
                		String a= alpha.toString();
                    	String b= beta.toString();
                    	if(alpha==Integer.MIN_VALUE)
                    	{
                    		a="-Infinity";
                    	}
                    	if(beta==Integer.MAX_VALUE)
                    	{
                    		b="Infinity";
                    	}
                	writer.println("root,0," + maxVal+","+a+","+b);     	
                	}
                	return bestAction;
                }
                else
                {
                	if(maxVal>alpha)
                	{
                		alpha=maxVal;
                	}
                }
                if(PRINT.equals("ON"))
                {
                	String a= alpha.toString();
                	String b= beta.toString();
                	if(alpha==Integer.MIN_VALUE)
                	{
                		a="-Infinity";
                	}
                	if(beta==Integer.MAX_VALUE)
                	{
                		b="Infinity";
                	}
                writer.println("root,0," + maxVal+","+a+","+b);
                }
            }
        }
        return bestAction;
    }

    static void printForOpponentAlphaBeta(int activePit, int level, Integer value, Integer alpha, Integer beta,PrintWriter writer){
        String val = value.toString();
        String a = alpha.toString();
        String b = beta.toString();
        if(value==Integer.MAX_VALUE){
            val = "Infinity";
        }
        if(value==Integer.MIN_VALUE){
            val = "-Infinity";
        }
        if(alpha==Integer.MAX_VALUE){
            a = "Infinity";
        }
        if(alpha==Integer.MIN_VALUE){
            a = "-Infinity";
        }
        if(beta==Integer.MAX_VALUE){
            b = "Infinity";
        }
        if(beta==Integer.MIN_VALUE){
            b = "-Infinity";
        }
        if (player == 2) {
        	if(PRINT.equals("ON"))
        	{
           writer.println("B" + (activePit + 2) + "," + level + "," + val+","+a+","+b);
        	}
        } else {
        	if(PRINT.equals("ON"))
        	{
            writer.println("A" + (pits - activePit + 1) + "," + level + "," + val+","+a+","+b);
        	}
        }
    }

    static void printForPlayerAlphaBeta(int activePit, int level, Integer value, Integer alpha, Integer beta,PrintWriter writer){
        String val = value.toString();
        String a = alpha.toString();
        String b = beta.toString();
        if(value==Integer.MAX_VALUE){
            val = "Infinity";
        }
        if(value==Integer.MIN_VALUE){
            val = "-Infinity";
        }
        if(alpha==Integer.MAX_VALUE){
            a = "Infinity";
        }
        if(alpha==Integer.MIN_VALUE){
            a = "-Infinity";
        }
        if(beta==Integer.MAX_VALUE){
            b = "Infinity";
        }
        if(beta==Integer.MIN_VALUE){
            b = "-Infinity";
        }
        if (player == 1) {
        	if(PRINT.equals("ON"))
        	{
            writer.println("B" + (activePit + 2) + "," + level + "," + val+","+a+","+b);
        	}
        } else {
        	if(PRINT.equals("ON"))
        	{
            writer.println("A" + (pits - activePit + 1) + "," + level + "," + val+","+a+","+b);
        	}
        }
    }

    static int getAlphaBetaMinValue(ArrayList<ArrayList<Integer>> boards, int level, int parent, int parentPit, int parentLevel, int alpha, int beta,PrintWriter writer){
        ArrayList<ArrayList<Integer>> state = getStateCopy(boards);

        if(level == CUTOFF){
            if(state.get(SECONDTURNS).get(OPP) == 0){
                int value = state.get(PLAYER).get(MANCALA) - state.get(OPP).get(MANCALA);
                return value;
            } 
            
           
            else {
                state.get(SECONDTURNS).set(OPP,0);
            }
        }
        if(gameOver(boards))
        {
        	return evaluation;
        }
        int minValue = Integer.MAX_VALUE;
        int nextValue;
        int nextLevel;
        if(parent == PLAYER){
            nextLevel = level + 1;
        } else {
            nextLevel = level;
        }

        if(player == 1) {
            for (int i = pits - 1; i >= 0; i--) {
                int stonesInPit = state.get(OPP).get(i);
                if (stonesInPit > 0) {
                    ArrayList<ArrayList<Integer>> nextState = getNextState(state, OPP, i);
                    if (nextState.get(SECONDTURNS).get(OPP) != 0) {
//                        if (nextLevel != CUTOFF) {
                    	if(gameOver(nextState))
                    	{
                    		printForOpponentAlphaBeta(i, nextLevel, evaluation,alpha,beta,writer);
                    		nextValue=evaluation;
                    	}
                    	else{    
                    	printForOpponentAlphaBeta(i, nextLevel, Integer.MAX_VALUE,alpha,beta,writer);
//                        } else {
//                            int valueAtCutoff = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
//                            printForOpponentAlphaBeta(i, nextLevel, valueAtCutoff);
//                        }
                        nextValue = getAlphaBetaMinValue(nextState, nextLevel, OPP, i, nextLevel,alpha,beta,writer);
                    	}
                    } else {
                    	if(gameOver(nextState))
                    	{
                    		printForOpponentAlphaBeta(i, nextLevel, evaluation,alpha,beta,writer);
                    		nextValue=evaluation;
                    	}
                    	else{
                        if (nextLevel != CUTOFF) {
                            printForOpponentAlphaBeta(i, nextLevel, Integer.MIN_VALUE,alpha,beta,writer);
                        } else {
                            int valueAtCutoff = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
                            printForOpponentAlphaBeta(i, nextLevel, valueAtCutoff,alpha,beta,writer);
                        }
                        nextValue = getAlphaBetaMaxValue(nextState, nextLevel, OPP, i, nextLevel,alpha,beta,writer);
                    	}
                    }
                    if (minValue > nextValue) {
                        minValue = nextValue;
                    }
                    
                    //add logic
                    
                    if(minValue<=alpha)
                    {
                    	//print statement
                    	
                    	if(gameOver(nextState))
                    	{
                    		if (parent == PLAYER) {
                                printForPlayerAlphaBeta(parentPit, parentLevel, evaluation,alpha,beta,writer);
                            } else {
                                printForOpponentAlphaBeta(parentPit, parentLevel, evaluation,alpha,beta,writer);
                            }    
                    	}
                    	else{
                    	if (parent == PLAYER) {
                            printForPlayerAlphaBeta(parentPit, parentLevel, minValue,alpha,beta,writer);
                        } else {
                            printForOpponentAlphaBeta(parentPit, parentLevel, minValue,alpha,beta,writer);
                        }    
                    	}
                    	return minValue;
                    }
                    else
                    {
                    	if(minValue<beta)
                    	{
                    		beta=minValue;
                    	}
                    }
                    
                    // if-else logic
                    
                    if(gameOver(nextState))
                    {
                    	if (parent == PLAYER) {
                            printForPlayerAlphaBeta(parentPit, parentLevel, evaluation,alpha,beta,writer);
                        } else {
                            printForOpponentAlphaBeta(parentPit, parentLevel, evaluation,alpha,beta,writer);
                        }
                    }
                    else
                    {
                    if (parent == PLAYER) {
                        printForPlayerAlphaBeta(parentPit, parentLevel, minValue,alpha,beta,writer);
                    } else {
                        printForOpponentAlphaBeta(parentPit, parentLevel, minValue,alpha,beta,writer);
                    }
                    }
                }
            }
        } else {
            for (int i = 0; i < pits; i++) {
                int stonesInPit = state.get(OPP).get(i);
                if (stonesInPit > 0) {
                    ArrayList<ArrayList<Integer>> nextState = getNextState(state, OPP, i);
                    if (nextState.get(SECONDTURNS).get(OPP) != 0) {
//                        if (nextLevel != CUTOFF) {
                    	if(gameOver(nextState))
                    	{
                    		 printForOpponentAlphaBeta(i, nextLevel, evaluation,alpha,beta,writer);
                    		 nextValue=evaluation;
                    	}
                    	else{
                            printForOpponentAlphaBeta(i, nextLevel, Integer.MAX_VALUE,alpha,beta,writer);
//                        } else {
//                            int valueAtCutoff = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
//                            printForOpponentAlphaBeta(i, nextLevel, valueAtCutoff);
//                        }
                        nextValue = getAlphaBetaMinValue(nextState, nextLevel, OPP, i, nextLevel,alpha,beta,writer);
                    	}
                    } else {
                    	if(gameOver(nextState))
                    	{
                    		printForOpponentAlphaBeta(i, nextLevel, evaluation,alpha,beta,writer);
                    		nextValue=evaluation;
                    	}
                    	else{
                        if (nextLevel != CUTOFF) {
                            printForOpponentAlphaBeta(i, nextLevel, Integer.MIN_VALUE,alpha,beta,writer);
                        } else {
                            int valueAtCutoff = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
                            printForOpponentAlphaBeta(i, nextLevel, valueAtCutoff,alpha,beta,writer);
                        }
                        nextValue = getAlphaBetaMaxValue(nextState, nextLevel, OPP, i, nextLevel,alpha,beta,writer);
                    	}
                    }
                    if (minValue > nextValue) {
                        minValue = nextValue;
                    }
                    //add logic
                    
                    if(minValue<=alpha)
                    {
                    	//print statement
                    	
                    	if(gameOver(nextState))
                    	{
                    		if (parent == PLAYER) {
                                printForPlayerAlphaBeta(parentPit, parentLevel, evaluation,alpha,beta,writer);
                            } else {
                                printForOpponentAlphaBeta(parentPit, parentLevel, evaluation,alpha,beta,writer);
                            }   
                    	}
                    	else{
                    	if (parent == PLAYER) {
                            printForPlayerAlphaBeta(parentPit, parentLevel, minValue,alpha,beta,writer);
                        } else {
                            printForOpponentAlphaBeta(parentPit, parentLevel, minValue,alpha,beta,writer);
                        }     
                    	}
                    	return minValue;
                    }
                    else
                    {
                    	if(minValue<beta)
                    	{
                    		beta=minValue;
                    	}
                    }
                    
                    if(gameOver(nextState))
                    {
                    	if (parent == PLAYER) {
                            printForPlayerAlphaBeta(parentPit, parentLevel, evaluation,alpha,beta,writer);
                        } else {
                            printForOpponentAlphaBeta(parentPit, parentLevel, evaluation,alpha,beta,writer);
                        }
                    }
                    else{
                    if (parent == PLAYER) {
                        printForPlayerAlphaBeta(parentPit, parentLevel, minValue,alpha,beta,writer);
                    } else {
                        printForOpponentAlphaBeta(parentPit, parentLevel, minValue,alpha,beta,writer);
                    }
                    }
                }
            }
        }
        return minValue;
    }

    static int getAlphaBetaMaxValue(ArrayList<ArrayList<Integer>> boards, int level, int parent, int parentPit, int parentLevel, int alpha, int beta,PrintWriter writer){
        ArrayList<ArrayList<Integer>> state = getStateCopy(boards);

        if(level == CUTOFF){
            if(state.get(SECONDTURNS).get(PLAYER) == 0){
                int value = state.get(PLAYER).get(MANCALA) - state.get(OPP).get(MANCALA);
                return value;
            }
            
            
            
            else {
                state.get(SECONDTURNS).set(PLAYER,0);
            }
        }
        if(gameOver(boards))
        {
        	return evaluation;
        }

        int maxValue = Integer.MIN_VALUE;
        int nextValue;
        int nextLevel;
        if(parent == OPP){
            nextLevel = level + 1;
        } else {
            nextLevel = level;
        }
        if(player==1) {
            for (int i = 0; i < pits; i++) {
                int stonesInPit = state.get(PLAYER).get(i);
                if (stonesInPit > 0) {
                    ArrayList<ArrayList<Integer>> nextState = getNextState(state, PLAYER, i);
                    if (nextState.get(SECONDTURNS).get(PLAYER) != 0) {
//                        if (nextLevel != CUTOFF) {
                    	if(gameOver(nextState))
                    	{
                    		printForPlayerAlphaBeta(i, nextLevel, evaluation,alpha,beta,writer);
                    		nextValue=evaluation;
                    	}
                    	else
                    	{
                    		printForPlayerAlphaBeta(i, nextLevel, Integer.MIN_VALUE,alpha,beta,writer);
                    	
//                        } else {
//                            int valueAtCutoff = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
//                            printForPlayerAlphaBeta(i, nextLevel, valueAtCutoff);
//                        }
                        nextValue = getAlphaBetaMaxValue(nextState, nextLevel, PLAYER, i, nextLevel,alpha,beta,writer);
                    	}
                    } else {
                    	
                    	if(gameOver(nextState))
                    	{
                    		 printForPlayerAlphaBeta(i, nextLevel, evaluation,alpha,beta,writer);
                    		 nextValue=evaluation;
                    	}
                    	else{
                    		
                    	
                        if (nextLevel != CUTOFF) {
                            printForPlayerAlphaBeta(i, nextLevel, Integer.MAX_VALUE,alpha,beta,writer);
                        } else {
                            int valueAtCutoff = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
                            printForPlayerAlphaBeta(i, nextLevel, valueAtCutoff,alpha,beta,writer);
                        }
                        nextValue = getAlphaBetaMinValue(nextState, nextLevel, PLAYER, i, nextLevel,alpha,beta,writer);
                    }
                    }
                    if (maxValue < nextValue) {
                        maxValue = nextValue;
                    }
                    //add logic
                    if(maxValue>=beta)
                    {
                    	//print statement
                    	if(gameOver(nextState))
                    	{
                    		if (parent == PLAYER) {
                                printForPlayerAlphaBeta(parentPit, parentLevel, evaluation,alpha,beta,writer);
                            } else {
                                printForOpponentAlphaBeta(parentPit, parentLevel, evaluation,alpha,beta,writer);
                            } 
                    	}
                    	else{
                    	if (parent == PLAYER) {
                            printForPlayerAlphaBeta(parentPit, parentLevel, maxValue,alpha,beta,writer);
                        } else {
                            printForOpponentAlphaBeta(parentPit, parentLevel, maxValue,alpha,beta,writer);
                        } 
                    	}
                    	return maxValue;
                    }
                    else
                    {
                    	if(maxValue>alpha)
                    	{
                    		alpha=maxValue;
                    	}
                    }
                    
                    
                    if(gameOver(nextState))
                    {
                    	if (parent == PLAYER) {
                            printForPlayerAlphaBeta(parentPit, parentLevel, evaluation,alpha,beta,writer);
                        } else {
                            printForOpponentAlphaBeta(parentPit, parentLevel, evaluation,alpha,beta,writer);
                        }
                    	
                    }
                    else{
                    if (parent == PLAYER) {
                        printForPlayerAlphaBeta(parentPit, parentLevel, maxValue,alpha,beta,writer);
                    } else {
                        printForOpponentAlphaBeta(parentPit, parentLevel, maxValue,alpha,beta,writer);
                    }
                    }
                }
            }
        } else {
            for (int i = pits - 1; i >= 0; i--) {
                int stonesInPit = state.get(PLAYER).get(i);
                if (stonesInPit > 0) {
                    ArrayList<ArrayList<Integer>> nextState = getNextState(state, PLAYER, i);
                    if (nextState.get(SECONDTURNS).get(PLAYER) != 0) {
//                        if (nextLevel != CUTOFF) {
                    	if(gameOver(nextState))
                    	{
                    		printForPlayerAlphaBeta(i, nextLevel, evaluation,alpha,beta,writer);
                    		nextValue=evaluation;
                    	}
                    	else{
                            printForPlayerAlphaBeta(i, nextLevel, Integer.MIN_VALUE,alpha,beta,writer);
//                        } else {
//                            int valueAtCutoff = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
//                            printForPlayerAlphaBeta(i, nextLevel, valueAtCutoff);
//                        }
                        nextValue = getAlphaBetaMaxValue(nextState, nextLevel, PLAYER, i, nextLevel,alpha,beta,writer);
                    	}
                    } else {
                    	if(gameOver(nextState))
                    	{
                    		printForPlayerAlphaBeta(i, nextLevel, evaluation,alpha,beta,writer);
                    		nextValue=evaluation;
                    	}
                    	else{
                        if (nextLevel != CUTOFF) {
                            printForPlayerAlphaBeta(i, nextLevel, Integer.MAX_VALUE,alpha,beta,writer);
                        } else {
                            int valueAtCutoff = nextState.get(PLAYER).get(MANCALA) - nextState.get(OPP).get(MANCALA);
                            printForPlayerAlphaBeta(i, nextLevel, valueAtCutoff,alpha,beta,writer);
                        }
                        nextValue = getAlphaBetaMinValue(nextState, nextLevel, PLAYER, i, nextLevel,alpha,beta,writer);
                    }}
                    	
                    if (maxValue < nextValue) {
                        maxValue = nextValue;
                    }
                    //add logic
                    
                    if(maxValue>=beta)
                    {
                    	//print statement
                    	if(gameOver(nextState))
                    	{
                    		if (parent == PLAYER) {
                                printForPlayerAlphaBeta(parentPit, parentLevel, evaluation,alpha,beta,writer);
                            } else {
                                printForOpponentAlphaBeta(parentPit, parentLevel, evaluation,alpha,beta,writer);
                            } 
                    	}
                    	else{
                    	if (parent == PLAYER) {
                            printForPlayerAlphaBeta(parentPit, parentLevel, maxValue,alpha,beta,writer);
                        } else {
                            printForOpponentAlphaBeta(parentPit, parentLevel, maxValue,alpha,beta,writer);
                        }  
                    	}
                    	return maxValue;
                    }
                    else
                    {
                    	if(maxValue>alpha)
                    	{
                    		alpha=maxValue;
                    	}
                    }
                    if(gameOver(nextState))
                    {
                    	if (parent == PLAYER) {
                            printForPlayerAlphaBeta(parentPit, parentLevel, evaluation,alpha,beta,writer);
                        } else {
                            printForOpponentAlphaBeta(parentPit, parentLevel, evaluation,alpha,beta,writer);
                        }
                    }
                    else{
                    if (parent == PLAYER) {
                        printForPlayerAlphaBeta(parentPit, parentLevel, maxValue,alpha,beta,writer);
                    } else {
                        printForOpponentAlphaBeta(parentPit, parentLevel, maxValue,alpha,beta,writer);
                    }
                    }
                }
            }
        }
        return maxValue;
    }
}