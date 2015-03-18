package rp.robotics.whereAmI;

import rp.robotics.localisation.ActionModel;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.mapping.Heading;

/**
 * 
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
		} else if (_heading == Heading.PLUS_Y) {
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
