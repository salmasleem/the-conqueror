package views.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Font;
import units.Archer;
import units.Cavalry;
import units.Unit;
import views.button.UnitButton;

public class UnitPanel extends JPanel {
  Unit unit;
  private JTextArea info = new JTextArea();
  private UnitButton select;

  public JTextArea getInfo() {
    return info;
  }

  public void setInfo(JTextArea info) {
    this.info = info;
  }

  public UnitButton getSelect() {
    return select;
  }

  public void setSelect(UnitButton action1) {
    this.select = action1;
  }

  public UnitPanel(ActionListener a, Unit unit) {
    this.unit = unit;
    var background = new ImagePanel(new ImageIcon(setUnitPanelTypeImage(unit)).getImage());
    setLayout(new BorderLayout());
    background.setLayout(new BorderLayout());
    select = new UnitButton("Select", 20);
    select.setUnit(unit);

    var buttonPanel = new JPanel();

    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
    buttonPanel.add(select);
    buttonPanel.setOpaque(false);

    background.add(buttonPanel, BorderLayout.PAGE_END);
    info.setText(unit.toString());
    info.setOpaque(false);
    info.setEditable(false);
    info.setFont(new Font(Font.MONOSPACED, Font.ITALIC | Font.BOLD, 16));
    setOpaque(false);

    background.add(info, BorderLayout.EAST);
    select.addActionListener(a);
    add(background);
  }

  private String setUnitPanelTypeImage(Unit unit) {
    if (unit instanceof Archer) {
      return "./assets/img/units/archer.png";
    } else if (unit instanceof Cavalry) {
      return "./assets/img/units/cavalry.png";
    } else {
      return "./assets/img/units/infantry.png";
    }

  }

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }

}
