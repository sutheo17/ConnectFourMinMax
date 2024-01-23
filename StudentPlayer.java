import java.util.ArrayList;

public class StudentPlayer extends Player{
	
	private final int MAX_DEPTH = 7;
	
    public StudentPlayer(int playerIndex, int[] boardSize, int nToConnect) {
        super(playerIndex, boardSize, nToConnect);
    }
    
    private int miniMax(Board board, int depth, int alpha, int beta, boolean isMaxPlayer)
    {
    	
    	if(board.gameEnded() || depth == 0 || board.getValidSteps().isEmpty())
    	{
    		return evalPosition(board, isMaxPlayer);
    	}
    	
    	else if(isMaxPlayer)
    	{
    		int maxEval = Integer.MIN_VALUE;
    		
    		ArrayList<Integer> moves = board.getValidSteps();
    		
    		for(int i = 0; i < moves.size(); i++)
    		{
    			Board copyBoard = new Board(board);
    			copyBoard.step(2, moves.get(i));
    			maxEval = Math.max(maxEval, miniMax(copyBoard, depth-1, alpha, beta, false));
    			alpha = Math.max(maxEval, alpha);
    			if(alpha >= beta)
    			{
    				break;
    			}
    		}
    		return maxEval;
    	}
    	else
    	{
    		int minEval = Integer.MAX_VALUE;
    		
    		ArrayList<Integer> moves = board.getValidSteps();
    		
    		for(int i = 0; i < moves.size(); i++)
    		{
    			Board copyBoard = new Board(board);
    			copyBoard.step(1, moves.get(i));
    			minEval = Math.min(minEval, miniMax(copyBoard, depth-1, alpha, beta, true));
    			beta = Math.min(minEval, beta);
    			if(alpha >= beta)
    			{
    				break;
    			}
    		}
    		return minEval;
    	}
    }
   
    private int evalPosition(Board board, boolean isMaxPlayer)
    {
		int score = 0;
		
		if(board.getWinner() == 2)
		{
			score += 100;
		}
		else if(board.getWinner() == 1)
		{
			score = -20;
		}
		else
		{
			for(int i = 0; i < 7; i++)
			{
				for(int j = 0; j < 6; j++)
				{
					if(isThreeInARow(board, j, i) && isMaxPlayer)
						score += 5;
					else if(isTwoInARow(board, j, i) && isMaxPlayer)
						score += 2;
					else if(isThreeInARow(board, j, i) && !isMaxPlayer)
						score -= 8;
				}
			}
		}
		
		
		for(int i = 0; i < 6; i++)
		{
			if(board.getState()[i][3] == 2)
			{
				score += 8;
			}
		}
		
		return score;
    }

    private boolean isThreeInARow(Board board, int r, int c)
	{
		int rowNumber = 6;
		int colNumber = 7;
		int symbol = 0;
		
		if(board.getState()[r][c] == 1)
		{
			symbol = 1;
		}
		else if(board.getState()[r][c] == 2)
		{
			symbol = 2;
		}
			
		if(symbol == 0)
		{
			return false;
		}
		
		if(r + 3 < rowNumber && board.getState()[r+1][c] == symbol  && board.getState()[r+2][c] == symbol  && board.getState()[r+3][c] == 0)
		{
			return true;
		}
		else if(c + 3 < colNumber && board.getState()[r][c+1] == symbol && board.getState()[r][c+2] == symbol  && board.getState()[r][c+3] == 0)
		{
			return true;
		}
		else if(r + 3 < rowNumber && c + 3 < colNumber && board.getState()[r+1][c+1] == symbol  && board.getState()[r+2][c+2] == symbol  && board.getState()[r+3][c+3] == 0)
		{
			return true;
		}
		else if(r - 3 >= 0 && c +3 < colNumber && board.getState()[r - 1][c +1] == symbol  && board.getState()[r-2][c+2] == symbol  && board.getState()[r-3][c+3] == 0)
		{
			return true;
		}
		return false;
		
	}
	
	private boolean isTwoInARow(Board board, int r, int c)
	{
		int rowNumber = 6;
		int colNumber = 7;
		int symbol = 0;
		if(board.getState()[r][c] == 1)
		{
			symbol = 1;
		}
		else if(board.getState()[r][c] == 2)
		{
			symbol = 2;
		}
		
		if(symbol == 0)
		{
			return false;
		}
		
		if(r + 2 < rowNumber && board.getState()[r+1][c] == symbol  && board.getState()[r+2][c] == 0)
		{
			return true;
		}
			
		else if(c + 2 < colNumber && board.getState()[r][c+1] == symbol && board.getState()[r][c+2] == 0)
		{
			return true;
		}
			
		else if(r + 2 < rowNumber && c + 2 < colNumber && board.getState()[r+1][c+1] == symbol  && board.getState()[r+2][c+2] == 0)
		{
			return true;
		}
			
		else if(r - 2 >= 0 && c +2 < colNumber && board.getState()[r - 1][c +1] == symbol  && board.getState()[r-2][c+2] == 0) 
		{
			return true;
		}
			
		return false;
	}

    
    
	@Override
    public int step(Board board) {
    	ArrayList<Integer> moves = board.getValidSteps();
    	ArrayList<Integer> movesScore = new ArrayList<Integer>();
    	
    	
    	for(int i = 0 ; i < moves.size(); i++)
    	{
    		Board copyBoard = new Board(board);
    		copyBoard.step(2, moves.get(i));
    		movesScore.add(miniMax(copyBoard, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, false));
    	}
    	
    	int max = Integer.MIN_VALUE;
    	int max_pos = 0;
    	
    	
    	for(int i = 0; i < movesScore.size(); i++)
    	{
    		if(movesScore.get(i) > max)
    		{
    			max = movesScore.get(i);
    			max_pos = i;
    		}
    	}
    	
    	
    	return moves.get(max_pos);
    	
    }

}
