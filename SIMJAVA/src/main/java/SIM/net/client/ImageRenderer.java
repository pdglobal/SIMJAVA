package SIM.net.client;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class ImageRenderer
  extends DefaultTableCellRenderer
{
  private static final long serialVersionUID = 6632514682975739962L;
  
  public ImageRenderer() {}
  
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
  {
    Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    ((JLabel)cell).setIcon((ImageIcon)value);
    ((JLabel)cell).setText("");
    ((JLabel)cell).setHorizontalAlignment(0);
    
    if (isSelected) {
      cell.setBackground(new Color(184, 207, 229));
    } else {
      cell.setBackground(null);
    }
    return cell;
  }
}
