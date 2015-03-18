package rp.robotics.localisation;

import rp.robotics.mapping.Heading;

/**
 * Example structure for an action model that should move the probabilities 1
 * cell in the requested direction. In the case where the move would take the
 * robot into an obstacle or off the map, this model assumes the robot stayed in
 * one place. This is the same as the model presented in Robot Programming
 * lecture on action models.
 * 
 * Note that this class doesn't actually do this, instead it shows you a
 * <b>possible</b> structure for your action model.
 * 
 * @author Vlad-Marian Toncu
 * 
 */
public class PerfectActionModel implements ActionModel {
	@Override
	public GridPositionDistribution updateAfterMove(
			GridPositionDistribution _from, Heading _heading) {

		// Create the new distribution that will result from applying the action
		// model
		GridPositionDistribution to = new GridPositionDistribution(_from);
		// Move the probability in the correct direction for the action
		if (_heading == Heading.PLUS_X) {
			movePlusX(to);
		} else if (_heading == Heading.PLUS_Y) {
			// you could implement a movePlusY etc. or you could find a way do
			// do all moves in a single method. Hint: all changes are just + or
			// - 1 to an x or y value.
			movePlusY(to);
		} else if (_heading == Heading.MINUS_X) {
			moveMinusX(to);

		} else if (_heading == Heading.MINUS_Y) {
			moveMinusY(to);

		}

		return to;
	}
	/**
	 * Move probabilities from _from one cell in the plus x direction into _to
	 * 
	 * @param _from
	 * @param _to
	 */
	private void movePlusX(GridPositionDistribution _to) {
		int counter=0;
		float sum=1;
		int nonobstructed=0;
		// iterate through points updating as appropriate
		for (int y = 0; y <_to.getGridHeight(); y++) {

			for (int x=_to.getGridWidth()-1;x>0;x--) {

				// make sure to respect obstructed grid points
				if (!_to.isObstructed(x, y)) {
					// the action model should work out all of the different
					// ways (x,y) in the _to grid could've been reached based on
					// the _from grid and the move taken (in this case
					// HEADING.PLUS_X)

					// for example if the only way to have got to _to (x,y) was
					// from _from (x-1, y) (i.e. there was a PLUS_X move from
					// (x-1, y) then you write the value from _from (x-1, y) to
					// the _to (x, y) value

					// The below code does not translate the value, just copies
					// it to the same position
					nonobstructed++;
					// position before move
					int fromX = x-1;
					int fromY = y;
					float fromProb = _to.getProbability(fromX, fromY);

					// position after move
					int toX = x;
					int toY = y;
					// set probability for position after move
					if(fromX==0)
					{
						    _to.setProbability(fromX, fromY, 0);
					}
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
		for (int y = 0; y <_to.getGridHeight(); y++)
			for (int x=0;x<_to.getGridWidth();x++)
				if(!_to.isObstructed(x, y))
				{
					sum=sum-_to.getProbability(x, y);
				}
		float distribution=0;
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
	private void movePlusY(GridPositionDistribution _to) {
		int counter=0;
		float sum=1;
		int nonobstructed=0;
		// iterate through points updating as appropriate
		for (int y = _to.getGridHeight()-1; y>0; y--) {

			for (int x = 0; x <_to.getGridWidth(); x++) {

				// make sure to respect obstructed grid points
				if (!_to.isObstructed(x, y)) {
					// the action model should work out all of the different
					// ways (x,y) in the _to grid could've been reached based on
					// the _from grid and the move taken (in this case
					// HEADING.PLUS_X)

					// for example if the only way to have got to _to (x,y) was
					// from _from (x-1, y) (i.e. there was a PLUS_X move from
					// (x-1, y) then you write the value from _from (x-1, y) to
					// the _to (x, y) value

					// The below code does not translate the value, just copies
					// it to the same position
					nonobstructed++;
					// position before move
					int fromX = x;
					int fromY = y-1;
					float fromProb = _to.getProbability(fromX, fromY);

					// position after move
					int toX = x;
					int toY = y;
					if(fromY==0)
					{
						    _to.setProbability(fromX, fromY, 0);
					}
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
		for (int y = 0; y <_to.getGridHeight(); y++)
			for (int x=0;x<_to.getGridWidth();x++)
				if(!_to.isObstructed(x, y))
				{
					sum=sum-_to.getProbability(x, y);
				}
		float distribution=0;
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
	private void moveMinusX(GridPositionDistribution _to) {
		int counter=0;
		float sum=1;
		int nonobstructed=0;
		// iterate through points updating as appropriate
		for (int y = 0; y <_to.getGridHeight(); y++) {

			for (int x = 0; x<_to.getGridWidth()-1; x++) {

				// make sure to respect obstructed grid points
				if (!_to.isObstructed(x, y)) {
					// the action model should work out all of the different
					// ways (x,y) in the _to grid could've been reached based on
					// the _from grid and the move taken (in this case
					// HEADING.PLUS_X)

					// for example if the only way to have got to _to (x,y) was
					// from _from (x-1, y) (i.e. there was a PLUS_X move from
					// (x-1, y) then you write the value from _from (x-1, y) to
					// the _to (x, y) value

					// The below code does not translate the value, just copies
					// it to the same position
					nonobstructed++;
					// position before move
					int fromX = x+1;
					int fromY = y;
					float fromProb = _to.getProbability(fromX, fromY);
					// position after move
					int toX = x;
					int toY = y;
					// set probability for position after move
					if(fromX==_to.getGridWidth()-1)
					{
						    _to.setProbability(fromX, fromY, 0);
					}
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
		for (int y = 0; y <_to.getGridHeight(); y++)
			for (int x=0;x<_to.getGridWidth();x++)
				if(!_to.isObstructed(x, y))
				{
					sum=sum-_to.getProbability(x, y);
				}
		float distribution=0;
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
	private void moveMinusY(GridPositionDistribution _to) {
		int counter=0;
		float sum=1;
		int nonobstructed=0;
		// iterate through points updating as appropriate
		for (int y =0; y <_to.getGridHeight()-1; y++) {

			for (int x = 0; x <_to.getGridWidth(); x++) {

				// make sure to respect obstructed grid points
				if (!_to.isObstructed(x, y)) {
					// the action model should work out all of the different
					// ways (x,y) in the _to grid could've been reached based on
					// the _from grid and the move taken (in this case
					// HEADING.PLUS_X)

					// for example if the only way to have got to _to (x,y) was
					// from _from (x-1, y) (i.e. there was a PLUS_X move from
					// (x-1, y) then you write the value from _from (x-1, y) to
					// the _to (x, y) value

					// The below code does not translate the value, just copies
					// it to the same position
					nonobstructed++;
					// position before move
					int fromX = x;
					int fromY = y+1;
					float fromProb = _to.getProbability(fromX, fromY);

					// position after move
					int toX = x;
					int toY = y;
					if(fromY==_to.getGridHeight()-1)
					{
						    _to.setProbability(fromX, fromY, 0);
					}
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
		for (int y = 0; y <_to.getGridHeight(); y++)
			for (int x=0;x<_to.getGridWidth();x++)
				if(!_to.isObstructed(x, y))
				{
					sum=sum-_to.getProbability(x, y);
				}
		float distribution=0;
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
