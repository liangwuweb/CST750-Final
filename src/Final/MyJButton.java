package Final;

import javax.swing.*;

public class MyJButton extends JButton
{
  private static final long serialVersionUID = 2L;
  public final int ROW;
  public final int COL;
  private String desc;
  
  public MyJButton(String text, int row, int col)
  {
    super(text);
    this.ROW = row;
    this.COL = col;
    this.setDesc("");
  }

  public String getDesc() {
    return this.desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
