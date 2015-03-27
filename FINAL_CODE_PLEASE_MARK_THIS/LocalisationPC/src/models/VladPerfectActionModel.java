package models;


import rp.robotics.localisation.ActionModel;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.mapping.Heading;

/**
 * This class is the Perfect Action model which evaluates the probability of the robot given in a position.
 * @author Vlad-Marian Toncu
 * 
 */
public class VladPerfectActionModel implements ActionModel {
	@Override
	public GridPositionDistribution updateAfterMove(
			GridPositionDistribution _from, Heading _heading) {

		// Create the new distribution that will result from applying the action
		// model
		GridPositionDistribution to = new GridPositionDistribution(_from);
		// Move the probability in the correct direction for the action
		if (_heading == Heading.PLUS_X) {
			movePlusX(to);
		} 
		else if (_heading == Heading.PLUS_Y) {
			movePlusY(to);
		} 
		else if (_heading == Heading.MINUS_X) {
			moveMinusX(to);

		}
		else if (_heading == Heading.MINUS_Y) {
			moveMinusY(to);

		}

		return to;
	}
	/**
	 * Move probabilities from one cell in the plus x direction. It uses only one grid position distribution because the nature of the implementation
	 * of this method is going reverse, without the need of a second GridPositionDistribution to keep track of the previous probabilities.
	 * 
	 * @param _to GridPositionDistribution we update after moving PlusX
	 */
	private void movePlusX(GridPositionDistribution _to) {
		//Number of points who have probability equal to 0. We use this counter in order to distribute equally the value of the probabilities to the rest of the positions
		int counter=0;
		//The sum of all the probabilities in the grid map
		float sum=1;
		// Number of points which aren't obstructed (We could've put a constant but we want it to work with every situation possible).
		int nonobstructed=0;
		// iterate through points updating as appropriate
		for (int y = 0; y <_to.getGridHeight(); y++) {

			for (int x=_to.getGridWidth()-1;x>0;x--) {

				// make sure to respect obstructed grid points
				if (!_to.isObstructed(x, y)) {
					
					nonobstructed++;
					
					// position before move
					int fromX = x-1;
					int fromY = y;
					float fromProb = _to.getProbability(fromX, fromY);

					// position after move
					int toX = x;
					int toY = y;
					
					// if the starting point is at the left edge of the graph (or is being obstructed)
					if(fromX==0 && !_to.isObstructed(x-1, y))
					{
						    _to.setProbability(fromX, fromY, 0);
					}
					
					//not a valid transition
					if(!_to.getGridMap().isValidTransition(fromX, fromY, toX, toY))
					{
							counter++;
							_to.setProbability(toX, toY, 0);
					}
					else
					{
						if(fromProb==0)
						{
							counter++;
						}
							
						_to.setProbability(toX, toY, fromProb);
					}
					
					}
				}
			}
		
		//Iterate through each point and check which is the the sum of probabilities which haven't been distributed
		for (int y = 0; y <_to.getGridHeight(); y++)
			for (int x=0;x<_to.getGridWidth();x++)
				if(!_to.isObstructed(x, y))
				{
					sum=sum-_to.getProbability(x, y);
				}
		
		float distribution=0;
		//Distribute the undistributed probability to points which still are "possible positions"(non-obstructed and not having probability 0)
		distribution=sum/((nonobstructed-counter));
		
		for (int y = 0; y <_to.getGridHeight(); y++)
				for (int x=0;x<_to.getGridWidth();x++)
			{
					if (!_to.isObstructed(x, y) && _to.getProbability(x, y)!=0)
					{
						float original=_to.getProbability(x,y);
						_to.setProbability(x, y, original+distribution);
					}		
			}
	}
	/**
	 * Move probabilities from one cell in the plus y direction. It uses only one grid position distribution because the nature of the implementation
	 * of this method is going reverse, without the need of a second GridPositionDistribution to keep track of the previous probabilities.
	 * 
	 * @param _to GridPositionDistribution we update after moving PlusY
	 */
	private void movePlusY(GridPositionDistribution _to) {
		//Number of points who have probability equal to 0. We use this counter in order to distribute equally the value of the probabilities to the rest of the positions
		int counter=0;
		//The sum of all the probabilities in the grid map
		float sum=1;
		// Number of points which aren't obstructed (We could've put a constant but we want it to work with every situation possible).
		int nonobstructed=0;
		// iterate through points updating as appropriate
		for (int y = _to.getGridHeight()-1; y>0; y--) {

			for (int x = 0; x <_to.getGridWidth(); x++) {

				// make sure to respect obstructed grid points
				if (!_to.isObstructed(x, y)) {
					
					nonobstructed++;
					
					// position before move
					int fromX = x;
					int fromY = y-1;
					float fromProb = _to.getProbability(fromX, fromY);

					// position after move
					int toX = x;
					int toY = y;
					
					// if the starting point is at the top edge of the graph (or is being obstructed)
					if(fromY==0 && !_to.isObstructed(x, y-1))
					{
						    _to.setProbability(fromX, fromY, 0);
					}
					
					//not a valid transition
					if(!_to.getGridMap().isValidTransition(fromX, fromY, toX, toY))
					{
							counter++;
							_to.setProbability(toX, toY, 0);
					}
					else
					{
						if(fromProb==0)
						{
							counter++;
						}
							
						_to.setProbability(toX, toY, fromProb);
					}
					
					}
				}
			}
		
		//Iterate through each point and check which is the the sum of probabilities which haven't been distributed
		for (int y = 0; y <_to.getGridHeight(); y++)
			for (int x=0;x<_to.getGridWidth();x++)
				if(!_to.isObstructed(x, y))
					{
						sum=sum-_to.getProbability(x, y);
					}
		
		float distribution=0;
		//Distribute the undistributed probability to points which still are "possible positions"(non-obstructed and not having probability 0)
		distribution=sum/((nonobstructed-counter));
		
		for (int y = 0; y <_to.getGridHeight(); y++)
				for (int x=0;x<_to.getGridWidth();x++)
			{
					if (!_to.isObstructed(x, y) && _to.getProbability(x, y)!=0)
					{
						float original=_to.getProbability(x,y);
						_to.setProbability(x, y, original+distribution);
					}		
			}
	}
	/**
	 * Move probabilities from one cell in the minus x direction. It uses only one grid position distribution because the nature of the implementation
	 * of this method is going reverse, without the need of a second GridPositionDistribution to keep track of the previous probabilities.
	 * 
	 * @param _to GridPositionDistribution we update after moving MinusY
	 */
	private void moveMinusX(GridPositionDistribution _to) {
		//Number of points who have probability equal to 0. We use this counter in order to distribute equally the value of the probabilities to the rest of the positions
		int counter=0;
		//The sum of all the probabilities in the grid map
		float sum=1;
		// Number of points which aren't obstructed (We could've put a constant but we want it to work with every situation possible).
		int nonobstructed=0;
		// iterate through points updating as appropriate
		for (int y = 0; y <_to.getGridHeight(); y++) {

			for (int x = 0; x<_to.getGridWidth()-1; x++) {

				// make sure to respect obstructed grid points
				if (!_to.isObstructed(x, y)) {
					
					nonobstructed++;
					
					// position before move
					int fromX = x+1;
					int fromY = y;
					float fromProb = _to.getProbability(fromX, fromY);
					
					// position after move
					int toX = x;
					int toY = y;
					
					// if the starting point is at the right edge of the graph (or is being obstructed)
					if(fromX==_to.getGridWidth()-1 && !_to.isObstructed(x+1, y))
					{
						    _to.setProbability(fromX, fromY, 0);
					}
					
					//not valid transition
					if(!_to.getGridMap().isValidTransition(fromX, fromY, toX, toY))
					{
							counter++;
							_to.setProbability(toX, toY, 0);
					}
					else
					{
						if(fromProb==0)
						{
							counter++;
						}
							
						_to.setProbability(toX, toY, fromProb);
					}
					
					}
				}
			}
		
		//Iterate through each point and check which is the the sum of probabilities which haven't been distributed
		for (int y = 0; y <_to.getGridHeight(); y++)
			for (int x=0;x<_to.getGridWidth();x++)
				if(!_to.isObstructed(x, y))
				{
					sum=sum-_to.getProbability(x, y);
				}
		
		float distribution=0;
		//Distribute the undistributed probability to points which still are "possible positions"(non-obstructed and not having probability 0)
		distribution=sum/((nonobstructed-counter));
		
		for (int y = 0; y <_to.getGridHeight(); y++)
				for (int x=0;x<_to.getGridWidth();x++)
			{
					if (!_to.isObstructed(x, y) && _to.getProbability(x, y)!=0)
					{
						float original=_to.getProbability(x,y);
						_to.setProbability(x, y, original+distribution);
					}		
			}
		
	}
	/**
	 * Move probabilities from one cell in the minus y direction. It uses only one grid position distribution because the nature of the implementation
	 * of this method is going reverse, without the need of a second GridPositionDistribution to keep track of the previous probabilities.
	 * 
	 * @param _to GridPositionDistribution we update after moving MinusY
	 */
	private void moveMinusY(GridPositionDistribution _to) {
		//Number of points who have probability equal to 0. We use this counter in order to distribute equally the value of the probabilities to the rest of the positions
		int counter=0;
		//The sum of all the probabilities in the grid map
		float sum=1;
		// Number of points which aren't obstructed (We could've put a constant but we want it to work with every situation possible).
		int nonobstructed=0;
		// iterate through points updating as appropriate
		for (int y =0; y <_to.getGridHeight()-1; y++) {

			for (int x = 0; x <_to.getGridWidth(); x++) {

				// make sure to respect obstructed grid points
				if (!_to.isObstructed(x, y)) {
					
					nonobstructed++;
					// position before move
					int fromX = x;
					int fromY = y+1;
					float fromProb = _to.getProbability(fromX, fromY);

					// position after move
					int toX = x;
					int toY = y;
					
					// if the starting point is at the bottom edge of the graph (or is being obstructed)
					if(fromY==_to.getGridHeight()-1 && !_to.isObstructed(x, y+1))
					{
						    _to.setProbability(fromX, fromY, 0);
					}
					
					//not valid transition
					if(!_to.getGridMap().isValidTransition(fromX, fromY, toX, toY))
					{
							counter++;
							_to.setProbability(toX, toY, 0);
					}
					else
					{
						if(fromProb==0)
						{
							counter++;
						}
							
						_to.setProbability(toX, toY, fromProb);
					}
					
					}
				}
			}
		
		//Iterate through each point and check which is the the sum of probabilities which haven't been distributed
		for (int y = 0; y <_to.getGridHeight(); y++)
			for (int x=0;x<_to.getGridWidth();x++)
				if(!_to.isObstructed(x, y))
				{
					sum=sum-_to.getProbability(x, y);
				}
		
		float distribution=0;
		//Distribute the undistributed probability to points which still are "possible positions"(non-obstructed and not having probability 0)
		distribution=sum/((nonobstructed-counter));
		
		for (int y = 0; y <_to.getGridHeight(); y++)
				for (int x=0;x<_to.getGridWidth();x++)
			{
					if (!_to.isObstructed(x, y) && _to.getProbability(x, y)!=0)
					{
						float original=_to.getProbability(x,y);
						_to.setProbability(x, y, original+distribution);
					}		
			}
	}
}