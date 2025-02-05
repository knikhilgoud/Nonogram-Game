 
public class Assign {
 /**
   * Constructor
   * 
   * @param row the row of the cell to be assigned
   * @param col the column of the cell to be assigned
   * @param state the assignment (will be EMPTY, FULL or UNKNOWN)
   */
  public Assign(int row, int col, int state) {
    if (row<0)
      throw new IllegalArgumentException("invalid row (" + row + ")");
    if (col<0)
      throw new IllegalArgumentException("invalid col (" + col + ")");
    if (!Cell.isValidState(state))
      throw new IllegalArgumentException("invalid state (" + state + ")");
    this.row   = row;
    this.col   = col;
    this.state = state;
    this.previousState= getState();
  }
  
  /**
   * Retrieve the cell row
   * 
   * @return the row
   */
  public int getRow() {
    return row;
  }

  /**
   * Retrieve the cell column
   * 
   * @return the column
   */
  public int getCol() {
    return col;
  }

  /**
   * Retrieve the cell state
   * 
   * @return the assignment
   */
  public int getState() {
    return state;
  }
  
  /**
   * String representation of the assignment (useful for debugging)
   * 
   * @return the String representation
   */
  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("Assign(" + row + "," + col + "," + state + ")");
    return buf.toString();
  }
  
  
   /**
   * Get the previous state of puzzle cell
   *
   * @return the previous state of cell
   */
  public int getPreviousState() {
    return previousState;
  }
  
  /**
   * Set Previous State of puzzle cell
   *
   */
  public void setPreviousState(int previousState) {
    this.previousState = previousState;
  }
	 
  private int row   = 0;
  private int col   = 0;
  private int state = Nonogram.UNKNOWN;
  private int previousState = Nonogram.UNKNOWN;

}
